package com.ftn.krt.mdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nikola on 6/2/15.
 */
public class MovieDB extends SQLiteOpenHelper {
        public static final String DATABASE_NAME = "movies.db";
        public static final int DATABASE_VERSION = 1;

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_HASH = "hash";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_EDITOR = "editor";
        public static final String COLUMN_YEAR = "yearr";

        private SQLiteDatabase mDb = null;

        public MovieDB(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_HASH + " INTEGER PRIMARY KEY, " +
                    COLUMN_TITLE + " TEXT, " +
                    COLUMN_EDITOR+ " TEXT, " +
                    COLUMN_YEAR + " INTEGER);" );
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        private SQLiteDatabase getDb() {
            if(mDb == null) {
                mDb = getWritableDatabase();
            }
            return mDb;
        }

        public void insert(Movie movie) {
            SQLiteDatabase db = getDb();

            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, movie.title);
            values.put(COLUMN_EDITOR, movie.editor);
            values.put(COLUMN_YEAR, movie.year);
            values.put(COLUMN_HASH, movie.hash);

            //student.mId = db.insert(TABLE_NAME, null, values);

            db.insert(TABLE_NAME, null, values);
        }

        public Movie[] readMovies() {
            SQLiteDatabase db = getDb();

            Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_HASH, null);

            if (cursor.getCount() <= 0) {
                return null;
            }

            Movie[] students = new Movie[cursor.getCount()];

            int i = 0;
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                students[i++] = createMovie(cursor);
            }

            return students;
        }

        public Movie readMovie(long hash) {
            SQLiteDatabase db = getDb();

            Cursor cursor = db.query(TABLE_NAME, null, "id = ?",
                    new String[] { Long.toString(hash) }, null, null, null);

            cursor.moveToFirst();
            return createMovie(cursor);
        }

        public void update(Movie movie) {
            SQLiteDatabase db = getDb();

            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, movie.title);
            values.put(COLUMN_EDITOR, movie.editor);
            values.put(COLUMN_YEAR, movie.year);

            db.update(TABLE_NAME, values, " id = ? ", new String[] { Long.toString(movie.hash) });
        }

        private Movie createMovie(Cursor cursor) {
            long hash = cursor.getLong(cursor.getColumnIndex(COLUMN_HASH));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String editor = cursor.getString(cursor.getColumnIndex(COLUMN_EDITOR));
            int year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR));

            return new Movie(title, editor, year, hash);
        }
}
