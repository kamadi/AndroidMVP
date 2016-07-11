package me.kamadi.aviapoisktask.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import me.kamadi.aviapoisktask.model.Magazine;
import me.kamadi.aviapoisktask.model.Post;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "aviapoisktask.db";
    private static final int DATABASE_VERSION = 1;
    private static final String LOG_TAG = "DatabaseHelper";
    private Dao<Magazine, String> magazineDao;
    private Dao<Post, String> postDao;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Magazine.class);
            TableUtils.createTable(connectionSource, Post.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }


    @Override
    public void close() {
        super.close();
        magazineDao = null;
        postDao = null;
    }


    public Dao<Magazine, String> getMagazineDao() throws SQLException {
        if (magazineDao == null)
            magazineDao = getDao(Magazine.class);

        return magazineDao;
    }

    public Dao<Post, String> getPostDao() throws SQLException {
        if (postDao == null)
            postDao = getDao(Post.class);

        return postDao;
    }
}
