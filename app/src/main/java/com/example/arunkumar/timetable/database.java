package com.example.arunkumar.timetable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Arunkumar on 11/20/2015.
 */
public class database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "timetableinfo.db";
    public static final String TABLE_TIMETABLEINFO = "info";
    public static final String COLUMN_TIME = "storetime";
    public static final String COLUMN_DESC = "storedesc";
    public static final String COLUMN_ID="rwo_id";
    public static final String COLUMN_DAY="dayname";
    public String returntime[];
    public String returndesc[];
    public int returnid[];

    public database(Context context, String D_NAME, SQLiteDatabase.CursorFactory factory, int DATABASE_VERSION) {
        super(context,DATABASE_NAME, factory, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {


        String query = "CREATE TABLE " + TABLE_TIMETABLEINFO + " ("+ COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TIME + " TEXT, " + COLUMN_DESC + " TEXT, " + COLUMN_DAY + " TEXT " + ")";
        db.execSQL(query);


    }


    //when we update the table with new rows

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXIST" + TABLE_TIMETABLEINFO);
        onCreate(db);

    }

    ///add a new row to the table

    public void addrow(table_prop tableprop) {

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIME, tableprop.getStoretime());
        values.put(COLUMN_DESC, tableprop.getStoredesc());
        values.put(COLUMN_DAY,tableprop.getStoreday());

        SQLiteDatabase db = getWritableDatabase();

        db.insert(TABLE_TIMETABLEINFO, null, values);
        db.close();

    }

    ///delete a row from the table


    public void deleterow(int deleteid){

        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_TIMETABLEINFO+" WHERE "+COLUMN_ID+"=\""+deleteid+"\"" );



    }


    public void setarray() {

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TIMETABLEINFO + " WHERE " + COLUMN_DAY + "=\"" + Day.day + "\"" +" ORDER  BY "+COLUMN_ID+" DESC ";

        Cursor c = db.rawQuery(query, null);


        returntime = new String[c.getCount()];
        returndesc = new String[c.getCount()];
        returnid= new int[c.getCount()];
        int cnt=c.getCount();
        int count = 0;

        c.moveToFirst();
        if(cnt>0){
         do {
            returnid[count]=c.getInt(0);
            returntime[count] = c.getString(1);
            returndesc[count] = c.getString(2);
            count++;

        }while (c.moveToNext());


    }}
}