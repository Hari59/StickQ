package com.mygdx.game;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MenuActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private int lvl = -1;
    private ImageButton btnDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        addListenerOnButton();
    }

    public void toMenu(View V)
    {
        //loads the game play screen
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void toLevelSelect(View V)
    {
        //loads the game play screen
        Intent intent = new Intent(this, LevelSelect.class);
        startActivity(intent);
    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.enemyRadio);
        btnDisplay = (ImageButton) findViewById(R.id.battle);

        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) findViewById(selectedId);
                lvl = radioGroup.indexOfChild(radioButton);

                if(lvl == -1){
                    Toast.makeText(MenuActivity.this,
                            "Please select a level", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent=new Intent(v.getContext(),BattleActivity.class);
                    intent.putExtra("levelcompleted", lvl);
                    startActivity(intent);
                }

            }

        });

    }


}
