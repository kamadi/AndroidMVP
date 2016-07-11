package me.kamadi.aviapoisktask.database;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.List;

import me.kamadi.aviapoisktask.model.Magazine;

/**
 * Created by Madiyar on 11.07.2016.
 */
public class MagazineRepo {
    private Dao<Magazine,String>magazineDao;

    public MagazineRepo(DatabaseHelper databaseHelper) throws SQLException {
        magazineDao = databaseHelper.getMagazineDao();
    }

    public boolean create(Magazine magazine) throws SQLException {
        return magazineDao.create(magazine) == 1;
    }

    public List<Magazine>getAll() throws SQLException {
        return magazineDao.queryForAll();
    }
}
