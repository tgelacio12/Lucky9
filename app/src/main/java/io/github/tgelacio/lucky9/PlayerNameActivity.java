/*
Lucky 9
PROG3210 Final Project

Activity Name: PlayerNameActivity
Purpose:
    Displays form that asks for player's name

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
import android.widget.EditText;
import android.widget.TextView;

public class PlayerNameActivity extends AppCompatActivity
        implements View.OnClickListener {

    // Declarations
    private Button btnStartGame;
    private EditText txtPlayerName;
    private TextView lblPlayerNameError;
    String playerNameError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_name);

        // Initialize from intent
        Intent intent = getIntent();
        playerNameError = intent.getStringExtra("playerNameError");

        // Initializations
        btnStartGame = (Button) findViewById(R.id.btnStartGame);
        lblPlayerNameError = (TextView) findViewById(R.id.lblPlayerNameError);
        txtPlayerName = (EditText) findViewById(R.id.txtPlayerName);

        // Set onClickListener
        btnStartGame.setOnClickListener(this);

        // Set text for labels
        if (playerNameError == null)
        {
            lblPlayerNameError.setText("");
        }
        else
        {
            lblPlayerNameError.setText(playerNameError);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartGame:

                // Save player name to intent
                Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                intent.putExtra("playerName", txtPlayerName.getText().toString());

                //Start Game
                startActivity(intent);

                break;
            default:
                break;
        }
    }

    ///
    /// Event handler of onCreateOptionsMenu method
    ///
    ///
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_player_name, menu);
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
            case R.id.mnuLeaderboard:
                startActivity(new Intent(getApplicationContext(), LeaderboardActivity.class));
                return true;
            case R.id.mnuInstructions:
                startActivity(new Intent(getApplicationContext(), InstructionsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
