package com.example.gestionetudiants.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gestionetudiants.model.Etudiant;

import java.util.ArrayList;
import java.util.List;

public class EtudiantService {

    private static final String TAG = "EtudiantService";
    private final MySQLiteHelper helper;

    public EtudiantService(Context context) {
        helper = new MySQLiteHelper(context);
    }

    // ── CREATE ────────────────────────────────────────────────
    public long ajouter(Etudiant e) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MySQLiteHelper.COL_NOM,    e.getNom());
        cv.put(MySQLiteHelper.COL_PRENOM, e.getPrenom());
        long id = db.insert(MySQLiteHelper.TABLE_ETUDIANTS, null, cv);
        db.close();
        Log.d(TAG, "Ajout → id=" + id + " | " + e.getNom() + " " + e.getPrenom());
        return id;
    }

    // ── READ (un) ─────────────────────────────────────────────
    public Etudiant findById(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(
                MySQLiteHelper.TABLE_ETUDIANTS,
                null,
                MySQLiteHelper.COL_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null
        );
        Etudiant e = null;
        if (cursor != null && cursor.moveToFirst()) {
            e = new Etudiant(
                    cursor.getInt(cursor.getColumnIndexOrThrow(MySQLiteHelper.COL_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COL_NOM)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COL_PRENOM))
            );
            cursor.close();
        }
        db.close();
        Log.d(TAG, "findById(" + id + ") → " + (e != null ? e : "non trouvé"));
        return e;
    }

    // ── READ (tous) ───────────────────────────────────────────
    public List<Etudiant> findAll() {
        List<Etudiant> liste = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(MySQLiteHelper.TABLE_ETUDIANTS,
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                liste.add(new Etudiant(
                        cursor.getInt(cursor.getColumnIndexOrThrow(MySQLiteHelper.COL_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COL_NOM)),
                        cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COL_PRENOM))
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(TAG, "findAll() → " + liste.size() + " étudiant(s)");
        return liste;
    }

    // ── UPDATE ────────────────────────────────────────────────
    public int modifier(Etudiant e) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(MySQLiteHelper.COL_NOM,    e.getNom());
        cv.put(MySQLiteHelper.COL_PRENOM, e.getPrenom());
        int rows = db.update(MySQLiteHelper.TABLE_ETUDIANTS, cv,
                MySQLiteHelper.COL_ID + "=?",
                new String[]{String.valueOf(e.getId())});
        db.close();
        Log.d(TAG, "modifier(id=" + e.getId() + ") → " + rows + " ligne(s)");
        return rows;
    }

    // ── DELETE ────────────────────────────────────────────────
    public int supprimer(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int rows = db.delete(MySQLiteHelper.TABLE_ETUDIANTS,
                MySQLiteHelper.COL_ID + "=?",
                new String[]{String.valueOf(id)});
        db.close();
        Log.d(TAG, "supprimer(id=" + id + ") → " + rows + " ligne(s)");
        return rows;
    }
}