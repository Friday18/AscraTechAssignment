package com.rdc.ascratechassignment.model.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.rdc.ascratechassignment.model.User;
import com.rdc.ascratechassignment.model.database.Ascra_SqliteDatabase;
import com.rdc.ascratechassignment.model.database.LoginDatabase;
import com.rdc.ascratechassignment.model.database.LoginTable;
import com.rdc.ascratechassignment.model.interfaces.LoginTableDao;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoginRepositories
{
	private LiveData<List<LoginTable>> listLiveDataLogin;

	private LoginTableDao loginTableDao;

	private Ascra_SqliteDatabase ascra_sqliteDatabase;

	public LoginRepositories(@NonNull Application application)
	{
		LoginDatabase loginDatabase = LoginDatabase.getDatabase(application);
		loginTableDao = loginDatabase.loginTableDao();

		ascra_sqliteDatabase = new Ascra_SqliteDatabase(application);
		//listLiveDataLogin=ascra_sqliteDatabase.checkIsLoginSuccess();
	}

	public LiveData<List<LoginTable>> getListLiveDataLogin()
	{
		return listLiveDataLogin;
	}

	public void addLogin(User user)
	{
		try
		{
			ascra_sqliteDatabase.addUser(user);
		}
		catch (Exception e)
		{
			Log.e("Log--> ", "Error" + e);
		}
	}
	/*
		public class AddLogin extends AsyncTask<User, Void, Void>
		{
	
			@Override
			protected Void doInBackground(User user)
			{
				ascra_sqliteDatabase.addUser(user);
				return null;
			}
		}*/

	public User checkLogin(User user)
	{
		try
		{
			return new CheckLogin(user).execute().get();
		}
		catch (ExecutionException e)
		{
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public class CheckLogin extends AsyncTask<Void, Void, User>
	{
		User user;

		CheckLogin(User user)
		{
			this.user = user;
		}

		@Override
		protected User doInBackground(Void... Void)
		{
			User data = ascra_sqliteDatabase.checkIsLoginSuccess(user);
			return data;
		}
	}

	public void updateUserDetails(User user)
	{
		ascra_sqliteDatabase.updateUser(user);
	}
}
