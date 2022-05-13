/*
Lucky 9
PROG3210 Final Project

Activity Name: SplashActivity
Purpose:
    Displays the splash screen

Revision History
    Tonnicca Gelacio, 2019-12-08: Created
    Tonnicca Gelacio, 2019-12-08: Code and UI Updated
 */

package io.github.tgelacio.lucky9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent openMainActivity = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(openMainActivity);
                finish();
            }
        }, 2000);
    }
}
