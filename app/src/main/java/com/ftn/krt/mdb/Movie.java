package com.ftn.krt.mdb;

/**
 * Created by nikola on 6/2/15.
 */
public class Movie {
    public String title;
    public String editor;
    public int year;
    public long hash;

    Movie(String title, String editor, int year, long hash) {
        init(title, editor, year, hash);
    }

    Movie(String title, String editor, int year) {
        init(title, editor, year, -1);
    }

    void init(String title, String editor, int year, long hash) {
        this.title = title;
        this.editor = editor;
        this.year = year;
        this.hash = hash;
    }

    public String getConcat() {
        return title + editor + year;
    }
}
