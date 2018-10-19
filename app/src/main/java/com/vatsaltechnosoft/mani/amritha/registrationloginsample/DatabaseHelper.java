package com.vatsaltechnosoft.mani.amritha.registrationloginsample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.StaticLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amritha on 6/26/18.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    public static final String DATABASE_NAME = "RegistrationLogin.db";

    // table name
    public static final String TABLE_NAME = "User";

    // Admin Table Columns names

    public static final String COLUMN_USER_ID = "user_id";

    public static final String COLUMN_USER_FIRST_NAME = "user_first_name";

    public static final String COLUMN_USER_LAST_NAME = "user_last_name";

    public static final String COLUMN_USER_EMAIL = "user_email";

    public static final String COLUMN_USER_NAME = "user_name";

    public static final String COLUMN_USER_PASSWORD = "user_password";

    public static final String COLUMN_USER_GENDER = "user_gender";

    public static final String COLUMN_USER_TYPE = "user_type";

    public static final String COLUMN_USER_HOBBIES = "user_hobbies";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USER_FIRST_NAME + " TEXT,"
            + COLUMN_USER_LAST_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT,"
            + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_PASSWORD + " TEXT,"
            + COLUMN_USER_GENDER + " TEXT,"
            + COLUMN_USER_TYPE + " TEXT,"
            + COLUMN_USER_HOBBIES + " TEXT" + ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        onCreate(db);
    }

    /**
     * This method is to create user record
     *
     * @param user
     */

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUMN_USER_FIRST_NAME, user.getFirstName());
        values.put(COLUMN_USER_LAST_NAME, user.getLastName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_NAME, user.getUserName());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_GENDER, user.getGender());
        values.put(COLUMN_USER_TYPE, user.getUserType());
        values.put(COLUMN_USER_HOBBIES, user.getHobbies());

        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */

    public List<User> getAllUser() {
        //array of columns to fetch

        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_FIRST_NAME,
                COLUMN_USER_LAST_NAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD,
                COLUMN_USER_GENDER,
                COLUMN_USER_TYPE,
                COLUMN_USER_HOBBIES
        };

        // sorting orders
        String sortOrder = COLUMN_USER_NAME + " ASC";

        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table

        Cursor cursor = db.query(
                TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder
        );

        // Traversing through all rows and adding to list

        if (cursor.moveToFirst()) {

            do {
                User user = new User();

                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setFirstName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME)));
                user.setLastName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_USER_GENDER)));
                user.setUserType(cursor.getString(cursor.getColumnIndex(COLUMN_USER_TYPE)));
                user.setHobbies(cursor.getString(cursor.getColumnIndex(COLUMN_USER_HOBBIES)));

                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_USER_FIRST_NAME, user.getFirstName());
        contentValues.put(COLUMN_USER_LAST_NAME, user.getLastName());
        contentValues.put(COLUMN_USER_EMAIL, user.getEmail());
        contentValues.put(COLUMN_USER_NAME, user.getUserName());
        contentValues.put(COLUMN_USER_PASSWORD, user.getPassword());
        contentValues.put(COLUMN_USER_GENDER, user.getGender());
        contentValues.put(COLUMN_USER_TYPE, user.getUserType());
        contentValues.put(COLUMN_USER_HOBBIES, user.getHobbies());

        db.update(TABLE_NAME, contentValues, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});

        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */

    public void deleteUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();

        // delete user record by id
        db.delete(TABLE_NAME, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(user.getId())});

        db.close();

    }

    /**
     * This method to check user exist or not
     *
     * @param userName
     * @return true/false
     */

    public boolean checkUser(String userName) {
        // array of columns to fetch

        String[] columns = {
                COLUMN_USER_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria

        String selection = COLUMN_USER_NAME + " = ?";

        // selection argument

        String[] selectionArgs = {userName};

        // query user table with condition

        Cursor cursor = db.query(
                TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;

    }

    /**
     * This method to check user exist or not
     *
     * @param userName
     * @param password
     * @return true/false
     */

    public boolean checkUser(String userName, String password) {
        // array of columns to fetch

        String[] columns = {
                COLUMN_USER_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria

        String selection = COLUMN_USER_NAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection argument

        String[] selectionArgs = {userName, password};

        // query user table with condition

        Cursor cursor = db.query(
                TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;

    }


}
