package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.movieapp.adapter.MovieAdapter;
import com.example.movieapp.databinding.MovieItemBinding;
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
    private Button btnDownload1, btnDownload2, btnRemoveAll;
    private RecyclerView rvMovie;
    private MovieAdapter movieAdapter;
    public List<Movie> movies;
    private MovieAPI movieAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initObserve();
        initData();
        initListener();
    }

    private void init() {
        btnDownload1 = findViewById(R.id.btn_download1);
        btnDownload2 = findViewById(R.id.btn_download2);
        btnRemoveAll = findViewById(R.id.btn_remove_all);
        rvMovie = findViewById(R.id.rv_movie);
        movies = new ArrayList<>();

        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        movieViewModel.init();


        movieAdapter = new MovieAdapter(MainActivity.this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        rvMovie.setLayoutManager(gridLayoutManager);
        rvMovie.setAdapter(movieAdapter);

        movieAPI = MovieAPI.retrofit.create(MovieAPI.class);
    }

    private void initObserve() {
        movieViewModel.subscribe().observe(MainActivity.this, movies1 -> {
            Log.d(TAG, "movieViewModel : 데이터 변경됨");
        });
    }

    private void initData() {
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

    private void initListener() {
        btnDownload1.setOnClickListener(v -> {
            download1();
            Toast.makeText(this, "다운로드 완료", Toast.LENGTH_SHORT).show();
        });

        btnDownload2.setOnClickListener(v -> {
            download2();
            Toast.makeText(this, "다운로드 완료", Toast.LENGTH_SHORT).show();
        });

        btnRemoveAll.setOnClickListener(v -> {
            deleteAll();
            Toast.makeText(this, "전체 삭제되었습니다.", Toast.LENGTH_LONG).show();
        });

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,  ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                deleteOne(viewHolder.getAdapterPosition());
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvMovie);
    }

    private void download1() {
        Call<Yts> call = movieAPI.downloadMovies1();
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

    private void download2() {
        Call<Yts> call = movieAPI.downloadMovies2();
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

    public void deleteOne(int position) {
        long movieId = movieAdapter.getMovieId(position);
        Call<String> call = movieAPI.deleteMovie(movieId);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("ok")) {
                    movieViewModel.removeOne(position);
                    movieAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteAll() {
        Call<String> call = movieAPI.deleteAll();
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("ok")) {
                    movieViewModel.removeAll();
                    movieAdapter.setMovies(new ArrayList<Movie>());
                } else {
                    Toast.makeText(MainActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "삭제 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }

}