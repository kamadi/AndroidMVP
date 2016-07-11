package me.kamadi.aviapoisktask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kamadi.aviapoisktask.R;
import me.kamadi.aviapoisktask.model.Post;

/**
 * Created by Madiyar on 10.07.2016.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostRecyclerViewHolder> {

    private Context context;
    private List<Post> posts;
    private LayoutInflater inflater;

    public PostAdapter(Context context, List<Post> posts) {
        this.context = context;
        this.posts = posts;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public PostRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.list_item_post, parent, false);
        return new PostRecyclerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(PostRecyclerViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.title.setText(post.getName());
        holder.description.setText(post.getDescription());
        Picasso.with(context).load(post.getImagePath()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class PostRecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        @BindView(R.id.description)
        TextView description;

        public PostRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
