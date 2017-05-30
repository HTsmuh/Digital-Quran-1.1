package com.horizontech.biz.digitalquran.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DbObject {

    private static MySqliteDatabase dbHelper;
    public SQLiteDatabase db;

    public DbObject(Context context) {
        dbHelper = new MySqliteDatabase(context);
        this.db = dbHelper.getWritableDatabase();
    }

    public SQLiteDatabase getDbConnection(){
        return this.db;
    }

    public void closeDbConnection(){
        if(this.db != null){
            this.db.close();
        }
    }
}

