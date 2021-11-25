package com.darts.dartscoreboard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    //Constant variable. Automatically done with - Refactor - Introduce Constant
    public static final String PLAYERS_TO_GAME = "players_to_game";
    public static final String TURN = "turn";
    public static final String ROUND = "round";
    public static final String LEGS = "legs";
    public static final String GAME = "game";
    public static final String PLAYER = "player";
    public static final String SETS = "sets";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CHECKOUT = "checkout";
    public static final String COLUMN_GAME_TYPE = "game_type";

    //Declare DB Name and version
    public DataBaseHelper(@Nullable Context context) {
        super(context, "gameData.db", null, 1);
    }

    // this is called the fist time a database is accessed. The code in here should generate new table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPlayerTable = "CREATE TABLE " + PLAYER +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_CHECKOUT + " INTEGER)";

        String createGameTable = "CREATE TABLE " + GAME +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GAME_TYPE + " INTEGER, " +
                "sets_id INTEGER," + "FOREIGN KEY(sets_id) REFERENCES sets(id) )";

        String createSetTable = "CREATE TABLE " + SETS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "set_one INTEGER, " +
                "set_two INTEGER, " +
                "set_three INTEGER, " +
                "set_four INTEGER, " +
                "set_five INTEGER," +
                "legs_id INTEGER, FOREIGN KEY(legs_id) REFERENCES legs(id) )";

        String createLegTable = "CREATE TABLE " + LEGS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " leg_1," +
                " leg_2," +
                " leg_3," +
                " leg_4," +
                " leg_5," +
                " round_id INTEGER, FOREIGN KEY(round_id) REFERENCES round(id) )";

        String createRoundTable = "CREATE TABLE " + ROUND +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "roundnumber INTEGER, " +
                "score INTEGER, " +
                "turn_id INTEGER, FOREIGN KEY(turn_id) REFERENCES turn(id) )";

        String createTurnTable = "CREATE TABLE " + TURN +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, turn_one INTEGER, turn_two INTEGER, turn_three INTEGER)";

        String createPlayerToGameTable = "Create Table " + PLAYERS_TO_GAME +
                " (player_id INTEGER, " +
                "game_id INTEGER, " +
                "FOREIGN KEY(player_id) REFERENCES player(id), " +
                "FOREIGN KEY(game_id) REFERENCES game(id) )";

        db.execSQL(createPlayerTable);
        db.execSQL(createGameTable);
        db.execSQL(createSetTable);
        db.execSQL(createPlayerToGameTable);
        db.execSQL(createLegTable);
        db.execSQL(createRoundTable);
        db.execSQL(createTurnTable);
    }

    //If version of data base number changes. Prevent previous users if db setting changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + GAME);
        db.execSQL("DROP TABLE IF EXISTS " + SETS);
        db.execSQL("DROP TABLE IF EXISTS " + LEGS);
        db.execSQL("DROP TABLE IF EXISTS " + ROUND);
        db.execSQL("DROP TABLE IF EXISTS " + PLAYER);
        db.execSQL("DROP TABLE IF EXISTS " + TURN);
        db.execSQL("DROP TABLE IF EXISTS " + PLAYERS_TO_GAME);
        onCreate(db);
    }

    //Save player in Player table
    public boolean addPlayer(PlayerModel playerModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, playerModel.getPlayerName());
        cv.put(COLUMN_CHECKOUT, playerModel.getCheckNumber());

        long insert = db.insert(PLAYER, null, cv);
        if (insert == -1) {
            return false;
        } else {

            return true;
        }
    }

    public List<PlayerModel> getPlayerListed(){

        List<PlayerModel> returnList = new ArrayList<>();
        //get data from db
        String queryString = "SELECT * FROM " + PLAYER;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new player object, put them into returnList
            do {
                int playersID = cursor.getInt(0);
                String playersName = cursor.getString(1);
                int playersCheckOut = cursor.getInt(1);

                PlayerModel newPlayer = new PlayerModel(playersID, playersName, playersCheckOut);
                returnList.add(newPlayer);

            } while (cursor.moveToNext());
        }
        else {
            // failure, do not add anything to the list
        }
        // Close cursor and db when done
        cursor.close();
        db.close();
        return returnList;
    }

}
