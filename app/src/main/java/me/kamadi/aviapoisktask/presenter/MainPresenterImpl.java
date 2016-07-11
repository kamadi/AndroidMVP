package me.kamadi.aviapoisktask.presenter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import me.kamadi.aviapoisktask.R;
import me.kamadi.aviapoisktask.api.ApiFactory;
import me.kamadi.aviapoisktask.database.Repo;
import me.kamadi.aviapoisktask.model.Magazine;
import me.kamadi.aviapoisktask.view.MainView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Madiyar on 11.07.2016.
 */
public class MainPresenterImpl implements MainPresenter {
    private List<Magazine> magazines;
    private MainView view;
    private Repo repo;

    public MainPresenterImpl(MainView view, Repo repo) {
        this.view = view;
        this.repo = repo;
    }


    @Override
    public void getMagazines() {
        view.showProgress();
        Call<List<Magazine>> call = ApiFactory.getMagazineService().getMagazines();
        call.enqueue(new Callback<List<Magazine>>() {
            @Override
            public void onResponse(Call<List<Magazine>> call, Response<List<Magazine>> response) {
                magazines = response.body();
                view.hideProgress();
                view.setMagazines(magazines);
                saveMagazines();
            }

            @Override
            public void onFailure(Call<List<Magazine>> call, Throwable t) {
                getLocalMagazines();
            }
        });
    }

    @Override
    public void getLocalMagazines() {
        try {
            magazines = repo.getMagazineRepo().getAll();
            view.setMagazines(magazines);
        } catch (SQLException e) {
            view.showMessage(R.string.loading_error);
            e.printStackTrace();
        }
        view.hideProgress();
    }

    @Override
    public Magazine getMagazine(int position) {
        return magazines.get(position);
    }

    @Override
    public void onResume() {
        if (magazines == null) {
            magazines = new ArrayList<>();
            getMagazines();
        }
    }


    @Override
    public void onDestroy() {
        view = null;
    }

    private void saveMagazines() {
        for (Magazine magazine : magazines) {
            try {
                repo.getMagazineRepo().create(magazine);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
