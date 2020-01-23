package com.rdc.ascratechassignment.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.rdc.ascratechassignment.model.interfaces.LoginTableDao;

@Database(entities = {LoginTable.class, ShopDetailsTable.class}, version = 3, exportSchema = false)

public abstract class LoginDatabase extends RoomDatabase {
    private static LoginDatabase INSTANCE = null;

    private static LoginDatabase SHOPDETAILSINSTANCE = null;

    public static LoginDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LoginDatabase.class, "logintable").build();
        }
        return INSTANCE;
    }

    /*public static LoginDatabase getDatabase(Context context)
    {
        if (SHOPDETAILSINSTANCE == null)
        {
            SHOPDETAILSINSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), LoginDatabase.class, "ShopDetailsTable").build();
        }
        return SHOPDETAILSINSTANCE;
    }
    */
    public static void closeDatabase() {
        INSTANCE = null;
    }

    public abstract LoginTableDao loginTableDao();
}
