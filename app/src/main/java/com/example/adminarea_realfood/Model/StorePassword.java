package com.example.adminarea_realfood.Model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StorePassword extends SQLiteOpenHelper {
    private static final String TAG = "ProductDbHelper";
    private static final String DATABASE_NAME = "account.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_PRODUCT = "product";

    public StorePassword(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryCreateTable = "CREATE TABLE " + TABLE_PRODUCT + " ( " +
                "email VARCHAR (255) NOT NULL PRIMARY KEY , " +
                "password VARCHAR (255) NOT NULL " +
                ")";

        db.execSQL(queryCreateTable);
    }

    //Lấy toàn bộ SP
    public String getPassword() {
        String password = "";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT password from " + TABLE_PRODUCT, null);
        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            password = cursor.getString(0);
            cursor.moveToNext();
        }
        cursor.close();
        return password;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        //Tiến hành tạo bảng mới
        onCreate(db);
    }
    //Chèn mới
    public void insertProduct(String email, String password) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("Delete  from " + TABLE_PRODUCT);
        db.execSQL("INSERT INTO " + TABLE_PRODUCT + "(email, password ) VALUES (?,?)",
                new String[]{email, password});
    }


}
