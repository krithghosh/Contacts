package com.gojek.krith.contacts.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by krith on 31/01/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "ContactDB";
    private static final int DB_VERSION = 1;

    private final Context context;
    private static DatabaseHelper instance;
    private static SQLiteDatabase writableDb;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public static DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseHelper(context);
        }
        return instance;
    }

    @Override
    public void close() {
        super.close();
        if (writableDb != null) {
            writableDb.close();
            writableDb = null;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ContactTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
