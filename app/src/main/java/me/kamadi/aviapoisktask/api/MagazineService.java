package me.kamadi.aviapoisktask.api;

import java.util.List;

import me.kamadi.aviapoisktask.model.Magazine;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Madiyar on 05.07.2016.
 */
public interface MagazineService {

    @GET("magazine")
    Call<List<Magazine>> getMagazines();

}
