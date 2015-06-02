package com.ftn.krt.mdb;

/**
 * Created by nikola on 6/2/15.
 */
public class Djb2RA502012 {
    public native long get_hash(String s);
    static {
        System.loadLibrary("Djb2RA502012");
    }
}
