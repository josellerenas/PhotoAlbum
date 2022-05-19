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

    public static final String TABLE_CITIES = "CITIES";
    public static final String COLUMN_CITY_ID = "CITY_ID";
    public static final String COLUMN_COUNTRY_NAME = "COUNTRY_NAME";
    public static final String COLUMN_STATE_NAME = "STATE_NAME";
    public static final String COLUMN_CITY_NAME = "CITY_NAME";

    public static final String TABLE_USERS = "USERS";
    public static final String COLUMN_USER_ID = "USER_ID";
    public static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    public static final String COLUMN_LAST_NAME = "LAST_NAME";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PASSWORD = "PASSWORD";
    public static final String COLUMN_COUNTRY = "COUNTRY";
    public static final String COLUMN_STATE = "STATE";
    public static final String COLUMN_CITY = "CITY";

    public static final String TABLE_STAMPS = "STAMPS";
    public static final String COLUMN_STAMP_NAME = "STAMP_NAME";
    public static final String COLUMN_STAMP_ID = "STAMP_ID";

    public static final String TABLE_USERS_STAMPS = "USERS_STAMPS";
    public static final String COLUMN_UPLOAD_DATE = "UPLOAD_DATE";
    public static final String COLUMN_STAMP_LINK = "STAMP_LINK";

    /* This is one of the constructors defined in SQLiteOpenHelper class.
        We modified the constructor with hardcoded data */
    public DatabaseHelper(@Nullable Context context) {
        super(context, "photoalbum_v2.db", null, 1);
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
        String queryString = "SELECT " + COLUMN_COUNTRY_NAME + " FROM " + TABLE_CITIES;
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
        String queryString = "SELECT " + COLUMN_STATE_NAME + " FROM " + TABLE_CITIES;
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
        String queryString = "SELECT " + COLUMN_CITY_NAME + " FROM " + TABLE_CITIES;
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
        cv.put(COLUMN_COUNTRY, newUser.getCountry());
        cv.put(COLUMN_STATE, newUser.getState());
        cv.put(COLUMN_CITY, newUser.getCity());

        long insert = db.insert(TABLE_USERS,null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    // Get user first name
    public String getFirstName(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String queryString = "SELECT " + COLUMN_FIRST_NAME + " FROM " + TABLE_USERS + " WHERE "
                + COLUMN_EMAIL + " = '" + email + "'";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            return cursor.getString(0);
        } else {
            return "Not found";
        }
    }

    // Get the cities from a certain state (user's state), to fill the recyclerviews
    // This example uses Colima.
    public List<String> getUsersCities(String email) {
        // In this case, because my user is from Colima, I need to select the ten cities from Colima
        String state = "";
        List<String> returnList = new ArrayList<>();
        String queryString = "SELECT " + COLUMN_STATE + " FROM " + TABLE_USERS + " WHERE " +
                COLUMN_EMAIL + " = '" + email + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            state = cursor.getString(0);
        }

        queryString = "SELECT " + COLUMN_CITY_NAME + " FROM " + TABLE_CITIES + " WHERE " +
                COLUMN_STATE_NAME + " = '" + state + "'";
        cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                returnList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return returnList;
    }

    public List<String> getCitysStamps(String city) {

        int cityID = 0;
        List<String> returnList = new ArrayList<>();
        // Query to know the city ID
        String queryString = "SELECT " + COLUMN_CITY_ID + " FROM " + TABLE_CITIES + " WHERE " +
                COLUMN_CITY_NAME + " = '" + city + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            cityID = cursor.getInt(0);
        }

        queryString = "SELECT " + COLUMN_STAMP_NAME + " FROM " + TABLE_STAMPS + " WHERE " +
                COLUMN_CITY_ID + " = '" + cityID + "'";
        cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                returnList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        return returnList;
    }

    public int getUserID(String userEmail) {
        int userID = 0;
        String queryString = "SELECT " + COLUMN_USER_ID + " FROM " + TABLE_USERS + " WHERE " +
                COLUMN_EMAIL + " = '" + userEmail + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            userID = cursor.getInt(0);
        }
        return userID;
    }

    public int getStampID(String stampName) {
        int stampID = 0;
        String queryString = "SELECT " + COLUMN_STAMP_ID + " FROM " + TABLE_STAMPS + " WHERE " +
                COLUMN_STAMP_NAME + " = '" + stampName + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            stampID = cursor.getInt(0);
        }
        return stampID;
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

    // Save Stamp
    public boolean saveStamp(int userID, int stampID, String uploadDate, String stampLink) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_ID, userID);
        cv.put(COLUMN_STAMP_ID, stampID);
        cv.put(COLUMN_UPLOAD_DATE, uploadDate);
        cv.put(COLUMN_STAMP_LINK, stampLink);

        long insert = db.insert(TABLE_USERS_STAMPS,null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

}
