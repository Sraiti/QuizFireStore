package com.example.firestorequiz.Ads;


import android.content.Context;
import android.os.Bundle;

import com.example.firestorequiz.R;
import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class ads {


    public static InterstitialAd interstitialAd;
    public static Context context;
    public static ads adsInstence;

    public static ads getInstance(Context context) {
        if (adsInstence == null) {
            adsInstence = new ads();
            interstitialAd = new InterstitialAd(context);
            interstitialAd.setAdUnitId(context.getResources().getString(R.string.interstitial));
        }


        return adsInstence;

    }

    public static AdRequest gdpr(Context context) {
        AdRequest request;
        if (ConsentInformation.getInstance(context).isRequestLocationInEeaOrUnknown()) {
            if (ConsentInformation.getInstance(context).getConsentStatus() == ConsentStatus.PERSONALIZED) {
                request = new AdRequest.Builder().build();
            } else {
                Bundle extras = new Bundle();
                extras.putString("npa", "1");
                request = new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build();
            }
        } else {
            request = new AdRequest.Builder().build();
        }
        return request;
    }

    public void loadInter() {
        if (!interstitialAd.isLoaded())
            interstitialAd.loadAd(gdpr(context));
    }

    public void showads() {
        if (interstitialAd.isLoaded())
            interstitialAd.show();
        loadInter();

    }

    public AdRequest banneReq() {
        return gdpr(context);
    }

    public InterstitialAd interstitialAdInstence() {
        return interstitialAd;
    }
}