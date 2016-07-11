package me.kamadi.aviapoisktask.view;

import java.util.List;

import me.kamadi.aviapoisktask.model.Post;

/**
 * Created by Madiyar on 11.07.2016.
 */
public interface PostView extends BaseView {
    void setPosts(List<Post> posts);
}
