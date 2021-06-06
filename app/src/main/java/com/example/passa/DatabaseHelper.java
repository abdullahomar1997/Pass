package com.example.passa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "game9";
    private static final String TABLE_NAME1 = "normalgametwoplayers";
    private static final String TABLE_NAME2 = "normalgamethreeplayers";
    private static final String TABLE_NAME3 = "normalgamefourplayers";
    private static final String TABLE_GAME = "gametable";

    private static final String col1 = "ID";
    private static final String col11 = "gametype";
    private static final String col2 = "gameswon";
    private static final String col3 = "gameslost";
    private static final String col4 = "gamescore";
    private static final String col5 = "numpasses";
    private static final String coln = "continue";
    private static final String colo = "cardsplayed";

    private static final String colm = "ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable1 = "CREATE TABLE " + TABLE_NAME1 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + col11 +  " TEXT, " + col2 + " TEXT, " + col3 + " TEXT, "+ col4 + " TEXT, " + col5 + " TEXT)";
        String createTable2 = "CREATE TABLE " + TABLE_NAME2 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + col11 +  " TEXT, " + col2 + " TEXT, " + col3 + " TEXT, "+ col4 + " TEXT, " + col5 + " TEXT)";
        String createTable3 = "CREATE TABLE " + TABLE_NAME3 + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + col11 +  " TEXT, " + col2 + " TEXT, " + col3 + " TEXT, "+ col4 + " TEXT, " + col5 + " TEXT)";
        String createTable4 = "CREATE TABLE " + TABLE_GAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +  coln + " TEXT, " + colo + " TEXT)";

        db.execSQL(createTable1);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
        db.execSQL(createTable4);

    }

    //game typedecks cardsplayed turn passes

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GAME);

        onCreate(db);
    }

    public boolean saveGameScores(String gametype, String gameswon, String gameslost, String gamescore, String numpasses){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col11,gametype);
        contentValues.put(col2,gameswon);
        contentValues.put(col3,gameslost);
        contentValues.put(col4,gamescore);
        contentValues.put(col5,numpasses);
        // Log.d(TAG," adding data " + items + " to " + TABLE_NAME);

        long result = 0;

        if(gametype.equals("2")){
            result = db.insert(TABLE_NAME1,null,contentValues);
        }
        else if(gametype.equals("3")){
            result = db.insert(TABLE_NAME2,null,contentValues);
        }
        else{
            result = db.insert(TABLE_NAME3,null,contentValues);
        }

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean addData(String items){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col2,items);

       // Log.d(TAG," adding data " + items + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME1,null,contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getData(String tableNo){

        String query = null;

        if(tableNo.equals("2")){
            query = "SELECT * FROM " + TABLE_NAME1;
        }
        else if(tableNo.equals("3")){
            query = "SELECT * FROM " + TABLE_NAME2;
        }

        else{
            query = "SELECT * FROM " + TABLE_NAME3;
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(query,null);

        return data;
    }

    public Cursor doQuery(String query) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public void insertItem(String gameType,String gamewon,String gamelost,String gamescore,String numpasses) {

        if(gameType.equals("2")){

            String query = "INSERT INTO " + TABLE_NAME1 + " VALUES (?,?,?,?,?)";
            SQLiteDatabase db = getWritableDatabase();


            db.execSQL(query, new String[]{gameType,gamewon,gamelost,gamescore,numpasses});
            db.close();
        }

        else if(gameType.equals("3")){

            String query = "INSERT INTO " + TABLE_NAME2 + " VALUES (?,?,?,?)";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(query, new String[]{gamewon,gamelost,gamescore,numpasses});
            db.close();

        }

        else if(gameType.equals("4")){

            String query = "INSERT INTO " + TABLE_NAME3 + " VALUES (?,?,?,?)";
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL(query, new String[]{gamewon,gamelost,gamescore,numpasses});
            db.close();
        }
    }

    public boolean saveCurrentGameData(String fullString,String cardsPlayed) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(coln,fullString);
        contentValues.put(colo,cardsPlayed);

        // Log.d(TAG," adding data " + items + " to " + TABLE_NAME);

        long result = db.insert(TABLE_GAME,null,contentValues);

        if(result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor OngoingGameData() {

        String query = null;

        query = "SELECT * FROM " + TABLE_GAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery(query,null);

        return data;
    }
}
