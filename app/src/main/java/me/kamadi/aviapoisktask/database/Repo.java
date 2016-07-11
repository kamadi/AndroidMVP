package me.kamadi.aviapoisktask.database;

import android.content.Context;

import java.sql.SQLException;

/**
 * Created by Madiyar on 11.07.2016.
 */
public class Repo {
    private MagazineRepo magazineRepo;
    private PostRepo postRepo;

    public Repo(Context context) throws SQLException {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        magazineRepo = new MagazineRepo(databaseHelper);
        postRepo = new PostRepo(databaseHelper);
    }

    public MagazineRepo getMagazineRepo() {
        return magazineRepo;
    }

    public PostRepo getPostRepo() {
        return postRepo;
    }
}
