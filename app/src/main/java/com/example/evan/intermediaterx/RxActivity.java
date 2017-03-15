package com.example.evan.intermediaterx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rx.Subscription;
import rx.internal.util.SubscriptionList;

/**
 * Created by Evan on 3/14/2017.
 */

public class RxActivity extends AppCompatActivity {

    protected SubscriptionList shortList = new SubscriptionList();
    protected SubscriptionList longList = new SubscriptionList();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        shortList.clear();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        longList.clear();
    }

    protected void shortSubscribe(Subscription subscription) {
        shortList.add(subscription);
    }

    protected void longSubscribe(Subscription subscription) {
        longList.add(subscription);
    }
}
