package me.kamadi.aviapoisktask.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.kamadi.aviapoisktask.R;
import me.kamadi.aviapoisktask.adapter.MagazineAdapter;
import me.kamadi.aviapoisktask.database.Repo;
import me.kamadi.aviapoisktask.listener.RecyclerViewItemClickListener;
import me.kamadi.aviapoisktask.model.Magazine;
import me.kamadi.aviapoisktask.presenter.MainPresenter;
import me.kamadi.aviapoisktask.presenter.MainPresenterImpl;
import me.kamadi.aviapoisktask.view.MainView;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, RecyclerViewItemClickListener.OnItemClickListener, MainView {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SwipeRefreshLayout swipeRefreshLayout;

    private MagazineAdapter adapter;
    private MainPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addOnItemTouchListener(
                new RecyclerViewItemClickListener(this, this));
        swipeRefreshLayout.setOnRefreshListener(this);

        try {
            presenter = new MainPresenterImpl(this, new Repo(this));
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
        presenter.getMagazines();
    }


    @Override
    public void onItemClick(View view, int position) {
        Magazine magazine = presenter.getMagazine(position);
        Intent intent = new Intent(this, PostActivity.class);
        intent.putExtra(PostActivity.CURRENT_MAGAZINE, magazine);
        startActivity(intent);
    }

    @Override
    public void setMagazines(List<Magazine> magazines) {
        adapter = new MagazineAdapter(MainActivity.this, magazines);
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
