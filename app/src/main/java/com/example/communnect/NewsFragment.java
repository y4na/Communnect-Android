package com.example.communnect;

import android.annotation.SuppressLint;
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

public class NewsFragment extends Fragment implements CategoryRVAdapter.CategoryClickInterface{


    //54179ecfee9f4e3ba215794d949fd4df

    private RecyclerView newsRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVAdapter newsRVAdapter;
    SearchView searchView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        searchView  = view.findViewById(R.id.search_view);
        newsRV = view.findViewById(R.id.idRVNews);
        categoryRV = view.findViewById(R.id.idRVCategories);
        loadingPB = view.findViewById(R.id.idPBLoading);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getNews("ALL", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        articlesArrayList = new ArrayList<>();
        categoryRVModalArrayList = new ArrayList<>();

        newsRVAdapter = new NewsRVAdapter(articlesArrayList, getContext());
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModalArrayList, getContext(), this);

        newsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        newsRV.setAdapter(newsRVAdapter);

        categoryRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryRV.setAdapter(categoryRVAdapter);

        getCategories();
        getNews("All", null);

        return view;
    }

    private void getNews(String category, String query){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModal> call;

        if(query != null && !query.isEmpty()) {
            String searchURL = "https://newsapi.org/v2/everything?q=" + query + "&apiKey=54179ecfee9f4e3ba215794d949fd4df";
            call = retrofitAPI.getNewsBySearch(searchURL);
        } else if(category.equals("All")) {
            String url = "https://newsapi.org/v2/top-headlines?country=us&language=en&apiKey=54179ecfee9f4e3ba215794d949fd4df";
            call = retrofitAPI.getAllNews(url);
        } else {
            String categoryURL = "https://newsapi.org/v2/top-headlines?country=us&category=" + category + "&apiKey=54179ecfee9f4e3ba215794d949fd4df";
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@androidx.annotation.NonNull Call<NewsModal> call, @androidx.annotation.NonNull Response<NewsModal> response) {
                NewsModal newsModal = response.body();
                if (newsModal != null) {
                    ArrayList<Articles> articles = newsModal.getArticles();
                    for (Articles article : articles) {
                        articlesArrayList.add(article);
                    }
                    newsRVAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "No news found", Toast.LENGTH_SHORT).show();
                }
                loadingPB.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@androidx.annotation.NonNull Call<NewsModal> call, @androidx.annotation.NonNull Throwable t) {
                Toast.makeText(getContext(), "Failed to get news", Toast.LENGTH_SHORT).show();
                loadingPB.setVisibility(View.GONE);
            }
        });

    }

    @SuppressLint("NotifyDataSetChanged")
    private void getCategories(){
        categoryRVModalArrayList.add(new CategoryRVModal("All", ""));
//        categoryRVModalArrayList.add(new CategoryRVModal("General", "https://images.unsplash.com/photo-1493612276216-ee3925520721?q=80&w=1964&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Technology", "https://images.unsplash.com/photo-1518770660439-4636190af475?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Science", "https://images.unsplash.com/photo-1628595351029-c2bf17511435?q=80&w=1932&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Sports", "https://images.unsplash.com/photo-1461896836934-ffe607ba8211?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Business", "https://images.unsplash.com/photo-1664575602276-acd073f104c1?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Entertainment", "https://images.unsplash.com/photo-1603190287605-e6ade32fa852?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
        categoryRVModalArrayList.add(new CategoryRVModal("Health", "https://images.unsplash.com/photo-1498837167922-ddd27525d352?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"));
         categoryRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModalArrayList.get(position).getCategory();
        getNews(category, null);

    }
}