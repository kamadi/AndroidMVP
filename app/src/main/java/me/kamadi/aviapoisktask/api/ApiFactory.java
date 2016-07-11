package me.kamadi.aviapoisktask.api;

import android.support.annotation.NonNull;

import me.kamadi.aviapoisktask.BuildConfig;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Madiyar on 01.05.2016.
 */
public class ApiFactory {

    private static final OkHttpClient CLIENT = new OkHttpClient();

    @NonNull
    public static Retrofit getRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.API_ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create())
                .client(CLIENT)
                .build();
    }

    public static MagazineService getMagazineService() {
        return getRetrofit().create(MagazineService.class);
    }

    public static PostService getPostService() {
        return getRetrofit().create(PostService.class);
    }
}
