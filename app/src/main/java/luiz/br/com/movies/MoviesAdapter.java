package luiz.br.com.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Luiz on 14/03/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter{
    private List<Movies> movies;
    private Context context;

    public MoviesAdapter(Context context, List<Movies> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        view.setOnCreateContextMenuListener((View.OnCreateContextMenuListener) context);
        MoviesViewHolder holder = new MoviesViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        MoviesViewHolder holder = (MoviesViewHolder) viewHolder;
        Movies movie = movies.get(position);

        holder.nameMovie.setText(movie.getName());
        holder.ratingMovie.setText(movie.getRating());
        holder.durationMovie.setText(movie.getDuration());
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder {
        final TextView nameMovie;
        final TextView ratingMovie;
        final TextView durationMovie;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            nameMovie = (TextView) itemView.findViewById(R.id.item_name_movie);
            ratingMovie = (TextView) itemView.findViewById(R.id.item_rating_movie);
            durationMovie = (TextView) itemView.findViewById(R.id.item_duration_movie);
        }
    }
}