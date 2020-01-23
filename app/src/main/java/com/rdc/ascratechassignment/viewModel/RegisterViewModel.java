package com.rdc.ascratechassignment.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rdc.ascratechassignment.model.User;
import com.rdc.ascratechassignment.model.database.LoginTable;
import com.rdc.ascratechassignment.model.repositories.LoginRepositories;

import java.util.List;


public class RegisterViewModel extends AndroidViewModel
{
	private LiveData<List<LoginTable>> listLiveDataLogin;

	LoginRepositories loginRepositories;

	public RegisterViewModel(@NonNull Application application)
	{
		super(application);
		loginRepositories = new LoginRepositories(application);
		listLiveDataLogin = loginRepositories.getListLiveDataLogin();

	}

	public LiveData<List<LoginTable>> getListLiveDataLogin()
	{
		return listLiveDataLogin;
	}

	public void addLogin(User user)
	{
		loginRepositories.addLogin(user);
	}
}
