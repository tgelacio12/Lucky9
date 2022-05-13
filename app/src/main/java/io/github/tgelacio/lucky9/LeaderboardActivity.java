/*
Lucky 9
PROG3210 Final Project

Activity Name: LeaderboardActivity
Purpose:
    Displays the leaderboard

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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class LeaderboardActivity extends AppCompatActivity {

    // Declarations
    private ListView statsListView;
    private PlayerDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        setTitle("Leaderboard");

        // Initializations
        statsListView = (ListView) findViewById(R.id.statsListView);
        db = new PlayerDB(this);

        // update display
        updateDisplay();

    }

    private void updateDisplay() {

        // create a List of Map<String, ?> objects
        ArrayList<HashMap<String, String>> data = db.getPlayersStats();

        // create the resource, from, and to variables
        int resource = R.layout.statslistview_item;
        String[] from = {"name", "winnings", "rounds"};
        int[] to = {R.id.nameTextView, R.id.winningsTextView, R.id.roundsTextView};

        // create and set the adapter
        SimpleAdapter adapter =
                new SimpleAdapter(this, data, resource, from, to);
        statsListView.setAdapter(adapter);

    }

    ///
    /// Event handler of onCreateOptionsMenu method
    ///
    ///
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_leaderboard, menu);
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
            case R.id.mnuInstructions:
                startActivity(new Intent(getApplicationContext(), InstructionsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
