package com.example.evan.intermediaterx;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.*;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.internal.util.SubscriptionList;

public class LoginActivity extends AppCompatActivity {

    SubscriptionList list = new SubscriptionList();
    
    @Bind(R.id.email)
    TextView email;
    @Bind(R.id.password)
    TextView password;
    @Bind(R.id.login)
    View loginButton;

    LoginViewModel viewModel = new LoginViewModel();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        viewModel.init(this);

        viewModel.loginEnabled.subscribe(loginButton::setEnabled);
        viewModel.loginSuccessful.subscribe(x -> Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show());
    }

    public Observable<String> getPasswordAsObservable() {
        return RxTextView.textChanges(password).map(x -> x.toString());
    }

    public Observable<String> getEmailAsObservable() {
        return RxTextView.textChanges(email).map(x -> x.toString());
    }

    public Observable<Void> getLoginClickedAsObservable() {
        return RxView.clicks(loginButton);
    }
}
