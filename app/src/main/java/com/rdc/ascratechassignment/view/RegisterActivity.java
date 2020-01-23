package com.rdc.ascratechassignment.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.rdc.ascratechassignment.R;
import com.rdc.ascratechassignment.databinding.ActivityRegisterBinding;
import com.rdc.ascratechassignment.model.User;
import com.rdc.ascratechassignment.model.commons.Utility;
import com.rdc.ascratechassignment.model.database.LoginTable;
import com.rdc.ascratechassignment.model.interfaces.Listner;
import com.rdc.ascratechassignment.viewModel.LoginViewModel;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity implements Listner
{
	private static final int CAMERA_REQUEST = 1888;

	private static final int CAMERA_PERMISSION_CODE = 100;

	private ActivityRegisterBinding activityRegisterBinding;

	private LoginViewModel loginViewModel;

	private boolean isEditProfile;

	private Bitmap bmImage;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		activityRegisterBinding = DataBindingUtil.setContentView(this, R.layout.activity_register);
		loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
		if (getIntent().getExtras().get("ISFROM") != null)
		{
			String isFrom = getIntent().getExtras().getString("ISFROM");
			if (isFrom.equalsIgnoreCase("HomeScreen"))
			{
				isEditProfile = true;
				getValueFromDb();
			}
		}
	}

	private void getValueFromDb()
	{
		try
		{
			activityRegisterBinding.etName.setEnabled(false);
			activityRegisterBinding.etMobileNo.setEnabled(false);
			activityRegisterBinding.etRegPassword.setVisibility(View.GONE);
			activityRegisterBinding.etRegConfirmPassword.setVisibility(View.GONE);
			User user = new User();
			activityRegisterBinding.etName.setText(user.getName());
			activityRegisterBinding.etMobileNo.setText(user.getMobileNo());
			activityRegisterBinding.etAddress.setText(user.getAddress());
			activityRegisterBinding.etUserName.setText(user.getUsername());
			activityRegisterBinding.btnRegRegister.setVisibility(View.GONE);
			activityRegisterBinding.btnRegRegister.setText("Update");

			byte[] bitmapdata = user.getProfilePic();
			Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata, 0, bitmapdata.length);
			activityRegisterBinding.ivProfilePic.setImageBitmap(bitmap);
			bmImage = bitmap;
			loginViewModel.updateUserDetails(user);

			activityRegisterBinding.etAddress.addTextChangedListener(new TextWatcher()
			{

				public void onTextChanged(CharSequence s, int start, int before, int count)
				{
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after)
				{

				}

				@Override
				public void afterTextChanged(Editable s)
				{
					activityRegisterBinding.btnRegRegister.setVisibility(View.VISIBLE);
				}
			});

		}
		catch (Exception e)
		{
			Log.e("Log---> ", "getValueFromDb: " + e);
			e.printStackTrace();
		}
	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	public void olclick(View view)
	{
		switch (view.getId())
		{
			case R.id.btnRegRegister:
				try
				{
					if (areValidFields())
					{
						User user = new User();
						user.setName(activityRegisterBinding.etName.getText().toString().trim());
						user.setAddress(activityRegisterBinding.etAddress.getText().toString().trim());
						user.setMobileNo(activityRegisterBinding.etMobileNo.getText().toString().trim());
						user.setUsername(activityRegisterBinding.etUserName.getText().toString().trim());
						user.setPassword(activityRegisterBinding.etRegPassword.getText().toString().trim());
						user.setProfilePic(Utility.imageinBytes(bmImage));
						loginViewModel.addLogin(user);

						Toast.makeText(this, "user Added  Successfully", Toast.LENGTH_SHORT).show();
						gotoLoginScreen();
					}
				}
				catch (Exception e)
				{
					Log.e("Log---", "Error" + e);
				}
				break;
			case R.id.ivProfilePic:
				captureImage();
				break;
			default:
				break;
		}

	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	private void captureImage()
	{
		if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
		{
			requestPermissions(new String[] { Manifest.permission.CAMERA }, CAMERA_PERMISSION_CODE);
		}
		else
		{
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_REQUEST);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == CAMERA_PERMISSION_CODE)
		{
			if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
			{
				Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
				Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(cameraIntent, CAMERA_REQUEST);
			}
			else
			{
				Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK)
		{
			if (Objects.requireNonNull(data.getExtras()).get("data") != null)
			{
				Bitmap photo = (Bitmap) data.getExtras().get("data");
				activityRegisterBinding.ivProfilePic.setImageBitmap(photo);
				bmImage = photo;
				if (isEditProfile)
				{
					activityRegisterBinding.btnRegRegister.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	private void gotoLoginScreen()
	{
		Intent intent = new Intent(this, LoginActivity.class);
		intent.putExtra("USERNAME", activityRegisterBinding.etUserName.getText().toString().trim());
		setResult(101, intent);

		activityRegisterBinding.etName.setText("");
		activityRegisterBinding.etMobileNo.setText("");
		activityRegisterBinding.etAddress.setText("");
		activityRegisterBinding.etUserName.setText("");
		activityRegisterBinding.etRegPassword.setText("");
		activityRegisterBinding.etRegConfirmPassword.setText("");
		finish();

	}

	private boolean areValidFields()
	{
		if (activityRegisterBinding.etName.getText().toString().isEmpty())
		{
			activityRegisterBinding.etName.requestFocus();
			Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (activityRegisterBinding.etAddress.getText().toString().isEmpty())
		{
			activityRegisterBinding.etAddress.requestFocus();
			Toast.makeText(this, "Enter Addredd", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (activityRegisterBinding.etMobileNo.getText().toString().isEmpty())
		{
			activityRegisterBinding.etMobileNo.requestFocus();
			Toast.makeText(this, "Enter Mobile no", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (activityRegisterBinding.etUserName.getText().toString().trim().isEmpty())
		{
			activityRegisterBinding.etUserName.setError("Enter User Name");
			return false;
		}
		String password = activityRegisterBinding.etRegPassword.getText().toString().trim();
		if (password.isEmpty())
		{
			activityRegisterBinding.etRegPassword.requestFocus();
			Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
			return false;
		}
		String confirmPassword = activityRegisterBinding.etRegConfirmPassword.getText().toString().trim();

		if (confirmPassword.isEmpty())
		{
			activityRegisterBinding.etRegConfirmPassword.requestFocus();
			Toast.makeText(this, "Enter Confirm password", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (!password.equalsIgnoreCase(confirmPassword))
		{
			activityRegisterBinding.etRegPassword.requestFocus();
			Toast.makeText(this, "Password Not Matched", Toast.LENGTH_SHORT).show();
			return false;
		}
		if (bmImage == null)
		{
			Toast.makeText(this, "Capture Image", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
}
