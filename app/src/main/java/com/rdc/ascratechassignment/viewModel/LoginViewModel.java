package com.rdc.ascratechassignment.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.rdc.ascratechassignment.model.User;
import com.rdc.ascratechassignment.model.database.LoginTable;
import com.rdc.ascratechassignment.model.repositories.LoginRepositories;

import java.util.List;

public class LoginViewModel extends AndroidViewModel
{
	private LiveData<List<LoginTable>> listLiveDataLogin;

	LoginRepositories loginRepositories;

	public LoginViewModel(@NonNull Application application)
	{
		super(application);
		loginRepositories = new LoginRepositories(application);
	}

	public User checkLogin(User user)
	{
		return loginRepositories.checkLogin(user);
	}

	public void addLogin(User user)
	{
		loginRepositories.addLogin(user);
	}

	public void updateUserDetails(User user)
	{
		loginRepositories.updateUserDetails(user);
	}
}
