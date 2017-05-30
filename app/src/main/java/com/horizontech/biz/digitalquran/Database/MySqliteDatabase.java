package com.horizontech.biz.digitalquran.Database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class MySqliteDatabase extends SQLiteAssetHelper {

    private static final String DATABASE_NAMES = "QURAN_v1.14";
    private static final int DATABASE_VERSION = 1;

    public MySqliteDatabase(Context context) {
        super(context, DATABASE_NAMES, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

}
