package com.example.cs360projecttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.sql.SQLException;


import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import android.database.sqlite.SQLiteException;




import java.util.ArrayList;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UserAccounts.db";
    private static final String TABLE_NAME = "users";
    private static final String COL_1 = "ID";
    private static final String COL_2 = "username";
    private static final String COL_3 = "password";


    // New constants for inventory
    private static final String TABLE_INVENTORY = "inventory";
    private static final String COL_ITEM_NAME = "item_name";
    private static final String COL_QUANTITY = "quantity";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create login table
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, password TEXT)");

        // Create inventory table
        db.execSQL("CREATE TABLE " + TABLE_INVENTORY + " (" +
                COL_ITEM_NAME + " TEXT PRIMARY KEY," +
                COL_QUANTITY + " INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "  + TABLE_INVENTORY);
        onCreate(db);
    }

    // Insert user data to login
    public boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, username); // collum for username
        values.put(COL_3, password); // collum for password
        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Check user credentials
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE username = ? AND password = ?", new String[]{username, password});
        return cursor.getCount() > 0;
    }

    // Add new inventory item
    public boolean addInventoryItem(String itemName, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ITEM_NAME, itemName);
        contentValues.put(COL_QUANTITY, quantity);
        try {
            long result = db.insertOrThrow(TABLE_INVENTORY, null, contentValues);
            Log.d("DatabaseHelper", "Item added to database. Result: " + result);
            return result != -1;
        } catch (SQLiteException e) { // Catch SQLiteException instead of SQLException
            Log.e("DatabaseHelper", "Error inserting item into database: " + e.getMessage());
            return false;
        } finally {
            db.close(); // Close the database connection
        }
    }


    // Update inventory item quantity
    public boolean updateInventoryItemQuantity(String itemName, int newQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_QUANTITY, newQuantity);
        int result = db.update(TABLE_INVENTORY, contentValues, COL_ITEM_NAME + "=?", new String[]{itemName});
        return result > 0;
    }

    // Delete inventory item
    public boolean deleteInventoryItem(String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_INVENTORY, COL_ITEM_NAME + "=?", new String[]{itemName});
        return result > 0;
    }

    // Get all inventory items
    public List<String> getAllInventoryItems() {
        List<String> itemsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INVENTORY, null);
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    String itemName = cursor.getString(cursor.getColumnIndexOrThrow(COL_ITEM_NAME));
                    int quantity = cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTITY));
                    itemsList.add(itemName + ": " + quantity);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }
        db.close();
        return itemsList;
    }




}

