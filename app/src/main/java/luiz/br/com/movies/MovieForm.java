package luiz.br.com.movies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MovieForm extends AppCompatActivity {
    private DatabaseHelper databaseHelper;
    private HelpMovie helper;
    private Movies movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_form);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        iniciaElementos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if( movie != null ){
            helper.putForm(movie);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cadastro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;

            case R.id.action_salvar:
                if( helper.formularioValido() ){
                    saveOrEdit();
                }
                return false;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveOrEdit(){
        if( movie == null ){
            saveMovie();
        }else{
            updateMovie();
        }
    }

    private void saveMovie(){
        Movies movie = helper.getMovieForm();
        databaseHelper.insereMovies(movie);
        databaseHelper.close();
        Toast.makeText(this, R.string.sucesso_form, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void updateMovie(){
        Movies tmp = helper.getMovieForm();

        movie.setName( tmp.getName() );
        movie.setRating( tmp.getRating() );
        movie.setDuration( tmp.getDuration() );

        databaseHelper.atualizaMovies(movie);
        Toast.makeText(this, R.string.sucesso_edita_form, Toast.LENGTH_SHORT).show();
        finish();
    }

    private void iniciaElementos(){
        helper = new HelpMovie(this);
        databaseHelper = new DatabaseHelper(this);
        movie = (Movies) getIntent().getSerializableExtra(Constantes.TAG_MOVIE);
    }

}
