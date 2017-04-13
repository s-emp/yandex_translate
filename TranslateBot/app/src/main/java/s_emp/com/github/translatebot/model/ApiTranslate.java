package s_emp.com.github.translatebot.model;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import s_emp.com.github.translatebot.model.IClient;
import s_emp.com.github.translatebot.other.Const;

public class ApiTranslate {
    private static Gson gson = new GsonBuilder().create();
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private static IClient translateApi = retrofit.create(IClient.class);

    public static IClient getApi(){
        return translateApi;
    }
}
