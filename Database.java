package com.example.task_71p.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.task_71p.model.Items;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    public Database(@Nullable Context context) {
        super(context, "Adverts_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_Adverts_TABLE = "CREATE TABLE Adverts(AdvertID INTEGER PRIMARY KEY AUTOINCREMENT, TYPE TEXT, NAME TEXT, PHONE INTEGER, DESCRIPTION TEXT, DATE TEXT, LOCATION TEXT)";
        sqLiteDatabase.execSQL(CREATE_Adverts_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String DROP_Adverts_TABLE = "DROP TABLE IF EXISTS Adverts";
        sqLiteDatabase.execSQL(DROP_Adverts_TABLE);
        onCreate(sqLiteDatabase);
    }

    public long InsertAdvert(Items items) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put("TYPE", items.getLostOrFound());
        Values.put("NAME", items.getName());
        Values.put("DESCRIPTION", items.getDescription());
        Values.put("PHONE", items.getPhone());
        Values.put("DATE", items.getDate());
        Values.put("LOCATION", items.getLocation());
        long row = db.insert("Adverts",null, Values);
        db.close();
        return row;
    }

    public void DeleteAdvert(Items items){
        SQLiteDatabase db = this.getWritableDatabase();
        String WhereClause = "AdvertID=?";
        String[] WhereArgs = {String.valueOf(items.getAdvertID())};
        db.delete("Adverts", WhereClause, WhereArgs);
        db.close();
    }

    public ArrayList<Items> fetchAllAdverts(){
        ArrayList<Items> itemsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String SELECT_ALL_Adverts = "SELECT * FROM Adverts";
        Cursor cursor = db.rawQuery(SELECT_ALL_Adverts, null);
        if (cursor.moveToFirst()){
            do {
                Items temp = new Items(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getInt(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
                itemsList.add(temp);
            }while(cursor.moveToNext());
        }
        return itemsList;
    }
}
