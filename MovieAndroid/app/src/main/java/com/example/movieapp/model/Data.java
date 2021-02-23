package com.example.movieapp.model;

import java.util.List;

public class Data {
    private long movie_count;
    private long limit;
    private long page_number;

    private List<Movie> movies;

    public long getMovie_count() {
        return movie_count;
    }

    public void setMovie_count(long movie_count) {
        this.movie_count = movie_count;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public long getPage_number() {
        return page_number;
    }

    public void setPage_number(long page_number) {
        this.page_number = page_number;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public String toString() {
        return "Data [movie_count=" + movie_count + ", limit=" + limit + ", page_number=" + page_number + ", movies="
                + movies + "]";
    }
}
