package me.kamadi.aviapoisktask.database;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.List;

import me.kamadi.aviapoisktask.model.Magazine;
import me.kamadi.aviapoisktask.model.Post;

/**
 * Created by Madiyar on 11.07.2016.
 */
public class PostRepo {
    private Dao<Post, String> dao;

    public PostRepo(DatabaseHelper databaseHelper) throws SQLException {
        dao = databaseHelper.getPostDao();
    }

    public boolean create(Post post) throws SQLException {
        return dao.create(post) == 1;
    }

    public boolean update(Post post) throws SQLException {
        return dao.update(post) == 1;
    }

    public List<Post> getPostByMagazine(Magazine magazine) throws SQLException {
        QueryBuilder<Post, String> qb = dao.queryBuilder();
        qb.where().eq("magazine_id", magazine.getId());
        return qb.query();
    }

    public List<Post> getAll() throws SQLException {
        return dao.queryForAll();
    }

    public Post getPostById(long id) throws SQLException {
        QueryBuilder<Post, String> qb = dao.queryBuilder();
        qb.where().eq("id", id);
        return qb.queryForFirst();
    }
}
