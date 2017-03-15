package com.example.evan.intermediaterx;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.internal.util.SubscriptionList;

public class LoginActivity extends AppCompatActivity {

    SubscriptionList list = new SubscriptionList();
    
    Observable<String> password;
    Observable<String> email;
    Observable<Void> loginClicked;
    Observable<Boolean> loginEnabled;
    
    Observable<LoginRequest> request;
    
    @Bind(R.id.email)
    TextView emailTv;
    @Bind(R.id.password)
    TextView passwordTv;
    @Bind(R.id.login)
    View loginButton;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        password = RxTextView.textChanges(passwordTv).map(x -> x.toString());
        email = RxTextView.textChanges(emailTv).map(x -> x.toString());
        loginClicked = RxView.clicked(loginButton).share();

        loginEnabled = Observable.combineLatest(password.map(x -> x.length() > 0), email.map(x -> x.length() > 0), (x, y) -> x && y);

        request = Observable.combineLatest(password, email, (p, e) -> new LoginRequest(e, p));
        
        loginClicked.withLatestFrom(loginEnabled, (x, y) -> y)
                .filter(x -> x)
                .flatMap(x -> request)
                .flatMap(x -> login(x))
                .subscribe(x -> Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show());

        loginClicked.withLatestFrom(loginEnabled, (x, y) -> y)
                .filter(x -> !x)
                .subscribe(x -> Toast.makeText(this, "Fill out form", Toast.LENGTH_SHORT).show());
    }
    
    private Observable<Boolean> login(LoginRequest request) {
        return Observable.fromCallable(() -> {
            Log.d("Login", "Login request performed");
            return true;
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
