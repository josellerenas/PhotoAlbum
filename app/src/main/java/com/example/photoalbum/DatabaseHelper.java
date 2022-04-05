package com.example.photoalbum;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TABLE_COUNTRIES = "COUNTRIES";
    public static final String COLUMN_COUNTRY_NAME = "NAME";
    public static final String COLUMN_STATE_NAME = "STATE_NAME";
    public static final String COLUMN_CITY_NAME = "CITY_NAME";

    public static final String TABLE_USERS = "USER_LIST";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_COUNTRY_USERS = "COUNTRY";
    public static final String COLUMN_STATE_USERS = "STATE";
    public static final String COLUMN_CITY_USERS = "CITY";


    /* This is one of the constructors defined in SQLiteOpenHelper class.
        We modified the constructor with hardcoded data */
    public DatabaseHelper(@Nullable Context context) {
        super(context, "photoalbum.db", null, 1);
    }

    /* The next two methods I had to implement them because the SQLiteOpenHelper needed it
        in order to not fail.  */

    /* This is called the first time the database is accessed. There should be code in here
        to create a new database */
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    /* This is called if the database version number changes. It prevents previous users
            app from breaking when you change the database design */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Get countries
    public List<String> getCountries() {
        List<String> returnList = new ArrayList<>();
        boolean countryAlreadyExists = false;
        String queryString = "SELECT " + COLUMN_COUNTRY_NAME + " FROM " + TABLE_COUNTRIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                for (String i:returnList) {
                    if (i.equals(cursor.getString(0))) {
                        countryAlreadyExists = true;
                    }
                }
                if (!countryAlreadyExists) {
                    returnList.add(cursor.getString(0));
                }
                countryAlreadyExists = false;
            } while (cursor.moveToNext());
        }
        return returnList;
    }

    // Get states
    public List<String> getStates() {
        List<String> returnList = new ArrayList<>();
        boolean stateAlreadyExists = false;
        String queryString = "SELECT " + COLUMN_STATE_NAME + " FROM " + TABLE_COUNTRIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                for (String i:returnList) {
                    if (i.equals(cursor.getString(0))) {
                        stateAlreadyExists = true;
                    }
                }
                if (!stateAlreadyExists) {
                    returnList.add(cursor.getString(0));
                }
                stateAlreadyExists = false;
            } while (cursor.moveToNext());
        }
        return returnList;
    }

    // Get cities
    public List<String> getCities() {
        List<String> returnList = new ArrayList<>();
        boolean cityAlreadyExists = false;
        String queryString = "SELECT " + COLUMN_CITY_NAME + " FROM " + TABLE_COUNTRIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                for (String i:returnList) {
                    if (i.equals(cursor.getString(0))) {
                        cityAlreadyExists = true;
                    }
                }
                if (!cityAlreadyExists) {
                    returnList.add(cursor.getString(0));
                }
                cityAlreadyExists = false;
            } while (cursor.moveToNext());
        }
        return returnList;
    }

    // Validate User
    public boolean existsUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT " + COLUMN_EMAIL + " FROM " + TABLE_USERS + " WHERE " +
                COLUMN_EMAIL + " = '" + email + "'";
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    // Register User
    public boolean registerUser(User newUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_FIRST_NAME, newUser.getFirstName());
        cv.put(COLUMN_LAST_NAME, newUser.getLastName());
        cv.put(COLUMN_EMAIL, newUser.getEmail());
        cv.put(COLUMN_PASSWORD, newUser.getPassword());
        cv.put(COLUMN_COUNTRY_USERS, newUser.getCountry());
        cv.put(COLUMN_STATE_USERS, newUser.getState());
        cv.put(COLUMN_CITY_USERS, newUser.getCity());

        long insert = db.insert(TABLE_USERS,null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    // Get user ID
    public int getId (String email) {
        SQLiteDatabase db = getReadableDatabase();
        String queryString = "SELECT " + COLUMN_ID + " FROM " + TABLE_USERS + " WHERE "
                + COLUMN_EMAIL + " = '" + email + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        } else {
            return -1;
        }
    }


    // Check if the password is correct
    public boolean correctPassword(String email, String password) {
        SQLiteDatabase db = getReadableDatabase();
        String queryString = "SELECT " +  COLUMN_EMAIL + " FROM " + TABLE_USERS + " WHERE " +
                COLUMN_EMAIL + " = '" + email + "' AND " + COLUMN_PASSWORD + " = '" +
                password + "'";
        Cursor cursor = db.rawQuery(queryString,null);
        if (cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }

    // Log in User
    public void logInUser() {

    }

    // Add Stamp
    public void addStamp() {

    }

}
