package me.kamadi.aviapoisktask.presenter;

import me.kamadi.aviapoisktask.model.Magazine;

/**
 * Created by Madiyar on 11.07.2016.
 */
public interface MainPresenter extends BasePresenter {
    void getMagazines();

    void getLocalMagazines();

    Magazine getMagazine(int position);
}
