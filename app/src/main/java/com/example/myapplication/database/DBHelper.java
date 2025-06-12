package com.example.myapplication.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "MyDataApp.db";
    private static final int DATABASE_VERSION = 1;

    // Table: users
    public static final String TABLE_USERS = "users";
    public static final String COL_USER_ID = "id";
    public static final String COL_USERNAME = "username";
    public static final String COL_EMAIL = "email";
    public static final String COL_PASSWORD = "password";

    // Table: items
    public static final String TABLE_ITEMS = "items";
    public static final String COL_ITEM_ID = "id";
    public static final String COL_ITEM_TITLE = "title";
    public static final String COL_ITEM_DESC = "description";
    public static final String COL_ITEM_USER_ID = "user_id"; // foreign key

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                COL_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_USERNAME + " TEXT UNIQUE, " +
                COL_EMAIL + " TEXT, " +
                COL_PASSWORD + " TEXT);";

        String createItemsTable = "CREATE TABLE " + TABLE_ITEMS + " (" +
                COL_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_ITEM_TITLE + " TEXT, " +
                COL_ITEM_DESC + " TEXT, " +
                COL_ITEM_USER_ID + " INTEGER, " +
                "FOREIGN KEY(" + COL_ITEM_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COL_USER_ID + "));";

        db.execSQL(createUsersTable);
        db.execSQL(createItemsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    // ✅ Register a new user
    public boolean registerUser(String username, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_EMAIL, email);
        values.put(COL_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // ✅ Check user credentials and return user ID if valid
    public int checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COL_USER_ID},
                COL_USERNAME + "=? AND " + COL_PASSWORD + "=?",
                new String[]{username, password},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(0);
            cursor.close();
            return userId;
        }

        return -1;
    }

    // 🧱 Add item (to be used later in MainActivity)
    public boolean addItem(String title, String desc, int userId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ITEM_TITLE, title);
        values.put(COL_ITEM_DESC, desc);
        values.put(COL_ITEM_USER_ID, userId);

        long result = db.insert(TABLE_ITEMS, null, values);
        return result != -1;
    }

    // 📦 Get all items for a user
    public Cursor getItemsForUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ITEMS, null, COL_ITEM_USER_ID + "=?",
                new String[]{String.valueOf(userId)}, null, null, COL_ITEM_ID + " DESC");
    }

    // 📝 Update item
    public boolean updateItem(int itemId, String title, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ITEM_TITLE, title);
        values.put(COL_ITEM_DESC, desc);

        int result = db.update(TABLE_ITEMS, values, COL_ITEM_ID + "=?", new String[]{String.valueOf(itemId)});
        return result > 0;
    }

    // ❌ Delete item
    public boolean deleteItem(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_ITEMS, COL_ITEM_ID + "=?", new String[]{String.valueOf(itemId)});
        return result > 0;
    }
}
