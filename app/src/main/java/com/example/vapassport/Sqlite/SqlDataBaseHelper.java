package com.example.vapassport.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import com.example.vapassport.Register.RegisterData;

import java.util.ArrayList;

public class SqlDataBaseHelper extends SQLiteOpenHelper {

    private static final String DataBaseName = "DataBaseIt";
    private static final int DataBaseVersion = 1;

    public SqlDataBaseHelper(@Nullable Context context) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String RegisterDataTable =
                "CREATE TABLE IF NOT EXISTS RegisterData (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "date varchar(20) not null," +
                "time varchar(20) not null," +
                "placeCode varchar(20) not null" +
                ")";
        sqLiteDatabase.execSQL(RegisterDataTable);

        String PassportDataTable =
                "CREATE TABLE IF NOT EXISTS PassportData (" +
                        "IsCheck varchar(20) not null" +
                        ")";
        sqLiteDatabase.execSQL(PassportDataTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        final String SQL = "DROP TABLE Users";
        sqLiteDatabase.execSQL(SQL);
    }
}
