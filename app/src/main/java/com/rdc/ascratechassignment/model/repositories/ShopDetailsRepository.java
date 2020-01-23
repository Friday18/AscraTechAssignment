package com.rdc.ascratechassignment.model.repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.rdc.ascratechassignment.model.database.LoginDatabase;
import com.rdc.ascratechassignment.model.database.ShopDetailsTable;
import com.rdc.ascratechassignment.model.interfaces.LoginTableDao;

import java.util.List;

public class ShopDetailsRepository {
    private LiveData<List<ShopDetailsTable>> listLiveDataShopDetails;

    private LoginTableDao shopDetailsDao;

    public ShopDetailsRepository(@NonNull Application application) {
        LoginDatabase shopDetailsdatabase = LoginDatabase.getDatabase(application);
        shopDetailsDao = shopDetailsdatabase.loginTableDao();
        listLiveDataShopDetails = shopDetailsDao.getShopDetails();
    }

    public LiveData<List<ShopDetailsTable>> getListLiveDataShopDetails() {
        return listLiveDataShopDetails;
    }

    public Long addShopDetails(ShopDetailsTable shopDetailsTable) {
        try {
            return new addShopDetailsTask().execute(shopDetailsTable).get();
        } catch (Exception e) {
            Log.e("Log--> ", "Error in add shop details : " + e);
            return null;
        }
    }

    public class addShopDetailsTask extends AsyncTask<ShopDetailsTable, Void, Long> {
        @Override
        protected Long doInBackground(ShopDetailsTable... shopDetailsTables) {
            return shopDetailsDao.insertShopDetails(shopDetailsTables[0]);
        }
    }

    public List<ShopDetailsTable> getShopDetails() {
        try {
            return new getShopDetailsTask().execute().get();
        } catch (Exception e) {
            Log.e("Log--> ", "Error in add shop details : " + e);
            return null;
        }

    }

    public class getShopDetailsTask extends AsyncTask<Void, Void, List<ShopDetailsTable>> {
        @Override
        protected List<ShopDetailsTable> doInBackground(Void... shopDetailsTables) {
            return shopDetailsDao.getShopDetail();
        }
    }

    public List<ShopDetailsTable> getShopDetailsAfterSave(String shopid) {
        try {
            return new getShopDetailsAfterSaveTask(shopid).execute().get();
        } catch (Exception e) {
            Log.e("Log--> ", "Error in add shop details : " + e);
            return null;
        }

    }

    public class getShopDetailsAfterSaveTask extends AsyncTask<Void, Void, List<ShopDetailsTable>> {
        String shopid;

        public getShopDetailsAfterSaveTask(String shopid) {
            this.shopid = shopid;
        }

        @Override
        protected List<ShopDetailsTable> doInBackground(Void... shopDetailsTables) {
            return shopDetailsDao.getShopDetailtoshow(shopid);
        }

    }
}
