package s_emp.com.github.translatebot.model.bot;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import s_emp.com.github.translatebot.model.Language;
import s_emp.com.github.translatebot.model.database.IHistory;
import s_emp.com.github.translatebot.model.database.IMark;
import s_emp.com.github.translatebot.model.network.ApiTranslate;
import s_emp.com.github.translatebot.model.database.DBHelper;
import s_emp.com.github.translatebot.model.database.MessageDB;
import s_emp.com.github.translatebot.model.network.DetermiteLangDTO;
import s_emp.com.github.translatebot.model.network.LangDTO;
import s_emp.com.github.translatebot.model.network.TranslateDTO;
import s_emp.com.github.translatebot.other.Const;
import s_emp.com.github.translatebot.other.Helper;
import s_emp.com.github.translatebot.presenter.ChatPresenter;
import s_emp.com.github.translatebot.presenter.IChatPresenter;
import s_emp.com.github.translatebot.presenter.ITranslatable;
import s_emp.com.github.translatebot.view.IChatView;

/* Основной
*
*/
public class Bot implements IBotTranslate, IBotSetting, IHistory, IMark {

    // Направление перевода
    private String fromLang = "en";
    private String toLang = "ru";
    // Автоматическое определение исходного языка
    private boolean isAutoLang = false;

    // Объект БД
    private DBHelper dataBase;

    // Мапа списка языков
    private Map<String, String> mapLangs = null;

    // Список всех возможный вариантов переводов
    private List<String> availableTranslations = null;

    // Список закладок
    private List<ITranslatable> mark = null;

    // Список языков с которого сейчас переводим
    private ArrayList<Language> fromTranslateLanguage = new ArrayList<>();
    // Мапа возможных переводов с одного языка на другой
    private ArrayList<Language> toTranslateLanguage = new ArrayList<>();

    public void  refreshFromTranslateLanguage() {
        fromTranslateLanguage.clear();
        for (String key: mapLangs.keySet()) {
            fromTranslateLanguage.add(new Language(key, mapLangs.get(key)));
        }
        // Отсортируем
        Collections.sort(fromTranslateLanguage , new Comparator<Language>() {
            @Override
            public int compare(Language o1, Language o2) {
                return o1.getNameLanguage().compareTo(o2.getNameLanguage());
            }
        });
    }

    public void refreshToTranslateLanguage() {
        toTranslateLanguage.clear();
        for (String key: mapLangs.keySet()) {
            toTranslateLanguage.add(new Language(key, mapLangs.get(key)));
        }
        // Отсортируем
        Collections.sort(toTranslateLanguage, new Comparator<Language>() {
            @Override
            public int compare(Language o1, Language o2) {
                return o1.getNameLanguage().compareTo(o2.getNameLanguage());
            }
        });
    }



    public void refreshMapLangs(final IChatPresenter presenter) {
        Call<LangDTO> call = ApiTranslate.getApi().getLangs("ru", Const.KEY);
        call.enqueue(new Callback<LangDTO>() {
            @Override
            public void onResponse(Call<LangDTO> call, Response<LangDTO> response) {
                if (response.code() == 200) {
                    mapLangs = response.body().getLangs();
                    availableTranslations = response.body().getDirs();
                    // Не правильно передается список языков сейчас вид <en-ru> должен быть <ru>
                    refreshFromTranslateLanguage();
                    refreshToTranslateLanguage();
//                    toTranslateLanguage = getListLanguageTranslate("en");
                    presenter.updateListLanguage(fromTranslateLanguage, toTranslateLanguage);
                }
            }

            @Override
            public void onFailure(Call<LangDTO> call, Throwable t) {
                Log.println(Log.ERROR, "Network error ", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void translate(@NotNull final String message, @NotNull final ChatPresenter chatPresenter) throws IOException {
        Map<String, String> query = new HashMap<>();
        query.put("key", Const.KEY);
        query.put("text", message.substring(0, Helper.getLengthTranslateText(message)));
        if (isAutoLang) {
            query.put("lang", toLang);
        } else {
            query.put("lang", fromLang + "-" + toLang);
        }
        Call<TranslateDTO> call = ApiTranslate.getApi().translate(query);
        call.enqueue(new Callback<TranslateDTO>() {
            @Override
            public void onResponse(Call<TranslateDTO> call, Response<TranslateDTO> response) {
                MessageDB result = new MessageDB(1, "Неизвестная ошибка");
                if (response.code() == 200) {
                    switch (response.body().getCode()) {
                        case 200:
                            // Запишем историю
                            try {
                                result = new MessageDB(1, fromLang.toUpperCase() + ": " + message + "\n" +
                                        toLang.toUpperCase() + ": " + response.body().getText());
                                dataBase.addHist(result);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            chatPresenter.translateSuccess(result);
                            break;
                        case 401:
                            result.setMessage("Неправильный API-ключ");
                            chatPresenter.translateError(response.body().getCode(), result);
                            break;
                        case 402:
                            result.setMessage("API-ключ заблокирован");
                            chatPresenter.translateError(response.body().getCode(), result);
                            break;
                        case 403:
                            result.setMessage("Превышено суточное ограничение на объем переведенного текста");
                            chatPresenter.translateError(response.body().getCode(), result);
                            break;
                        case 413:
                            result.setMessage("Превышен максимальный размер текста");
                            chatPresenter.translateError(response.body().getCode(), result);
                            break;
                        case 422:
                            result.setMessage("Текст не может быть переведен");
                            chatPresenter.translateError(response.body().getCode(), result);
                            break;
                        case 501:
                            result.setMessage("Заданое направление перевода текста не поддерживается");
                            chatPresenter.translateError(response.body().getCode(), result);
                            break;
                        default:
                            chatPresenter.translateError(response.body().getCode(), result);
                    }
                } else {
                    chatPresenter.translateError(response.code(), result);
                }
            }

            @Override
            public void onFailure(Call<TranslateDTO> call, Throwable t) {
                Log.println(Log.ERROR, "Network error ", t.getLocalizedMessage());
                chatPresenter.translateError(-200, new MessageDB(1, "ошибка сети"));
            }
        });
    }

    @Override
    public void getLanguageText(@NotNull String text, @NotNull final ChatPresenter chatPresenter) throws IOException {
        Map<String, String> query = new HashMap<>();
        query.put("key", Const.KEY);
        query.put("text", text.substring(0, Helper.getLengthTranslateText(text)));
        Call<DetermiteLangDTO> call = ApiTranslate.getApi().getLangText(query);
        call.enqueue(new Callback<DetermiteLangDTO>() {
            @Override
            public void onResponse(Call<DetermiteLangDTO> call, Response<DetermiteLangDTO> response) {
                if (response.code() == 200) {
                    switch (response.body().getCode()) {
                        case 200:
                            chatPresenter.determiteSuccess(response.body().getLang());
                            break;
                        case 401:
                            chatPresenter.determiteError(response.body().getCode(), "Неправильный API-ключ");
                            break;
                        case 402:
                            chatPresenter.determiteError(response.body().getCode(), "API-ключ заблокирован");
                            break;
                        case 403:
                            chatPresenter.determiteError(response.body().getCode(),
                                    "Превышено суточное ограничение  на объем переведенного текста");
                            break;
                        default:
                            chatPresenter.determiteError(response.body().getCode(), "Не известный код!");
                    }
                } else {
                    chatPresenter.determiteError(response.code(), response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<DetermiteLangDTO> call, Throwable t) {
                chatPresenter.determiteError(-200, "Ошибка сети");
                Log.println(Log.ERROR, "Network error ", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public Map<String, String> getLanguages() {
        return mapLangs;
    }

    @Override
    public String getNameLanguage(String ui) {
        return mapLangs.get(ui);
    }

    @Override
    public List<String> getListLanguageTranslate(String ui) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < availableTranslations.size(); i++) {
            if (availableTranslations.get(i).contains(ui))
                // Запишем код языка на который можем перевести
                result.add(availableTranslations.get(i).split("-")[1]);
        }
        return result;
    }

    @Override
    public String getUiLanguage(String name) {
        return null;
    }

    // endregion

    // endregion

    public Bot(IChatView view, IChatPresenter presenter){
        refreshMapLangs(presenter);
        dataBase = new DBHelper(view.getActivity());
    }

    @Override
    public void addHist(MessageDB obj) throws IOException {
    }

    @Override
    public ArrayList<MessageDB> getMark() {
        return dataBase.getMark();
    }

    @Override
    public void setAutoTranslate(boolean isAuto) {

    }

    @Override
    public void deleteMark(int index) {
        dataBase.deleteMark(index);
    }

    @Override
    public boolean getAutoTranslate() {
        return false;
    }

    @Override
    public void addMark(MessageDB mark) {
        dataBase.addMark(mark);
    }

    @Override
    public ArrayList<MessageDB> getHist(int count) {
        return null;
    }

    @Override
    public void setFromLanguage(String language) {

    }

    @Override
    public String getFromLanguage() {
        return null;
    }

    @Override
    public void setToLanguage(String language) {
        toLang = language;
    }

    @Override
    public ArrayList<MessageDB> getHist(int from, int to) {
        return null;
    }

    @Override
    public String getToLanguage() {
        return toLang;
    }

    @Override
    public String getInfo() {
        return null;
    }


    @Override
    public void deleteHist(int index) {
        dataBase.deleteHist(index);
    }

    @Override
    public void deleteHist() {
        dataBase.deleteHist();
    }

    // region Get

    public String getFromLang() {
        return fromLang;
    }

    public String getToLang() {
        return toLang;
    }

    public boolean isAutoLang() {
        return isAutoLang;
    }

    public DBHelper getDataBase() {
        return dataBase;
    }

    //endregion

    // region Set

    public void setFromLang(String fromLang) {
        if (fromLang.equals(toLang)) {
            toLang = this.fromLang;
        }
        this.fromLang = fromLang;
        refreshToTranslateLanguage();
    }

    public void setToLang(String toLang) {
        if (toLang.equals(fromLang)) {
            fromLang = this.toLang;
        }
        this.toLang = toLang;
    }

    public void setAutoLang(boolean autoLang) {
        isAutoLang = autoLang;
    }

    // endregion
}
