package com.emmanuel.sqlito;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "productsdb";
    private static final String TABLE = "products";

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KEY_NAME + " TEXT, "
                + KEY_PRICE + " TEXT)";
        db.execSQL(CREATE_TABLE);
    }

    public void insertProduct(String name, String price) {
        // Get the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, name);
        contentValues.put(KEY_PRICE, price);

        long newRowId = db.insert(TABLE, null, contentValues);
        db.close();
    }

    public Cursor getProducts() {
        String query = "SELECT * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public Cursor getProductById(int id) {
        String query = "SELECT * FROM " + TABLE + " WHERE id=" + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        return cursor;
    }

    public void updateProductById(int productId, String name, String price) {
        String query = "UPDATE " + TABLE + " SET name='" + name + "', price='" + price + "' WHERE id=" + productId;

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

    public void deleteProductById(int id) {
        String query = "DELETE FROM " + TABLE + " WHERE id=" + id;

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        // Create tables again
        onCreate(db);
    }
}
