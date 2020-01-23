package com.rdc.ascratechassignment.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.rdc.ascratechassignment.R;
import com.rdc.ascratechassignment.databinding.ActivityHomeScreenBinding;
import com.rdc.ascratechassignment.model.interfaces.Listner;

public class HomeScreenActivity extends AppCompatActivity implements Listner
{
	ActivityHomeScreenBinding activityHomeScreenBinding;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		activityHomeScreenBinding = DataBindingUtil.setContentView(this, R.layout.activity_home_screen);
		activityHomeScreenBinding.setClickListner(this);
	}

	@Override
	public void olclick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnEditProfile:
				Intent intentEditProf = new Intent(this, RegisterActivity.class);
				intentEditProf.putExtra("ISFROM", "HomeScreen");
				startActivity(intentEditProf);
				break;
			case R.id.btnBookTicket:
				break;
			case R.id.btnReviewTicket:
				break;
			default:
				break;

		}
	}
}
