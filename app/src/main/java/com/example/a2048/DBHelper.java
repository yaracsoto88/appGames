package com.example.a2048;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "ScoreData.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table ScoreData2048(name TEXT, score INT)");
        DB.execSQL("create Table ScoreDataSenku(name TEXT, score INT)");
        DB.execSQL("create Table UserData(name TEXT, password TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
        DB.execSQL("drop Table if exists ScoreData2048");
        DB.execSQL("drop Table if exists ScoreDataSenku");
        DB.execSQL("drop Table if exists UserData");
    }

    public Boolean insertScoreData(String tabla, String name, int score) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);
        contentValues.put("score", score);
        long result = DB.insert(tabla, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public void insertUserData(String name, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        DB.insert("UserData", null, contentValues);
    }
    public Boolean checkUserData(String name, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from UserData where name = ? and password = ?", new String[]{name, password});
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Cursor getScoreData(String tabla, String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from " + tabla+ " WHERE name = ?", new String[]{name});
        return cursor;
    }

    public void deleteAll(String tabla) {
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("delete from " + tabla);
    }

    //en el senku la mejor puntuacion es la que menos tarda en acabar el juego
    public int getMaxScoreSenku(String tabla,String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select MIN(score) from " + tabla+ " WHERE name = ?", new String[]{name});
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    //en el 2048 al revés
    public int getMaxScore2048(String tabla, String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT MAX(score) FROM " + tabla + " WHERE name = ?", new String[]{name});
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

}