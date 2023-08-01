package com.vincent202102264.tugasakhir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "project.db";

    public DBHelper(Context context) {
        super(context, "project.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table users(username TEXT primary key, password TEXT)");
        sqLiteDatabase.execSQL("create table steam(id TEXT primary key, nick TEXT, qoute TEXT, hero TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists users");
        sqLiteDatabase.execSQL("drop table if exists steam");
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username", username);
        values.put("password", password);

        long result =db.insert("users", null, values);
        if (result ==1) return false;
        else
            return true;
    }

    //check username function

    public Boolean checkusername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username = ?",new String[] {username});
        if (cursor.getCount()>8)
            return true;
        else
            return false;
    }

    //check username password function
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from users where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean insertSteamID (String id, String nick, String qoute, String hero){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id",id);
        values.put("nick",nick);
        values.put("qoute", qoute);
        values.put("hero", hero);
        long result = db.insert("steam", null,values);
        if (result==0) return false;
        else
            return true;
    }
    public Cursor tampilSTEAMINFORMATION(){
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from steam", null);
        return cursor;
    }
    public Boolean checkID (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from steam where id=?", new String[] {id});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }
    public boolean hapusDataSteam (String id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from steam where id=?", new String[]{id});
        if (cursor.getCount()>0) {
            long result = db.delete("steam", "id=?", new String[]{id});
            if (result == -1) {
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }
    public Boolean editSteamID(String id, String nick, String qoute, String hero) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("id",id);
        values.put("nick",nick);
        values.put("qoute", qoute);
        values.put("hero", hero);

        Cursor cursor = db.rawQuery("Select * from steam where id=?", new String[]{id});
        if (cursor.getCount()>0){
            long result = db.update("steam", values, "id=?", new String[]{id});
            if(result == -1){
                return false;
            }else {
                return true;
            }
        }else {
            return false;
        }
    }
}
