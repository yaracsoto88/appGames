package com.example.a2048;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.Arrays;


public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "ScoreData.db", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table ScoreData2048(name TEXT, score INT)");
        DB.execSQL("create Table ScoreDataSenku(name TEXT, score INT)");
        DB.execSQL("create Table UserData(name TEXT, password TEXT,userPhoto BLOB)");


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
    public void insertUserData(String name, String password, byte[] userPhoto) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("password", password);
        contentValues.put("userPhoto", userPhoto);
        DB.insert("UserData", null, contentValues);
    }
    public void setPhoto(String name, Bitmap userPhoto){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        userPhoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] photo = baos.toByteArray();
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("userPhoto", photo);
        // Update the photo of the user
        DB.update("UserData", contentValues, "name = ?", new String[]{name});
    }
    public Bitmap getPhoto(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT userPhoto FROM UserData WHERE name = ?", new String[]{name});

        Bitmap photoBitmap = null;

        if (cursor != null && cursor.moveToFirst()) {
            byte[] photo = cursor.getBlob(0);

            // Convert byte array to Bitmap without using DbBitmapUtility
            if (photo != null && photo.length > 0) {
                photoBitmap = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            }

            cursor.close();
        }

        return photoBitmap;
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


    public void deleteScore(String tabla, String name, int score){
        SQLiteDatabase DB = this.getWritableDatabase();
        DB.execSQL("DELETE FROM " + tabla + " WHERE name = ? AND score = ?", new String[]{name, String.valueOf(score)});
    }
    public void updatePassword(String userName, String newPassword) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", newPassword);
        DB.update("UserData", contentValues, "name = ?", new String[]{userName});
    }





}