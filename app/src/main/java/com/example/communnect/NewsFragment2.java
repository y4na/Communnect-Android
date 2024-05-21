//package com.example.communnect;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.android.material.progressindicator.LinearProgressIndicator;
//import com.kwabenaberko.newsapilib.NewsApiClient;
//import com.kwabenaberko.newsapilib.models.Article;
//import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
//import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//public class NewsFragment2 extends Fragment {
//
////    private RecyclerView newsRV, categoryRV;
////    private ProgressBar loadingPB;
////    private ArrayList<Article> articlesArrayList;
////    private ArrayList<CategoryRVModal> categoryRVModalArrayList;
////    private CategoryRVAdapter categoryRVAdapter;
////    private NewsRVAdapter newsRVAdapter;
//
//    RecyclerView recyclerView;
//    List<Article> articleList = new ArrayList<>();
//    NewsRecyclerAdapter adapter;
//    LinearProgressIndicator progressIndicator;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_news, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        recyclerView = view.findViewById(R.id.news_recycler_view);
//        progressIndicator = view.findViewById(R.id.progress_bar);
//
//        setUpRecyclerView();
//        getNews();
//    }
//
//    private void setUpRecyclerView() {
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        adapter = new NewsRecyclerAdapter(articleList);
//        recyclerView.setAdapter(adapter);
//    }
//
//    void changeInProgress(boolean show){
//        if (show) {
//            progressIndicator.setVisibility(View.VISIBLE);
//        }else{
//            progressIndicator.setVisibility(View.INVISIBLE);
//        }
//    }
//
//    private void getNews() {
//        if (progressIndicator != null) {
//            changeInProgress(true);
//        }
//        NewsApiClient newsApiClient = new NewsApiClient("54179ecfee9f4e3ba215794d949fd4df");
//        newsApiClient.getTopHeadlines(
//                new TopHeadlinesRequest.Builder()
//                        .language("en")
//                        .build(),
//                new NewsApiClient.ArticlesResponseCallback() {
//                    @SuppressLint("NotifyDataSetChanged")
//                    @Override
//                    public void onSuccess(ArticleResponse response) {
//                        if (getActivity() == null) {
//                            return;
//                        }
//                        getActivity().runOnUiThread(() -> {
//                            changeInProgress(false);
//                            articleList = response.getArticles();
////                            NewsRecyclerAdapter adapter = new NewsRecyclerAdapter(articleList);
//                            adapter.updateData(articleList);
//                            adapter.notifyDataSetChanged();
//                        });
//                    }
//
//                    @Override
//                    public void onFailure(Throwable throwable) {
//                        if (getActivity() != null) {
//                            getActivity().runOnUiThread(() -> changeInProgress(false));
//                        }
//                        Log.i("GOT FAILURE", Objects.requireNonNull(throwable.getMessage()));
//                    }
//                }
//        );
//    }
//}
