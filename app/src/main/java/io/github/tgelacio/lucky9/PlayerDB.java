package io.github.tgelacio.lucky9;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerDB {

    // database constants
    public static final String DB_NAME = "player.sqlite";
    public static final int DB_VERSION = 1;

    // player table constants
    public static final String PLAYER_TABLE = "players";

    public static final String PLAYER_ID = "id";
    public static final int PLAYER_ID_COL = 0;

    public static final String PLAYER_NAME = "name";
    public static final int PLAYER_NAME_COL = 1;

    public static final String PLAYER_WINNINGS = "winnings";
    public static final int PLAYER_WINNINGS_COL = 2;

    public static final String PLAYER_ROUNDS = "rounds";
    public static final int PLAYER_ROUNDS_COL = 3;

    // CREATE and DROP TABLE statements
    public static final String CREATE_PLAYER_TABLE =
            "CREATE TABLE " + PLAYER_TABLE + " (" +
                    PLAYER_ID + " INTEGER PRIMARY KEY  AUTOINCREMENT  NOT NULL, " +
                    PLAYER_NAME + " VARCHAR NOT NULL, " +
                    PLAYER_WINNINGS + " INTEGER NOT NULL  DEFAULT 0, " +
                    PLAYER_ROUNDS + " INTEGER NOT NULL  DEFAULT 0);";

    public static final String DROP_PLAYER_TABLE =
            "DROP TABLE IF EXISTS " + PLAYER_TABLE;

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // create tables
            db.execSQL(CREATE_PLAYER_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            db.execSQL(DROP_PLAYER_TABLE);
            Log.d("PlayerDB: ", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            onCreate(db);
        }
    }

    // database and database helper objects
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public PlayerDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
        openWriteableDB();
        closeDB();
    }

    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    // Get Players' Stats
    ArrayList<HashMap<String, String>> getPlayersStats() {

        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
        openReadableDB();

        String selectQuery = "SELECT id, name, winnings, rounds " +
                "FROM players " +
                "ORDER BY winnings DESC;";

        Cursor cursor = db.rawQuery(selectQuery, null);
        while (cursor.moveToNext()) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("id", cursor.getString(0));
            map.put("name", cursor.getString(1));
            map.put("winnings", cursor.getString(2));
            map.put("rounds", cursor.getString(3));
            data.add(map);
        }

        if (cursor != null) {
            cursor.close();
        }

        closeDB();

        return data;
    }

    ///
    /// Add New Player
    ///
    void insertPlayer(String sName, int winnings, int rounds) throws Exception {
        openWriteableDB();

        ContentValues content = new ContentValues();
        content.put("name", sName);
        content.put("winnings", winnings);
        content.put("rounds", rounds);

        long nResult = db.insert("players", null, content);

        if (nResult == -1) {
            throw new Exception("No data.");
        }

        closeDB();
    }
}
