package com.mygdx.game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.StickQuestGame;

public class AndroidLauncher extends AndroidApplication {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int levelNum = getIntent().getIntExtra("level", 0);

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new StickQuestGame(levelNum), config);
    }
}