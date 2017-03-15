package com.example.evan.intermediaterx;

import android.view.View;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Evan on 3/14/2017.
 */

public class RxView {

    public static Observable<Void> clicked(View view) {
        return Observable.create(new RxViewClickedSubscription(view));
    }

    public static class RxViewClickedSubscription implements Observable.OnSubscribe<Void> {

        private View view;

        public RxViewClickedSubscription(View view) {
            this.view = view;
        }

        @Override
        public void call(Subscriber<? super Void> subscriber) {
            view.setOnClickListener(v -> subscriber.onNext(null));
        }
    }
}
