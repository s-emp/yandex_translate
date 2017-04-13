package s_emp.com.github.translatebot.model;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;
import s_emp.com.github.translatebot.model.network.DetermiteLangDTO;
import s_emp.com.github.translatebot.model.network.LangDTO;
import s_emp.com.github.translatebot.model.network.TranslateDTO;

public interface IClient {


    @POST("api/v1.5/tr.json/getLangs")
    Call<LangDTO> getLangs(@Query("ui") String ui, @Query("key") String key);

    @FormUrlEncoded
    @POST("api/v1.5/tr.json/detect")
    Call<DetermiteLangDTO> getLangText(@FieldMap Map<String, String> map);

    @FormUrlEncoded
    @POST("api/v1.5/tr.json/translate")
    Call<TranslateDTO> translate(@FieldMap Map<String, String> map);

}
