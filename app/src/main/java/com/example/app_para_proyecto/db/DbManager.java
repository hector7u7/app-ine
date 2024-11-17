package com.example.app_para_proyecto.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

public class DbManager extends DbHelper{
    Context context;

    public DbManager(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertar(String user, String correo, String password ){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("user",user);
        values.put("correo",correo);
        values.put("password",password);
        long id = db.insert(TABLE_CONTACTOS, null, values);
        return id;
    }

    public boolean existeUsuario(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTOS + " WHERE user = ?";

        Cursor cursor = db.rawQuery(query, new String[]{user});
        int count = cursor.getCount();
        cursor.close();
        db.close();

        return count > 0;
    }

    public boolean validarLogin(String user, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTOS + " WHERE user = ? AND password = ?";
        Cursor cursor = db.rawQuery(query, new String[]{user, password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}
