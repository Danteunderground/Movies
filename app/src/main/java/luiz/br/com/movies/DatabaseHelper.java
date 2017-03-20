package luiz.br.com.movies;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luiz on 14/03/2017.
 */

public class DatabaseHelper  extends SQLiteOpenHelper {
    public static final String NOME_BD = "data_movies.db";
    public static final int VERSAO_BD = 1;

    public static final String TB_USERS = "users";
    public static final String TB_MOVIES = "movies";

    public DatabaseHelper(Context context){
        super(context, NOME_BD, null, VERSAO_BD);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( criaTabelaUsers() );
        sqLiteDatabase.execSQL( criaTabelaMovies() );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int versaoAntiga, int versaoNova) {}

    // Users ***************
    private String criaTabelaUsers(){
        String sql = "CREATE TABLE " + TB_USERS
                + " (id INTEGER PRIMARY KEY, "
                + " login TEXT, "
                + " senha TEXT);";

        return sql;
    }

    public void insereUsuario(Usuario usuario){
        ContentValues values = new ContentValues();

        values.put("login", usuario.getLogin());
        values.put("senha", usuario.getSenha());

        getWritableDatabase().insert(TB_USERS, null, values);
    }

    public int procuraUsuario(String login, String senha){
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM " + TB_USERS
                + " WHERE login='"+ login.trim() +"' AND senha='"+ senha.trim() +"';";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Usuario usuario = new Usuario();
            usuarios.add(usuario);
        }

        return usuarios.size();
    }

    // Movies ***************
    private String criaTabelaMovies(){
        String sql = "CREATE TABLE " + TB_MOVIES
                + " (id INTEGER PRIMARY KEY, "
                + " name TEXT, "
                + " rating TEXT, "
                + " duration TEXT);";

        return sql;
    }

    public void insereMovies(Movies movies){
        ContentValues values = new ContentValues();

        values.put("name", movies.getName());
        values.put("rating", movies.getRating());
        values.put("duration", movies.getDuration());

        getWritableDatabase().insert(TB_MOVIES, null, values);
    }

    public List<Movies> getMovies(){
        List<Movies> movies = new ArrayList<>();
        String sql = "SELECT * FROM " + TB_MOVIES + " ORDER BY id DESC;";

        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while(c.moveToNext()){
            Movies movie = new Movies();

            movie.setId(c.getLong(c.getColumnIndex("id")));
            movie.setName(c.getString(c.getColumnIndex("name")));
            movie.setRating(c.getString(c.getColumnIndex("rating")));
            movie.setDuration(c.getString(c.getColumnIndex("duration")));

            movies.add(movie);
        }

        return movies;
    }

    public void atualizaMovies(Movies movie){
        ContentValues values = new ContentValues();

        values.put("name", movie.getName());
        values.put("rating", movie.getRating());
        values.put("duration", movie.getDuration());

        String[] args = { movie.getId().toString() };
        getWritableDatabase().update(TB_MOVIES, values, "id=?", args);
    }

    public void deletaMovies(Movies movie){
        String[] args = { movie.getId().toString() };
        getWritableDatabase().delete(TB_MOVIES, "id=?", args);
    }

}