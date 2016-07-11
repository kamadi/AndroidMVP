package me.kamadi.aviapoisktask.api;

import java.util.List;

import me.kamadi.aviapoisktask.model.Post;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Madiyar on 05.07.2016.
 */
public interface PostService {
    @GET("magazine/{magazine_id}")
    Call<List<Post>> getPosts(@Path("magazine_id") long id);

    @GET("magazine/feed/{feed_id}")
    Call<Post> getPost(@Path("feed_id") long id);
}
