package com.rdc.ascratechassignment.view;

import java.util.Collections;
import java.util.Objects;

import com.google.android.material.snackbar.Snackbar;
import com.rdc.ascratechassignment.BuildConfig;
import com.rdc.ascratechassignment.R;
import com.rdc.ascratechassignment.databinding.LoginActivityLayoutBinding;
import com.rdc.ascratechassignment.model.User;
import com.rdc.ascratechassignment.model.interfaces.Listner;
import com.rdc.ascratechassignment.viewModel.LoginViewModel;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import org.jetbrains.annotations.Nullable;

public class LoginActivity extends AppCompatActivity implements Listner
{
	LoginActivityLayoutBinding binding;

	private LoginViewModel loginViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestMultiplePermission();
		binding = DataBindingUtil.setContentView(LoginActivity.this, R.layout.login_activity_layout);
		binding.setClickListner(this);

		loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
		if (BuildConfig.DEBUG)
		{
			binding.etEmail.setText("swap@g.c");
			binding.etPassword.setText("12345");
		}

	}

	private void requestMultiplePermission()
	{

		// Creating String Array with Permissions.
		ActivityCompat.requestPermissions(this,
				new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE

				}, 1);

	}

	@Override
	public void olclick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnLogin:

				String email = binding.etEmail.getText().toString().trim();
				String password = binding.etPassword.getText().toString().trim();
				if (TextUtils.isEmpty(email))
				{
					Toast.makeText(this, "enter email", Toast.LENGTH_LONG).show();
					return;
				}
				if (TextUtils.isEmpty(password))
				{
					Toast.makeText(this, "enter password", Toast.LENGTH_LONG).show();
					return;
				}
				Log.d("\n\n\n", TextUtils.join(password, Collections.singleton(email)));
				User user = new User();
				if (loginViewModel.checkLogin(user) != null)
				{
					Snackbar.make(binding.getRoot(), "Login Successfull", Snackbar.LENGTH_SHORT).show();
				}
				else
				{
					Snackbar.make(binding.getRoot(), "please try again", Snackbar.LENGTH_SHORT).show();
					return;
				}

				startActivity(new Intent(this, HomeScreenActivity.class));

				break;

			case R.id.btnRegister:
				startActivityForResult(new Intent(this, RegisterActivity.class), 101);
				break;
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == 101 && resultCode == 101)
		{
			assert data != null;
			binding.etEmail.setText(Objects.requireNonNull(data.getExtras()).getString("USERNAME"));
			binding.etPassword.requestFocus();
			Toast.makeText(this, "Please Login", Toast.LENGTH_SHORT).show();
		}
	}
}
