package com.ryasik.appnewsapi.Controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ryasik.appnewsapi.Adapters.PaginationAdapter;
import com.ryasik.appnewsapi.DataBase.BaseHelper;
import com.ryasik.appnewsapi.Model.Item;
import com.ryasik.appnewsapi.R;

import java.util.List;


public class FavoritesActivity extends AppCompatActivity {

    PaginationAdapter adapter;
    private RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    private List<Item> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_favorite);
        adapter = new PaginationAdapter(this);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        onLoadList();
        adapter.addAll(list);
        recyclerView.setAdapter(adapter);
    }
    private void onLoadList(){
        BaseHelper baseHelper = new BaseHelper(getApplicationContext());
        list =  baseHelper.getFavoritesArticles();
    }
}
