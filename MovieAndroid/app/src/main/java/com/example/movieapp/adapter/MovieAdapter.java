package com.example.movieapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.MainActivity;
import com.example.movieapp.R;
import com.example.movieapp.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private MainActivity mainActivity;

    public MovieAdapter(List<Movie> movies, MainActivity mainActivity) {
        this.movies = movies;
        this.mainActivity = mainActivity;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void addMovies(List<Movie> newMovies) {
        for (Movie movie: newMovies) {
            this.movies.add(movie);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.setItem(movies.get(position));
        holder.ivDelete.setOnClickListener(v -> {
            mainActivity.deleteOne(position, movies.get(position).getId());
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivMovie, ivDelete;
        private TextView tvTitle, tvRating;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMovie = itemView.findViewById(R.id.iv_movie);
            ivDelete = itemView.findViewById(R.id.iv_delete);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvRating = itemView.findViewById(R.id.tv_rating);
        }

        public void setItem(Movie movie) {
            getGlide(ivMovie, movie.getMedium_cover_image());
            tvTitle.setText(movie.getTitle());
            tvRating.setText(movie.getRating()+"");
        }

        private void getGlide(ImageView iv, String url) {
            Glide
                    .with(itemView.getContext())
                    .load(url)
                    .centerCrop()
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(iv);
        }
    }
}
