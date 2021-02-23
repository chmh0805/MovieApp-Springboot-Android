package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.model.Movie;
import com.example.movieapp.model.MovieAPI;
import com.example.movieapp.model.Yts;
import com.example.movieapp.viewmodel.MovieViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity2";
    private MovieViewModel movieViewModel;
    private Button btnDownload, btnRemoveAll;
    private RecyclerView rvMovie;
    private MovieAdapter movieAdapter;
    private List<Movie> movies;
    private MovieAPI movieAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        setting();
    }

    private void init() {
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.init();
        movieViewModel.subscribe().observe(MainActivity.this, movies1 -> {
            Log.d(TAG, "movieViewModel : 데이터 변경됨");
        });

        btnDownload = findViewById(R.id.btn_download);
        btnRemoveAll = findViewById(R.id.btn_remove_all);
        rvMovie = findViewById(R.id.rv_movie);

        movies = new ArrayList<>();
        movieAdapter = new MovieAdapter(movies, MainActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rvMovie.setLayoutManager(gridLayoutManager);
        rvMovie.setAdapter(movieAdapter);

        movieAPI = MovieAPI.retrofit.create(MovieAPI.class);
        Call<List<Movie>> call = movieAPI.initMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> data = response.body();
                movieViewModel.home(data);
                movieAdapter.setMovies(data);
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.d(TAG, "init 실패");
                t.printStackTrace();
            }
        });
    }

    private void setting() {
        btnDownload.setOnClickListener(v -> {
            download();
            Toast.makeText(this, "다운로드 완료", Toast.LENGTH_SHORT).show();
        });

        btnRemoveAll.setOnClickListener(v -> {
            deleteAll();
            Toast.makeText(this, "전체 삭제되었습니다.", Toast.LENGTH_LONG).show();
        });
    }

    private void download() {
        Call<Yts> call = movieAPI.downloadMovies();
        call.enqueue(new Callback<Yts>() {
            @Override
            public void onResponse(Call<Yts> call, Response<Yts> response) {
                List<Movie> movies = response.body().getData().getMovies();
                movieViewModel.download(movies);
                movieAdapter.addMovies(movies);
            }
            @Override
            public void onFailure(Call<Yts> call, Throwable t) {
                Log.d(TAG, "download 실패");
                t.printStackTrace();
            }
        });
    }

    public void deleteOne(int position, long movieId) {
        Call<Void> call = movieAPI.deleteMovie(movieId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                movieViewModel.removeOne(position);
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d(TAG, "removeOne 실패");
                t.printStackTrace();
            }
        });
    }

    public void deleteAll() {
        Call<Void> call = movieAPI.deleteAll();
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                movieViewModel.removeAll();
                movieAdapter.setMovies(new ArrayList<Movie>());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

}