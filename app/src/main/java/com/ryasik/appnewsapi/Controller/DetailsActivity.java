package com.ryasik.appnewsapi.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.ryasik.appnewsapi.R;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {

    private TextView articleTitleTV, articleDescriptionTV, articleAuthorTV;
    private ImageView articlePictureIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = getIntent();
        String articleTitle = intent.getStringExtra("article_name");
        String articleDescription = intent.getStringExtra("article_details");
        String articlePictureURL = intent.getStringExtra("article_picture");
        String articleAuthor = intent.getStringExtra("article_author");

        articleTitleTV = findViewById(R.id.title_tv_tag);
        articleDescriptionTV = findViewById(R.id.article_details_tv);
        articlePictureIV = findViewById(R.id.article_image);
        Picasso.with(this)
                .load(articlePictureURL)
                .into(articlePictureIV);
        articleTitleTV.setText(articleTitle);
        articleDescriptionTV.setText(articleDescription);
    }
}
