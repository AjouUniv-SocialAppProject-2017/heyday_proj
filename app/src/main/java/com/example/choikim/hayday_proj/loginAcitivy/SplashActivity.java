package com.example.choikim.hayday_proj.loginAcitivy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.choikim.hayday_proj.R;

/**
 * Created by khy12 on 2017-11-18.
 */

public class SplashActivity extends Activity {
    int SPLASH_TIME=4000;

    @Override protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.acrivity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                overridePendingTransition(0,android.R.anim.fade_in);
                startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                finish();
            }
        },SPLASH_TIME);
    }

}
