package com.ftn.krt.mdb;

/**
 * Created by nikola on 6/2/15.
 */
public class Djb2RA502012 {
    public native long getHashRA502012(String s);
    static {
        System.loadLibrary("Djb2RA502012");
    }
}
