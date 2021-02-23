package com.example.movieapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieapp.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieViewModel extends ViewModel {

    private MutableLiveData<List<Movie>> mldMovies = new MutableLiveData<>();

    public MutableLiveData<List<Movie>> subscribe() {
        return mldMovies;
    }

    public void init() {
        List<Movie> movies = new ArrayList<>();
        mldMovies.setValue(movies);
    }

    public void home(List<Movie> movies) {
        mldMovies.setValue(movies);
    }

    public void download(List<Movie> newMovies) {
        List<Movie> movies = mldMovies.getValue();
        for (Movie movie : newMovies) {
            movies.add(movie);
        }
        mldMovies.setValue(movies);
    }

    public void removeOne(int position) {
        List<Movie> movies = mldMovies.getValue();
        movies.remove(position);
        mldMovies.setValue(movies);
    }

    public void removeAll() {
        List<Movie> movies = new ArrayList<>();
        mldMovies.setValue(movies);
    }
}
