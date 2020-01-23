package com.rdc.ascratechassignment.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.rdc.ascratechassignment.R;
import com.rdc.ascratechassignment.databinding.ActivitySplashScreenBinding;
import com.rdc.ascratechassignment.model.interfaces.Listner;
import com.rdc.ascratechassignment.viewModel.LoginViewModel;

import java.util.HashMap;
import java.util.Map;


public class SplashScreenActivity extends AppCompatActivity implements Listner {

    private ActivitySplashScreenBinding binding;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(SplashScreenActivity.this, R.layout.activity_splash_screen);
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);

        Map<String, Boolean> hm = new HashMap<>();

        String strLoginName = "";
        boolean isLogin = false;
        assert hm != null;
        for (Map.Entry entry : hm.entrySet()) {
            strLoginName = entry.getKey().toString();
            isLogin = (boolean) entry.getValue();
            break;
        }
        final boolean isLogins = isLogin;
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if (isLogins) {
                    //    startActivity(new Intent(SplashScreenActivity.this, HomeScreenActivity.class));
                    return;
                }
                startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));

            }
        }, 2500);
    }


    @Override
    public void olclick(View view) {

    }
}
