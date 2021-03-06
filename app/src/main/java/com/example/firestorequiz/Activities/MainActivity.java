package com.example.firestorequiz.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.firestorequiz.DB.CategoryDbHelper;
import com.example.firestorequiz.Model.Category;
import com.example.firestorequiz.MusicBackground.MediaPlayerPresenter;
import com.example.firestorequiz.R;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    public static final int NUMBER_OF_ADS = 3;


    private AdLoader adLoader;


    private List<Object> mRecyclerViewItems = new ArrayList<>();


    private List<UnifiedNativeAd> mNativeAds = new ArrayList<>();

    SharedPreferences preferences;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();
        player.pauseMusic();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        player = MediaPlayerPresenter.getInstance(MainActivity.this);
        // Create new fragment to display a progress spinner while the data set for the

        // Update the RecyclerView item's list with menu items.
        GetData(new FireStoreCallback() {
            @Override
            public void OnCallBack(List<Object> List) {
                new AddingCategories(MainActivity.this).execute();
            }
        });
        loadNativeAds();
        // Update the RecyclerView item's list with native ads.
        //    loadNativeAds();


    }

    private void loadNativeAds() {
        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.ad_unit_id));
        adLoader = builder.forUnifiedNativeAd(
                new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        // A native ad loaded successfully, check if the ad loader has finished loading
//                        // and if so, insert the ads into the list.
                        mNativeAds.add(unifiedNativeAd);
                        if (!adLoader.isLoading()) {
                            insertAdsInMenuItems();
                        }
                    }
                }).withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        // A native ad failed to load, check if the ad loader has finished loading
                        // and if so, insert the ads into the list.
                        Log.e("MainActivity", "The previous native ad failed to load. Attempting to"
                                + " load another.");
                        if (!adLoader.isLoading()) {
                            insertAdsInMenuItems();
                        }
                    }
                }).build();

        // Load the Native ads.
        adLoader.loadAds(new AdRequest.Builder().build(), NUMBER_OF_ADS);
    }

    public List<Object> getRecyclerViewItems() {
        return mRecyclerViewItems;
    }

    private void insertAdsInMenuItems() {
        if (mNativeAds.size() <= 0) {
            return;
        }

        int offset = (mRecyclerViewItems.size() / mNativeAds.size()) + 1;
        int index = 0;
        for (UnifiedNativeAd ad : mNativeAds) {

            //Comment this to close the native Ads

            index = index + 2;
        }
        loadMenu();
    }

    private void loadMenu() {
        // Create new fragment and transaction
        Fragment newFragment = new CategoryFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.fragment_container, newFragment);
        transaction.addToBackStack(null);

        SpinKitView sp = findViewById(R.id.spinKitView);
        sp.setVisibility(View.GONE);
        // Commit the transaction
        try {
            transaction.commit();

        } catch (Exception ex) {
            try {
                transaction.commitAllowingStateLoss();

            } catch (Exception edx) {
                finish();
            }
        }
        // transaction.commit();
    }


    private void GetData(final FireStoreCallback callback) {


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("TAG", document.getId() + " => " + document.getData());
                                mRecyclerViewItems.add(document.toObject(Category.class));
                            }

                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        callback.OnCallBack(mRecyclerViewItems);
                    }
                });
    }

    MediaPlayerPresenter player;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (hasFocus && preferences.getBoolean("music", true)) {
            player.playMusic();
        }

    }


    private interface FireStoreCallback {
        void OnCallBack(List<Object> List);
    }

    public class AddingCategories extends AsyncTask<Void, Void, Void> {
        private final Context mContext;

        public AddingCategories(Context context) {
            super();
            this.mContext = context;
        }

        protected Void doInBackground(Void... params) {
            // using this.mContext
            CategoryDbHelper categoryDbHelper = new CategoryDbHelper(mContext);

            for (Object item : mRecyclerViewItems) {

                if (item instanceof Category) {
                    Category cat = new Category(((Category) item).getCategoryId(), ((Category) item).getCategoryName()
                            , ((Category) item).getCategoryImage());
                    categoryDbHelper.AddCategories(cat);
                }
            }

            return null;
        }
    }

}
