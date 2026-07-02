package com.example.gestionetudiants.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME    = "etudiants.db";
    private static final int    DATABASE_VERSION = 1;

    // Noms table / colonnes
    public static final String TABLE_ETUDIANTS = "etudiants";
    public static final String COL_ID          = "id";
    public static final String COL_NOM         = "nom";
    public static final String COL_PRENOM      = "prenom";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_ETUDIANTS + " (" +
                    COL_ID     + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NOM    + " TEXT NOT NULL, "                     +
                    COL_PRENOM + " TEXT NOT NULL"                       +
                    ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ETUDIANTS);
        onCreate(db);
    }
}