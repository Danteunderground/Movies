package luiz.br.com.movies;

import android.app.Activity;
import android.widget.EditText;

/**
 * Created by Luiz on 14/03/2017.
 */

public class HelpMovie {
    private Activity activity;
    private EditText nameEd, ratingEd, durationEd;
    private Movies movies;

    public HelpMovie(Activity activity){
        this.activity = activity;
        nameEd = (EditText) activity.findViewById(R.id.form_name);
        ratingEd = (EditText) activity.findViewById(R.id.form_rating);
        durationEd = (EditText) activity.findViewById(R.id.form_duration);
        movies = new Movies();
    }

    public Movies getMovieForm(){
        movies.setName( nameEd.getText().toString() );
        movies.setRating( ratingEd.getText().toString() );
        movies.setDuration( durationEd.getText().toString() );

        return movies;
    }

    public void putForm(Movies movies){
        nameEd.setText(movies.getName());
        ratingEd.setText(movies.getRating());
        durationEd.setText(movies.getDuration());
    }

    public boolean formularioValido(){
        boolean retorno = true;

        if( nameEd.getText().toString().equals("") ){
            nameEd.setError( activity.getString(R.string.erro_name) );
            retorno = false;
        }

        if( ratingEd.getText().toString().equals("") ){
            ratingEd.setError( activity.getString(R.string.erro_rating) );
            retorno = false;
        }

        if( durationEd.getText().toString().equals("") ){
            durationEd.setError( activity.getString(R.string.erro_duration) );
            retorno = false;
        }

        return retorno;
    }

}
