/*
Lucky 9
PROG3210 Final Project

Activity Name: MainActivity
Purpose:
    Displays the application's main menu

Revision History
    Tonnicca Gelacio, 2019-12-01: Created
    Tonnicca Gelacio, 2019-12-01: Code Updated
    Tonnicca Gelacio, 2019-12-04: Code and UI Updated
    Tonnicca Gelacio, 2019-12-06: Code and UI Updated
    Tonnicca Gelacio, 2019-12-07: Code and UI Updated
    Tonnicca Gelacio, 2019-12-08: Code and UI Updated
 */

package io.github.tgelacio.lucky9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declarations
        Button btnStartGame, btnLeaderboard, btnHowToPlay;

        // Initializations
        btnStartGame = (Button) findViewById(R.id.btnStartGame);
        btnLeaderboard = (Button) findViewById(R.id.btnLeaderboard);
        btnHowToPlay = (Button) findViewById(R.id.btnHowToPlay);

        // Set buttons' onClickListener
        btnStartGame.setOnClickListener(this);
        btnLeaderboard.setOnClickListener(this);
        btnHowToPlay.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartGame:
                startActivity(new Intent(getApplicationContext(), GameActivity.class));
                break;
            case R.id.btnLeaderboard:
                startActivity(new Intent(getApplicationContext(), LeaderboardActivity.class));
                break;
            case R.id.btnHowToPlay:
                startActivity(new Intent(getApplicationContext(), InstructionsActivity.class));
                break;
            default:
                break;
        }
    }
}
