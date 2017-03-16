package com.example.evan.intermediaterx;

import android.util.Log;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import rx.Observable;

/**
 * Created by Evan on 3/14/2017.
 */

public class LoginViewModel {

    private Observable<String> password;
    private Observable<String> email;
    private Observable<Void> loginClicked;
    private Observable<LoginRequest> request;

    public Observable<Boolean> loginEnabled;
    public Observable<Void> loginSuccessful;

    public void init(LoginActivity view) {

        password = view.getPasswordAsObservable();
        email = view.getEmailAsObservable();
        loginClicked = view.getLoginClickedAsObservable();

        loginEnabled = Observable.combineLatest(password.map(x -> x.length() > 0), email.map(x -> x.length() > 0), (x, y) -> x && y);

        request = Observable.combineLatest(password, email, (p, e) -> new LoginRequest(e, p));

        loginSuccessful = loginClicked.withLatestFrom(request, (x, y) -> y)
                .flatMap(x -> login(x));

    }

    private Observable<Void> login(LoginRequest request) {
        return Observable.fromCallable(() -> {
            Log.d("Login", "Login request performed");
            return null;
        });
    }

    class LoginRequest {
        private String email;
        private String password;

        public LoginRequest(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }
}
