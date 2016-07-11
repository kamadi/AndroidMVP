package me.kamadi.aviapoisktask.presenter;

import java.sql.SQLException;

import me.kamadi.aviapoisktask.R;
import me.kamadi.aviapoisktask.api.ApiFactory;
import me.kamadi.aviapoisktask.database.Repo;
import me.kamadi.aviapoisktask.model.Magazine;
import me.kamadi.aviapoisktask.model.Post;
import me.kamadi.aviapoisktask.view.PostDetailView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Madiyar on 11.07.2016.
 */
public class PostDetailPresenterImpl implements PostDetailPresenter {

    private PostDetailView view;
    private Post post;
    private Repo repo;
    private Magazine magazine;
    public PostDetailPresenterImpl(PostDetailView view, Post post, Repo repo) {
        this.view = view;
        this.post = post;
        this.repo = repo;
        this.magazine = post.getMagazine();
    }

    @Override
    public void getPostDetail() {
        view.showProgress();
        Call<Post> call = ApiFactory.getPostService().getPost(post.getId());
        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                post = response.body();
                savePost(post);
                view.hideProgress();
                view.displayPost(post);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                getLocalPostDetail();
            }
        });
    }

    private void savePost(Post post) {
        try {
            post.setMagazine(magazine);
            repo.getPostRepo().update(post);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getLocalPostDetail() {
        try {
            post = repo.getPostRepo().getPostById(post.getId());
            view.displayPost(post);
        } catch (SQLException e) {
            e.printStackTrace();
            view.showMessage(R.string.loading_error);
        }

        view.hideProgress();
    }

    @Override
    public void onResume() {
        getPostDetail();
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
