package s_emp.com.github.translatebot.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import s_emp.com.github.translatebot.ApiTranslate;
import s_emp.com.github.translatebot.model.dto.DetermiteLangDTO;
import s_emp.com.github.translatebot.other.Const;
import s_emp.com.github.translatebot.other.Helper;


public class TranslatorBot implements ITranslator, IHist, IMark {

    private static TranslatorBot instanse;
    // Направление перевода
    private String fromLang = "ru";
    private String toLang = "en";
    // Автоматическое определение исходного языка
    private boolean isAutoLang = false;

    @Override
    public ITranslatable getMark() {
        return null;
    }

    @Override
    public void addHist(ITranslatable obj) throws IOException {

    }

    @Override
    public void translate(ITranslatable translatedObj) throws IOException {
    }

    @Override
    public void deleteMark(int index) {

    }

    @Override
    public ITranslatable getHist(int count) {
        return null;
    }

    @Override
    public void addMark(ITranslatable mark) {

    }

    @Override
    public String getSourceLang(final ITranslatable translatedObj) throws IOException {
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
        return "1";
    }

    @Override
    public void deleteHist(int from, int to) {

    }

    @Override
    public List<String> getLangs() {
        return null;
    }

    @Override
    public void deleteHist(int index) {

    }

    @Override
    public String getNameLang(String ui) {
        return null;
    }

    @Override
    public void deleteHist() {

    }

    @Override
    public Map<String, String> getTranslateLangs() {
        return null;
    }

    // Singleton
    public static TranslatorBot getInstanse() {
        if (instanse == null) {
            instanse = new TranslatorBot();
        }
        return instanse;
    }
    private TranslatorBot(){}

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
