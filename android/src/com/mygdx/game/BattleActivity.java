package com.mygdx.game;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class BattleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        ImageView imageView = (ImageView) findViewById(R.id.enemy1);
        ImageView imageView2 = (ImageView) findViewById(R.id.enemy2);
        ImageView imageView3 = (ImageView) findViewById(R.id.enemy3);

        Glide.with(this).load("https://thumbs.gfycat.com/AnotherCourageousBluemorphobutterfly-small.gif").into(imageView);
        Glide.with(this).load("https://thumbs.gfycat.com/AnotherCourageousBluemorphobutterfly-small.gif").into(imageView2);
        Glide.with(this).load("https://thumbs.gfycat.com/AnotherCourageousBluemorphobutterfly-small.gif").into(imageView3);
    }
}
