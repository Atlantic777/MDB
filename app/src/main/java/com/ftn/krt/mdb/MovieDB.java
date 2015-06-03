package com.ftn.krt.mdb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

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
        public static final String COLUMN_YEAR = "year";
        private Context mContext;

        private SQLiteDatabase mDb = null;

        private Djb2RA502012 djb2;

        public MovieDB(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            mContext = context;
            djb2 = new Djb2RA502012();
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

            long hash = djb2.get_hash(movie.getConcat());

            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, movie.title);
            values.put(COLUMN_EDITOR, movie.editor);
            values.put(COLUMN_YEAR, movie.year);
            values.put(COLUMN_HASH, hash);

            try {
                db.insertOrThrow(TABLE_NAME, null, values);
            } catch(Exception e) {
                Toast.makeText(mContext, "ERROR: Movie already in DB!", Toast.LENGTH_SHORT).show();
                Log.d("DB", "gotcha!");
            }
        }

        public Movie[] readMovies() {
            SQLiteDatabase db = getDb();

            Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, COLUMN_HASH, null);

            if (cursor.getCount() <= 0) {
                return null;
            }

            Movie[] movies = new Movie[cursor.getCount()];

            int i = 0;
            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                movies[i++] = createMovie(cursor);
            }

            return movies;
        }

        public Movie readMovie(long hash) {
            SQLiteDatabase db = getDb();

            Cursor cursor = db.query(TABLE_NAME, null, "hash = ?",
                    new String[] { Long.toString(hash) }, null, null, null);

            cursor.moveToFirst();
            return createMovie(cursor);
        }

        public void update(Movie movie, long oldHash) {
            SQLiteDatabase db = getDb();

            long newHash = djb2.get_hash(movie.getConcat());

            ContentValues values = new ContentValues();
            values.put(COLUMN_TITLE, movie.title);
            values.put(COLUMN_EDITOR, movie.editor);
            values.put(COLUMN_YEAR, movie.year);
            values.put(COLUMN_HASH, newHash);

            db.update(TABLE_NAME, values, " hash = ? ", new String[] { Long.toString(oldHash) });
        }

        private Movie createMovie(Cursor cursor) {
            long hash = cursor.getLong(cursor.getColumnIndex(COLUMN_HASH));
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
            String editor = cursor.getString(cursor.getColumnIndex(COLUMN_EDITOR));
            int year = cursor.getInt(cursor.getColumnIndex(COLUMN_YEAR));

            return new Movie(title, editor, year, hash);
        }
}
