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
import me.kamadi.aviapoisktask.model.Magazine;

/**
 * Created by Madiyar on 10.07.2016.
 */
public class MagazineAdapter extends RecyclerView.Adapter<MagazineAdapter.MagazineRecyclerViewHolder> {

    private Context context;
    private List<Magazine> magazines;
    private LayoutInflater inflater;

    public MagazineAdapter(Context context, List<Magazine> magazines) {
        this.context = context;
        this.magazines = magazines;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MagazineRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = inflater.inflate(R.layout.list_item_magazine, parent, false);
        return new MagazineRecyclerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(MagazineRecyclerViewHolder holder, int position) {
        Magazine magazine = magazines.get(position);
        holder.title.setText(magazine.getName());
        Picasso.with(context).load(magazine.getImagePath()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return magazines.size();
    }

    public class MagazineRecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;


        @BindView(R.id.thumbnail)
        ImageView thumbnail;

        public MagazineRecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
