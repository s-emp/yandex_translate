package s_emp.com.github.translatebot.model;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import s_emp.com.github.translatebot.model.database.DBHelper;
import s_emp.com.github.translatebot.model.network.DetermiteLangDTO;
import s_emp.com.github.translatebot.model.network.LangDTO;
import s_emp.com.github.translatebot.model.network.TranslateDTO;
import s_emp.com.github.translatebot.other.Const;
import s_emp.com.github.translatebot.other.Helper;
import s_emp.com.github.translatebot.presenter.ITranslatable;

/* Основной
*
*/
public class TranslatorBot implements ITranslator {

    private static TranslatorBot instanse;
    // Направление перевода
    private String fromLang = "ru";
    private String toLang = "en";
    // Автоматическое определение исходного языка
    private boolean isAutoLang = false;

    // Объект БД
    private DBHelper dataBase;

    // Мапа списка языков
    private Map<String, String> mapLangs = null;

    // Мапа возможных переводов с одного языка на другой
    private List<String> translateLang = null;

    // Список закладок
    private List<ITranslatable> mark = null;


    public void refreshMapLangs() {
        Call<LangDTO> call = ApiTranslate.getApi().getLangs("ru", Const.KEY);
        call.enqueue(new Callback<LangDTO>() {
            @Override
            public void onResponse(Call<LangDTO> call, Response<LangDTO> response) {
                if (response.code() == 200) {
                    mapLangs = response.body().getLangs();
                    translateLang = response.body().getDirs();
                }
            }

            @Override
            public void onFailure(Call<LangDTO> call, Throwable t) {
                Log.println(Log.ERROR, "Network error ", t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void translate(final ITranslatable translatedObj) throws IOException {
        Map<String, String> query = new HashMap<>();
        query.put("key", Const.KEY);
        query.put("text", translatedObj.getSourceText().substring(0,
                Helper.getLengthTranslateText(translatedObj.getSourceText())));
        if (isAutoLang) {
            query.put("lang", toLang);
        } else {
            query.put("lang", fromLang + "-" + toLang);
        }
        Call<TranslateDTO> call = ApiTranslate.getApi().translate(query);
        call.enqueue(new Callback<TranslateDTO>() {
            @Override
            public void onResponse(Call<TranslateDTO> call, Response<TranslateDTO> response) {
                if (response.code() == 200) {
                    switch (response.body().getCode()) {
                        case 200:
                            translatedObj.setTranslatedText(TypeMessage.TRANSLATE,
                                    response.body().getText());
                            break;
                        case 401:
                            translatedObj.setMessageError(TypeMessage.ERROR_API,
                                    response.body().getCode(), "Неправильный API-ключ");
                            break;
                        case 402:
                            translatedObj.setMessageError(TypeMessage.ERROR_API,
                                    response.body().getCode(), "API-ключ заблокирован");
                            break;
                        case 403:
                            translatedObj.setMessageError(TypeMessage.ERROR_API,
                                    response.body().getCode(), "Превышено суточное ограничение " +
                                            "на объем переведенного текста");
                            break;
                        case 413:
                            translatedObj.setMessageError(TypeMessage.ERROR_API,
                                    response.body().getCode(), "Превышен максимальный размер " +
                                            "текста");
                            break;
                        case 422:
                            translatedObj.setMessageError(TypeMessage.ERROR_API,
                                    response.body().getCode(), "Текст не может быть переведен");
                            break;
                        case 501:
                            translatedObj.setMessageError(TypeMessage.ERROR_API,
                                    response.body().getCode(), "Заданое направление перевода " +
                                            "текста не поддерживается");
                            break;
                        default:
                            translatedObj.setMessageError(TypeMessage.ERROR_API,
                                    response.body().getCode(), "Не известный код!");
                    }
                } else {
                    translatedObj.setMessageError(TypeMessage.ERROR, response.code(),
                            response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<TranslateDTO> call, Throwable t) {
                Log.println(Log.ERROR, "Network error ", t.getLocalizedMessage());
                translatedObj.setMessageError(TypeMessage.ERROR_API, -200, "Network error " +
                        t.getLocalizedMessage());
            }
        });
    }

    @Override
    public void getSourceLang(final ITranslatable translatedObj) throws IOException {
        Map<String, String> query = new HashMap<>();
        query.put("key", Const.KEY);
        query.put("text", translatedObj.getSourceText().substring(0,
                Helper.getLengthTranslateText(translatedObj.getSourceText())));
        Call<DetermiteLangDTO> call = ApiTranslate.getApi().getLangText(query);
        call.enqueue(new Callback<DetermiteLangDTO>() {
            @Override
            public void onResponse(Call<DetermiteLangDTO> call, Response<DetermiteLangDTO> response) {
                if (response.code() == 200) {
                    switch (response.body().getCode()) {
                        case 200:
                            translatedObj.setTranslatedText(TypeMessage.SUGGESTION,
                                    response.body().getLang());
                            break;
                        case 401:
                            translatedObj.setMessageError(TypeMessage.ERROR_API,
                                    response.body().getCode(), "Неправильный API-ключ");
                            break;
                        case 402:
                            translatedObj.setMessageError(TypeMessage.ERROR_API,
                                    response.body().getCode(), "API-ключ заблокирован");
                            break;
                        case 403:
                            translatedObj.setMessageError(TypeMessage.ERROR_API,
                                    response.body().getCode(), "Превышено суточное ограничение " +
                                            "на объем переведенного текста");
                            break;
                        default:
                            translatedObj.setMessageError(TypeMessage.ERROR_API,
                                    response.body().getCode(), "Не известный код!");
                    }
                } else {
                    translatedObj.setMessageError(TypeMessage.ERROR, response.code(),
                            response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<DetermiteLangDTO> call, Throwable t) {
                Log.println(Log.ERROR, "Network error ", t.getLocalizedMessage());
                translatedObj.setMessageError(TypeMessage.ERROR_API, -200, "Network error " +
                        t.getLocalizedMessage());
            }
        });
    }

    @Override
    public Map<String, String> getLangs() {
        return mapLangs;
    }

    @Override
    public String getNameLang(String ui) {
        return mapLangs.get(ui);
    }

    @Override
    public List<String> getListLangsTranslate(String ui) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < translateLang.size(); i++) {
            if (translateLang.get(i).indexOf(ui) >= 0)
                // Запишем код языка на который можем перевести
                result.add(translateLang.get(i).split("-")[1]);
        }
        return result;
    }

    // endregion

    // endregion

    // Singleton
    public static TranslatorBot getInstanse() {
        if (instanse == null) {
            instanse = new TranslatorBot();
        }
        return instanse;
    }
    private TranslatorBot(){
        refreshMapLangs();
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

    //endregion

    // region Set

    public void setFromLang(String fromLang) {
        if (fromLang.equals(toLang)) {
            toLang = this.fromLang;
        }
        this.fromLang = fromLang;
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
