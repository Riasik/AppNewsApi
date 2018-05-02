package com.ryasik.appnewsapi.DataBase;

import com.ryasik.appnewsapi.Model.Item;

import java.util.List;

public interface IDataBase {

    void createArticle(Item article);
    List getFavoritesArticles();
}
