package me.kamadi.aviapoisktask.presenter;

import me.kamadi.aviapoisktask.model.Post;

/**
 * Created by Madiyar on 11.07.2016.
 */
public interface PostPresenter extends BasePresenter {
    void getPosts();

    void getLocalPosts();

    Post getPost(int position);

}
