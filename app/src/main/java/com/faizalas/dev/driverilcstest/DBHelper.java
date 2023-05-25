package com.faizalas.dev.driverilcstest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Project.db";

    public DBHelper(Context context) {
        super(context, "Project.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(nip TEXT primary key, password TEXT, email TEXT, fullname TEXT)");
        MyDB.execSQL("create Table driver(nip TEXT primary key, password TEXT, email TEXT, fullname TEXT)");
        MyDB.execSQL("create Table car(id TEXT primary key, departure TEXT, arrival TEXT, date TEXT, total_seats TEXT)");
        MyDB.execSQL("create Table pesan(pickseats Text primary key, fullname TEXT)");
        MyDB.execSQL("create Table admin(nip TEXT primary key, password TEXT, email TEXT, fullname TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists driver");
        MyDB.execSQL("drop Table if exists car");
        MyDB.execSQL("drop Table if exists pesan");
        MyDB.execSQL("drop Table if exists admin");
    }

    public Boolean insertData(String fullname, String email, String nip, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", fullname);
        contentValues.put("email", email);
        contentValues.put("nip", nip);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean insertAdmin(String fullname, String email, String nip, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", fullname);
        contentValues.put("email", email);
        contentValues.put("nip", nip);
        contentValues.put("password", password);
        long result = MyDB.insert("admin", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean insertDriver(String fullname, String email, String nip, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", fullname);
        contentValues.put("email", email);
        contentValues.put("nip", nip);
        contentValues.put("password", password);
        long result = MyDB.insert("driver", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public boolean checknip(String nip) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where nip = ?", new String[]{nip});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkadminnip(String nip) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor =MyDB.rawQuery("Select * from admin where nip = ?", new String[]{nip});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkdrivernip(String nip) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from driver where nip = ?", new String[]{nip});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checknippassword(String nip, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where nip = ? and password = ?", new String[]{nip, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkadminnippassword(String nip, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from admin where nip = ? and password = ?", new String[]{nip, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkdrivernippassword(String nip, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from driver where nip = ? and password = ?", new String[]{nip, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public Boolean insertCar(String id, String departure, String arrival, String date, String total_seats) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("departure", departure);
        contentValues.put("arrival", arrival);
        contentValues.put("date", date);
        contentValues.put("total_seats", total_seats);
        long result = MyDB.insert("car", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


}
