package com.example.communnect;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    private final String api_key = "669ecbdf65f247a78f5d4d044101eeb2";

    private RecyclerView newsRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private NewsRVAdapter newsRVAdapter;
    private SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchView = view.findViewById(R.id.search_view);
        View searchPlate = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(Color.TRANSPARENT); // Or use any other color
        }
        newsRV = view.findViewById(R.id.idRVNews);
        loadingPB = view.findViewById(R.id.idPBLoading);

        articlesArrayList = new ArrayList<>();
        newsRVAdapter = new NewsRVAdapter(articlesArrayList, getContext());

        newsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRV.setAdapter(newsRVAdapter);

        // Initially hide the progress bar
        loadingPB.setVisibility(View.GONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getNews("All", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        // Remove the black underline from the SearchView
        removeSearchViewUnderline(searchView);

        return view;
    }

    private void getNews(String category, String query) {
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;

        if (query != null && !query.isEmpty()) {
            String searchURL = "v2/everything?q=" + query + "&apiKey=" + api_key;
            call = retrofitAPI.getNewsBySearch(searchURL);
        } else if (category.equals("All")) {
            String url = "v2/top-headlines?country=us&language=en&apiKey=" + api_key;
            call = retrofitAPI.getAllNews(url);
        } else {
            String categoryURL = "v2/top-headlines?country=us&category=" + category + "&apiKey=" + api_key;
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                if (newsModal != null) {
                    articlesArrayList.addAll(newsModal.getArticles());
                    newsRVAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "No news found", Toast.LENGTH_SHORT).show();
                }
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(getContext(), "Failed to get news", Toast.LENGTH_SHORT).show();
                loadingPB.setVisibility(View.GONE);
            }
        });
    }

    private void removeSearchViewUnderline(SearchView searchView) {
        View searchPlate = searchView.findViewById(androidx.appcompat.R.id.search_plate);
        if (searchPlate != null) {
            searchPlate.setBackgroundColor(android.graphics.Color.TRANSPARENT);
        }
    }
}
