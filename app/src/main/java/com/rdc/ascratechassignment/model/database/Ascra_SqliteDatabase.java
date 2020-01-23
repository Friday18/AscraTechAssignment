package com.rdc.ascratechassignment.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.rdc.ascratechassignment.model.User;

import java.util.ArrayList;

public class Ascra_SqliteDatabase extends SQLiteOpenHelper
{

	// Database Version
	private static final int DATABASE_VERSION = 1;

	private SQLiteDatabase mDb;

	// Database Name
	private static final String DATABASE_NAME = "TrainBookingDB";

	// Table Names
	private static final String USERDETAILS_TABLE = "UserDetails";

	// column names of table USERDETAILS_TABLE
	private static final String USERID = "id";

	private static final String USER_NAME = "userName";

	private static final String KEY_PASSWORD = "password";

	private static final String NAME = "name";

	private static final String ADDRESS = "address";

	private static final String MOBILE_NO = "mobileNo";

	private static final String PROFILE_PIC = "profilePic";

	private static final String TYPE = "wardobetype";

	/*  // Table create statement
	  private static final String CREATE_TABLE_IMAGE = "CREATE TABLE  IF NOT EXISTS " + USERDETAILS_TABLE + "(" + USERID
	          + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PROFILE_PIC + " BLOB," + TROUSERS_IMAGE + " BLOB," + TYPE + ");";
	*/
	//SQL for creating users table
	private static final String REGISTERUSER_QUERY = " CREATE TABLE " + USERDETAILS_TABLE + " ( " + USERID
			+ " INTEGER PRIMARY KEY AUTOINCREMENT , " + NAME + " TEXT, " + ADDRESS + " TEXT, " + MOBILE_NO + " TEXT, "
			+ USER_NAME + " TEXT, " + KEY_PASSWORD + " TEXT," + PROFILE_PIC + " BLOB" + " ) ";

	public Ascra_SqliteDatabase(@Nullable Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		mDb = this.getWritableDatabase();
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		Log.d("Log--->", REGISTERUSER_QUERY);
		db.execSQL(REGISTERUSER_QUERY);
		Log.d("Log---> after", REGISTERUSER_QUERY);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		db.execSQL("DROP TABLE IF EXISTS " + USERDETAILS_TABLE);
		onCreate(db);
	}

	//using this method we can add users to user table
	public void addUser(User user)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put(NAME, user.getName());
		values.put(ADDRESS, user.getAddress());
		values.put(MOBILE_NO, user.getMobileNo());
		values.put(USER_NAME, user.getUsername());
		values.put(KEY_PASSWORD, user.getPassword());
		values.put(PROFILE_PIC, user.getProfilePic());
		long todo_id = db.insert(REGISTERUSER_QUERY, null, values);
	}

	public User checkIsLoginSuccess(User user)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(USERDETAILS_TABLE, new String[] { USERID, USER_NAME, KEY_PASSWORD }, USER_NAME + "=?",
				new String[] { user.getUsername() }, null, null, null);

		if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0)
		{
			User user1 = new User();
			user1.setUsername(cursor.getString(4));
			user1.setPassword(cursor.getString(5));
			if (user.getPassword().equalsIgnoreCase(user1.getPassword()))
			{
				return user;
			}
		}
		return null;
	}

	public boolean updateUser(User user)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		db.execSQL("UPDATE " + USERDETAILS_TABLE + " SET " + ADDRESS + " = " + "'" + user.getAddress() + "' " + "WHERE "
				+ NAME + " = " + "'" + user.getName() + "'");
		return true;
	}

	// Insert the image to the Sqlite DB
	public void insertImage(byte[] imageBytes, String shirtORtrouser)
	{
		ContentValues cv = new ContentValues();
		SQLiteDatabase db = this.getWritableDatabase();
		if (shirtORtrouser.equalsIgnoreCase("shirts"))
		{
			cv.put(PROFILE_PIC, imageBytes);
			cv.put(TYPE, "shirts");
		}
		db.insert(USERDETAILS_TABLE, null, cv);
		db.close();
	}

	// Get the image from SQLite DB
	public Bitmap getShirtFromDB()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		Bitmap bitmap = null;
		Cursor cur = db.rawQuery(
				"SELECT " + PROFILE_PIC + " FROM " + USERDETAILS_TABLE + " WHERE " + TYPE + "= 'shirts'", null);

		if (cur.moveToFirst())
		{
			byte[] b_shirt = cur.getBlob(cur.getColumnIndex(PROFILE_PIC));
			if (b_shirt != null)
			{
				bitmap = BitmapFactory.decodeByteArray(b_shirt, 0, b_shirt.length);
			}
			return bitmap;
		}
		cur.close();
		db.close();
		return null;
	}

	/*// Get the image from SQLite DB
	public ArrayList<Pojo_clothes> getAllShirtsFromDB() {
	    ArrayList<Pojo_clothes> listpojo_clothes = new ArrayList<>();
	    SQLiteDatabase db = this.getWritableDatabase();
	    Pojo_clothes pojo_clothes = null;
	    Cursor cur = db.rawQuery("SELECT " + PROFILE_PIC + " FROM " + USERDETAILS_TABLE, null);
	    if (cur.moveToFirst()) {
	        do {
	            pojo_clothes = new Pojo_clothes();
	            byte[] blob = cur.getBlob(cur.getColumnIndex(PROFILE_PIC));
	            if (blob != null) {
	                Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
	                pojo_clothes.setShirts(bitmap);
	                listpojo_clothes.add(pojo_clothes);
	            }
	        }
	        while (cur.moveToNext());
	    }
	
	    cur.close();
	    db.close();
	    return listpojo_clothes;
	
	}*/

	/*// Get the image from SQLite DB
	public ArrayList<Trousers_Pojo> getAllTrousersFromDB() {
	    ArrayList<Trousers_Pojo> list_trousers_pojos = new ArrayList<>();
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cur = db.rawQuery("SELECT " + TROUSERS_IMAGE + " FROM " + USERDETAILS_TABLE, null);
	    if (cur.moveToFirst()) {
	        do {
	            Trousers_Pojo trousers_pojo = new Trousers_Pojo();
	            byte[] blob = cur.getBlob(cur.getColumnIndex(TROUSERS_IMAGE));
	            if (blob != null) {
	                Bitmap bitmap = BitmapFactory.decodeByteArray(blob, 0, blob.length);
	                trousers_pojo.setTrousers(bitmap);
	                list_trousers_pojos.add(trousers_pojo);
	            }
	        }
	        while (cur.moveToNext());
	    }
	
	    db.close();
	    cur.close();
	    return list_trousers_pojos;
	}
	*/
	public Bitmap getTrouserFromDB()
	{

		SQLiteDatabase db = this.getWritableDatabase();
		Bitmap bitmap = null;
		Cursor cur = db.rawQuery("SELECT " + NAME + " FROM " + USERDETAILS_TABLE + " WHERE " + TYPE + "= 'trousers'",
				null);

		if (cur.moveToFirst())
		{
			byte[] b_trouser = cur.getBlob(cur.getColumnIndex(NAME));
			if (b_trouser != null)
			{
				bitmap = BitmapFactory.decodeByteArray(b_trouser, 0, b_trouser.length);
			}
			cur.close();
			return bitmap;
		}
		cur.close();
		db.close();
		return null;
	}

}
