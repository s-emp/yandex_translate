package s_emp.com.github.translatebot.model;

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
import s_emp.com.github.translatebot.model.dto.TranslateDTO;
import s_emp.com.github.translatebot.other.Const;

//import static s_emp.com.github.translatebot.ApiTranslate.getApi;

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
//        Logger.getGlobal().info("Вызван переводчик.");
//        System.err.println("Вызван перевод!");
//        Map<String, String> query = new HashMap<>();
//        query.put("key", Const.KEY);
//        // TODO: Сделать обработку длины символов, либо тут, либо на уровне Объекта
//        query.put("text", translatedObj.getSourceText());
//        if (isAutoLang) {
//            query.put("lang", toLang);
//        } else {
//            query.put("lang", fromLang + "-" + toLang);
//        }
//        Call<TranslateDTO> call = ApiTranslate.getApi().translate(query);
//        Response<TranslateDTO> response = call.execute();
//        System.err.println("Ответ: ");
//        System.err.println("Code:" + response.code());
//        System.err.println("Body:" + response.body());
//
//        switch (response.body().getCode()) {
//            case 200: translatedObj.setTranslatedText(response.body().getText());
//            default: translatedObj.setTranslatedText("Неизвестная ошибка");
//        }
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
    public String getSourceLang(ITranslatable translatedObj) throws IOException {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
        IClient translateApi = retrofit.create(IClient.class);

        Map<String, String> query = new HashMap<>();
        query.put("key", Const.KEY);
        // TODO: Сделать обработку длины символов, либо тут, либо на уровне Объекта
        query.put("text", translatedObj.getSourceText());
        Call<DetermiteLangDTO> call = translateApi.getLangText(query);
        call.enqueue(new Callback<DetermiteLangDTO>() {
            @Override
            public void onResponse(Call<DetermiteLangDTO> call, Response<DetermiteLangDTO> response) {
                System.out.println("Call: " + call.toString());
                System.out.println("Response code: " + response.code());
                System.out.println("Response error body: " + response.errorBody());
            }

            @Override
            public void onFailure(Call<DetermiteLangDTO> call, Throwable t) {
                System.out.println("MyError: " + t.getLocalizedMessage());
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
}
