package me.kamadi.aviapoisktask.presenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.kamadi.aviapoisktask.R;
import me.kamadi.aviapoisktask.api.ApiFactory;
import me.kamadi.aviapoisktask.database.Repo;
import me.kamadi.aviapoisktask.model.Magazine;
import me.kamadi.aviapoisktask.model.Post;
import me.kamadi.aviapoisktask.view.PostView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Madiyar on 11.07.2016.
 */
public class PostPresenterImpl implements PostPresenter {
    private PostView view;
    private List<Post> posts;
    private Magazine magazine;
    private Repo repo;

    public PostPresenterImpl(PostView view, Magazine magazine, Repo repo) {
        this.view = view;
        this.magazine = magazine;
        this.repo = repo;
    }

    @Override
    public void getPosts() {
        view.showProgress();
        Call<List<Post>> call = ApiFactory.getPostService().getPosts(magazine.getId());
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                posts = response.body();
                view.hideProgress();
                view.setPosts(posts);
                savePosts();
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                getLocalPosts();
            }
        });
    }

    @Override
    public void getLocalPosts() {
        try {
            posts = repo.getPostRepo().getPostByMagazine(magazine);
            view.setPosts(posts);
        } catch (SQLException e) {
            e.printStackTrace();
            view.showMessage(R.string.loading_error);
        }

        view.hideProgress();
    }

    @Override
    public Post getPost(int position) {
        return posts.get(position);
    }

    @Override
    public void onResume() {
        if (posts == null) {
            posts = new ArrayList<>();
            getPosts();
        }
    }

    private void savePosts() {
        for (Post post : posts) {
            try {
                post.setMagazine(magazine);
                repo.getPostRepo().create(post);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        view = null;
    }
}
