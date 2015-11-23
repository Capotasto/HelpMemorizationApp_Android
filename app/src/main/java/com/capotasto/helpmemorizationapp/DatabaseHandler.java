package com.capotasto.helpmemorizationapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Capotasto on 11/22/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    static final int DB_VERSION = 1;

    // Database Name
    static final String DB_NAME = "sqlite_sample.db";

    // vocabularies table name
    private static final String TABLE_NAME = "vocabularies";

    // vocabularies Table Columns names
    private static final String FIELD_ID = "id";
    private static final String FIELD_WORD = "word";
    private static final String FIELD_MEANING = "meaning";
    private static final String FIELD_EXAMPLE = "example";
    private static final String FIELD_SYMBOL = "p_symbol";

    public DatabaseHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_SQL =
                "CREATE TABLE " + TABLE_NAME
                        + " ( "
                        + FIELD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + FIELD_WORD + " TEXT NOT NULL, "
                        + FIELD_MEANING + " TEXT NOT NULL, "
                        + FIELD_EXAMPLE + " TEXT, "
                        + FIELD_SYMBOL + " TEXT"
                        + ");";
        db.execSQL(CREATE_TABLE_SQL);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        // Create tables again
        onCreate(db);

    }

    // Adding new contact
    public void addWord(Vocabulary vocabulary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(FIELD_WORD, vocabulary.getWord());
        values.put(FIELD_MEANING, vocabulary.getMeaning());
        values.put(FIELD_EXAMPLE, vocabulary.getExample());
        values.put(FIELD_SYMBOL, vocabulary.getP_symbol());

        db.insert(TABLE_NAME, null, values);// Inserting Row
        db.close(); // Closing database connection
    }

    // Getting single contact
    public Vocabulary getWord(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{FIELD_ID,
                        FIELD_WORD, FIELD_MEANING, FIELD_EXAMPLE, FIELD_SYMBOL}, FIELD_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Vocabulary vocabulary = new Vocabulary(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        return vocabulary;
    }

    // Getting All Contacts
    public List<Vocabulary> getAllWords() {
        List<Vocabulary> vocabularyList = new ArrayList<Vocabulary>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Vocabulary vocabulary = new Vocabulary();
                vocabulary.setId(Integer.parseInt(cursor.getString(0)));
                vocabulary.setWord(cursor.getString(1));
                vocabulary.setMeaning(cursor.getString(2));
                vocabulary.setExample(cursor.getString(3));
                vocabulary.setP_symbol(cursor.getString(4));
                // Adding contact to list
                vocabularyList.add(vocabulary);
            } while (cursor.moveToNext());
        }

        // return contact list
        return vocabularyList;
    }

    // Getting contacts Count
    public int getWordsCount() {

        String countQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Updating single contact
    public int updateWord(Vocabulary vocabulary) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FIELD_WORD, vocabulary.getWord());
        values.put(FIELD_MEANING, vocabulary.getMeaning());
        values.put(FIELD_EXAMPLE, vocabulary.getExample());
        values.put(FIELD_SYMBOL, vocabulary.getP_symbol());

        // updating row
        return db.update(TABLE_NAME, values, FIELD_ID + " = ?",
                new String[] { String.valueOf(vocabulary.getId()) });
    }

    // Deleting single contact
    public void deleteWord(Vocabulary vocabulary) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, FIELD_ID + " = ?",
                new String[] { String.valueOf(vocabulary.getId()) });
        db.close();
    }

}
