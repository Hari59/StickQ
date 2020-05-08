package com.mygdx.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LevelSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_select);
    }

    public void goLevelOne(View view) {
        Intent intent = new Intent(this, AndroidLauncher.class);
        intent.putExtra("level", 1);
        startActivity(intent);
    }

    public void goLevelTwo(View view){
        Intent intent = new Intent(this, AndroidLauncher.class);
        intent.putExtra("level", 2);
        startActivity(intent);
    }

    public void goHome(View view){
        Intent intent = new Intent(this, MenuActivity.class);
        startActivity(intent);
    }
}
