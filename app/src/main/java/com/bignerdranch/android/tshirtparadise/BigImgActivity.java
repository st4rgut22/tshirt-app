package com.bignerdranch.android.tshirtparadise;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BigImgActivity extends AppCompatActivity{
    private static String BIGIMG = "com.bignerdranch.android.bigImg";
    public static Intent newIntent(Context context,String url){
        Intent i = new Intent(context,BigImgActivity.class);
        i.putExtra(BIGIMG,url);
        return i;
    }

    @Override
    protected void onCreate(Bundle onSavedInstanceState){
        super.onCreate(onSavedInstanceState);
        Bundle bundle = getIntent().getExtras();
        String url = bundle.getString(BIGIMG);

        setContentView(R.layout.activity_big_img);
        ImageView bigOne = findViewById(R.id.bigImg);

        Glide.with(BigImgActivity.this)
                .load(url)
                .into(bigOne);

    }
}
