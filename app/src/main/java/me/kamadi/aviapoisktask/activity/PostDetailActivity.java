package me.kamadi.aviapoisktask.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kamadi.aviapoisktask.R;
import me.kamadi.aviapoisktask.customview.TagLayout;
import me.kamadi.aviapoisktask.database.Repo;
import me.kamadi.aviapoisktask.helper.PicassoImageGetter;
import me.kamadi.aviapoisktask.model.Post;
import me.kamadi.aviapoisktask.presenter.PostDetailPresenter;
import me.kamadi.aviapoisktask.presenter.PostDetailPresenterImpl;
import me.kamadi.aviapoisktask.view.PostDetailView;

public class PostDetailActivity extends AppCompatActivity implements PostDetailView {

    public static final String CURRENT_POST = "me.kamadi.aviapoisktask.activity.PostDetailActivity.CURRENT_POST";
    public static final String IMAGE_TRANSITION_NAME = "me.kamadi.aviapoisktask.activity.PostDetailActivity.IMAGE_TRANSITION_NAME";
    public static final String TITLE_TRANSITION_NAME = "me.kamadi.aviapoisktask.activity.PostDetailActivity.TITLE_TRANSITION_NAME";
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.description_text_view)
    TextView descriptionTextView;

    @BindView(R.id.author_text_view)
    TextView authorTextView;

    @BindView(R.id.poster_image_view)
    ImageView posterImageView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.tagLayout)
    TagLayout tagLayout;

    private Post currentPost;
    PostDetailPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        currentPost = getIntent().getParcelableExtra(CURRENT_POST);
        ViewCompat.setTransitionName(posterImageView, IMAGE_TRANSITION_NAME);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(currentPost.getName());
        }

        Picasso.with(this)
                .load(currentPost.getImagePath())
                .into(posterImageView);
        try {
            presenter = new PostDetailPresenterImpl(this,currentPost, new Repo(this));
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
    public void displayPost(Post post) {
        try {
            Display display = getWindowManager().getDefaultDisplay();
            int width = display.getWidth();
            Spanned spanned = Html.fromHtml(post.getText(), new PicassoImageGetter(descriptionTextView, getResources(), Picasso.with(this), width), null);
            descriptionTextView.setVisibility(View.VISIBLE);
            descriptionTextView.setText(spanned);
            authorTextView.setText(post.getAuthor());
            String[] tags = post.getTags().split(",");
            LayoutInflater layoutInflater = getLayoutInflater();
            for (String tag : tags) {
                View tagView = layoutInflater.inflate(R.layout.list_item_keyword, null, false);
                TextView tagTextView = (TextView) tagView.findViewById(R.id.tagTextView);
                tagTextView.setText(tag);
                tagLayout.addView(tagView);
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
