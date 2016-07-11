package me.kamadi.aviapoisktask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kamadi.aviapoisktask.R;
import me.kamadi.aviapoisktask.adapter.PostAdapter;
import me.kamadi.aviapoisktask.database.Repo;
import me.kamadi.aviapoisktask.listener.RecyclerViewItemClickListener;
import me.kamadi.aviapoisktask.model.Magazine;
import me.kamadi.aviapoisktask.model.Post;
import me.kamadi.aviapoisktask.presenter.PostPresenter;
import me.kamadi.aviapoisktask.presenter.PostPresenterImpl;
import me.kamadi.aviapoisktask.view.PostView;

public class PostActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewItemClickListener.OnItemClickListener, PostView {

    public static final String CURRENT_MAGAZINE = "me.kamadi.aviapoisktask.activity.PostActivity.CURRENT_MAGAZINE";
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    private Magazine currentMagazine;
    private LinearLayoutManager layoutManager;
    private PostAdapter adapter;
    private PostPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        currentMagazine = getIntent().getParcelableExtra(CURRENT_MAGAZINE);
        setTitle(currentMagazine.getName());
        if (getSupportActionBar() != null)
            getSupportActionBar().setHomeButtonEnabled(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnItemTouchListener(
                new RecyclerViewItemClickListener(this, this));
        swipeRefreshLayout.setOnRefreshListener(this);
        try {
            presenter = new PostPresenterImpl(this, currentMagazine, new Repo(this));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void onRefresh() {
        presenter.getPosts();
    }

    @Override
    public void onItemClick(View view, int position) {
        Post post = presenter.getPost(position);
        Intent intent = new Intent(this, PostDetailActivity.class);
        intent.putExtra(PostDetailActivity.CURRENT_POST, post);

        Pair imagePair = new Pair<>(view.findViewById(R.id.thumbnail), PostDetailActivity.IMAGE_TRANSITION_NAME);
        Pair titlePair = new Pair<>(view.findViewById(R.id.title), PostDetailActivity.TITLE_TRANSITION_NAME);

        ActivityOptionsCompat transitionActivityOptions =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this, imagePair, titlePair);

        ActivityCompat.startActivity(this,
                intent, transitionActivityOptions.toBundle());

    }

    @Override
    public void setPosts(List<Post> posts) {

        adapter = new PostAdapter(PostActivity.this, posts);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgress() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideProgress() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showMessage(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
