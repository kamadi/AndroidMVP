package me.kamadi.aviapoisktask.view;

import java.util.List;

import me.kamadi.aviapoisktask.model.Magazine;

/**
 * Created by Madiyar on 11.07.2016.
 */
public interface MainView extends BaseView {
    void setMagazines(List<Magazine> magazines);
}
