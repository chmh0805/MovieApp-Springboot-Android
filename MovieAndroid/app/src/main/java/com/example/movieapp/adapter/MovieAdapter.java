package com.example.movieapp.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.MainActivity;
import com.example.movieapp.R;
import com.example.movieapp.databinding.MovieItemBinding;
import com.example.movieapp.model.Movie;

import java.util.Collections;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private List<Movie> movies;
    private final MainActivity mainActivity;

    public MovieAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.movies = mainActivity.movies;
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

    public long getMovieId(int position) {
        return movies.get(position).getId();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieItemBinding binding = MovieItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.movieItemBinding.setMovie(movies.get(position));
//        holder.setItem(movies.get(position));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private MovieItemBinding movieItemBinding;
        private ImageView ivMovie, ivDelete;

        public MovieViewHolder(@NonNull MovieItemBinding movieItemBinding) {
            super(movieItemBinding.getRoot());
            this.movieItemBinding = movieItemBinding;
            ivMovie = itemView.findViewById(R.id.iv_movie);
            ivDelete = itemView.findViewById(R.id.iv_delete);

            ivDelete.setOnClickListener(v -> {
                mainActivity.deleteOne(getAdapterPosition());
            });

            setDialog(ivMovie);
        }

        public void setDialog(ImageView ivMovie) {
            ivMovie.setOnClickListener(v -> {
                View dialog = v.inflate(v.getContext(), R.layout.dialog_detail, null);
                ImageView ivDialog = dialog.findViewById(R.id.iv_dialog);
                int position = getAdapterPosition();
                getGlide(ivDialog, movies.get(position).getMedium_cover_image());

                TextView tvSummary = dialog.findViewById(R.id.tv_summary);
                tvSummary.setText(movies.get(position).getSummary());

                AlertDialog.Builder dig = new AlertDialog.Builder(v.getContext());
                dig.setTitle(movies.get(position).getTitle());
                dig.setView(dialog);
                dig.setPositiveButton("상세보기", (dialog1, which) -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(movies.get(position).getUrl()));
                    v.getContext().startActivity(intent);
                });
                dig.setNegativeButton("닫기", null);
                dig.show();
            });
        }

//        public void setItem(Movie movie) {
//            getGlide(ivMovie, movie.getMedium_cover_image());
//        }

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
