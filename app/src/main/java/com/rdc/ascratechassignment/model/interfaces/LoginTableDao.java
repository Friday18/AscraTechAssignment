package com.rdc.ascratechassignment.model.interfaces;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.rdc.ascratechassignment.model.database.LoginTable;
import com.rdc.ascratechassignment.model.database.ShopDetailsTable;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface LoginTableDao {
    // Select all from Task table and order by "complete by" date
    @Query("SELECT * FROM logintable")
    LiveData<List<LoginTable>> getLoginDetails();

    @Insert(onConflict = REPLACE)
    void insertLoginData(LoginTable loginTable);

    @Delete
    void deleteLoginInfo(LoginTable loginTable);

    @Query("SELECT * FROM LOGINTABLE " + "WHERE  email LIKE :email and password LIKE :password")
    List<LoginTable> checkLogin(String email, String password);

    @Insert(onConflict = REPLACE)
    Long insertShopDetails(ShopDetailsTable shopDetailsTable);

    @Query("SELECT * FROM ShopDetailsTable")
    LiveData<List<ShopDetailsTable>> getShopDetails();

    @Query("SELECT * FROM ShopDetailsTable")
    List<ShopDetailsTable> getShopDetail();

    @Query("SELECT * FROM ShopDetailsTable WHERE shopNameId LIKE :id")
    List<ShopDetailsTable> getShopDetailtoshow(String id);
}
