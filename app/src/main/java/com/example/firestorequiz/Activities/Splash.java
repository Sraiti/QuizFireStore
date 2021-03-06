package com.example.firestorequiz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.firestorequiz.Ads.ConsentSDK;
import com.example.firestorequiz.R;

public class Splash extends AppCompatActivity {


    ConsentSDK consentSDK;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final Intent intent = new Intent(Splash.this, Home.class);

        consentSDK = new ConsentSDK.Builder(this)
                .addPrivacyPolicy(getString(R.string.url_privacy)) // Add your privacy policy url
                .addPublisherId(getString(R.string.publisher_id)) // Add your admob publisher id
                .build();

        consentSDK.checkConsent(new ConsentSDK.ConsentCallback() {
            @Override
            public void onResult(boolean isRequestLocationInEeaOrUnknown) {

            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                startActivity(intent);

                finish();

            }
        }, 6500);
    }
}
