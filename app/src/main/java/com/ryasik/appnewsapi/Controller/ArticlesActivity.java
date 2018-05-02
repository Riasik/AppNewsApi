package com.ryasik.appnewsapi.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.ryasik.appnewsapi.API.ApiClient;
import com.ryasik.appnewsapi.API.ApiInterface;
import com.ryasik.appnewsapi.Adapters.PaginationAdapter;
import com.ryasik.appnewsapi.Adapters.PaginationScrollListener;
import com.ryasik.appnewsapi.Fragments.SettingsFragment;
import com.ryasik.appnewsapi.Model.Item;
import com.ryasik.appnewsapi.Model.articlesResponse;
import com.ryasik.appnewsapi.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesActivity extends AppCompatActivity {
    public static String API_KEY = MainActivity.API_KEY;
    private static final String TAG = ArticlesActivity.class.getSimpleName();

    PaginationAdapter adapter;
    ProgressBar progressBar;
    private RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    Toolbar toolbar;

    private static final int PAGE_START = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = PAGE_START;
    private List<Item> results;
    private ApiInterface apiService;
    private String source = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.main_progress);
        adapter = new PaginationAdapter(this);
        source = getIntent().getStringExtra("source");
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {

            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        apiService = ApiClient.getClient().create(ApiInterface.class);

        loadFirstPage();
    }


    private void loadFirstPage() {

        callNewsApi(currentPage).enqueue(new Callback<articlesResponse>() {
            @Override
            public void onResponse(Call<articlesResponse> call, Response<articlesResponse> response) {
                results = fetchResults(response);
                progressBar.setVisibility(View.GONE);
                Log.i("api response","results body contains "+ results);
                adapter.addAll(results);

                if (adapter.getItemCount() < 1000)
                    adapter.addLoadingFooter();

                else {
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<articlesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void loadNextPage() {

        callNewsApi(currentPage).enqueue(new Callback<articlesResponse>() {
            @Override
            public void onResponse(Call<articlesResponse> call, Response<articlesResponse> response) {
                adapter.removeLoadingFooter();
                isLoading = false;
                results = fetchResults(response);
                adapter.addAll(results);

                if (adapter.getItemCount() < 1050){
                    Log.i("api response","item count "+ adapter.getItemCount());
                    adapter.addLoadingFooter(); }
                else {
                    Log.i("api response","item count is "+ adapter.getItemCount());
                    isLastPage = true;
                }
            }

            @Override
            public void onFailure(Call<articlesResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private Call<articlesResponse> callNewsApi(int currentPage) {
        return apiService.getItems(source,currentPage,API_KEY);
    }

    private List<Item> fetchResults(Response<articlesResponse> response) {
        articlesResponse articlesResponse = response.body();

        if (articlesResponse != null) {

            return articlesResponse.getArticles();
        }
        return results;
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.action_filter) {
            FragmentManager fragMan = getSupportFragmentManager();
            SettingsFragment fragment = new SettingsFragment();
            fragment.show(fragMan, "fragment_settings");
            return true;
        }if (item.getItemId() == R.id.action_favorites){
            Intent intent = new Intent(getApplicationContext(), FavoritesActivity.class);
            getApplicationContext().startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
