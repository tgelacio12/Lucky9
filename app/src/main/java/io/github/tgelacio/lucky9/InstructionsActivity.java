/*
Lucky 9
PROG3210 Final Project

Activity Name: InstructionsActivity
Purpose:
    Displays the instructions

Revision History
    Tonnicca Gelacio, 2019-12-04: Created
    Tonnicca Gelacio, 2019-12-04: Code and UI Updated
    Tonnicca Gelacio, 2019-12-06: Code and UI Updated
    Tonnicca Gelacio, 2019-12-07: Code and UI Updated
    Tonnicca Gelacio, 2019-12-08: Code and UI Updated
 */

package io.github.tgelacio.lucky9;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class InstructionsActivity extends AppCompatActivity
        implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        setTitle("How to Play");

        // Declarations
        Button btnStartGame;

        // Initializations
        btnStartGame = (Button) findViewById(R.id.btnStartGame);

        // Set onClickListener
        btnStartGame.setOnClickListener(this);
    }

    ///
    /// Event handler of onCreateOptionsMenu method
    ///
    ///
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_instructions, menu);
        return true;
    }

    ///
    /// Event handler of onOptionsItemSelected method
    ///
    ///
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.mnuHome:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            case R.id.mnuStartGame:
                startActivity(new Intent(getApplicationContext(), GameActivity.class));
                return true;
            case R.id.mnuLeaderboard:
                startActivity(new Intent(getApplicationContext(), LeaderboardActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartGame:

                // Save player name to intent
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);

                //Start Game
                startActivity(intent);

                break;
            default:
                break;
        }
    }
}
