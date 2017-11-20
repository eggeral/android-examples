package ese.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ese.database.BookStoreContract.BookTable;

public class BookStoreDbOpenHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BookStore.db";

    public BookStoreDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String SQL_CREATE_BOOK_TABLE =
            "CREATE TABLE " + BookTable.TABLE_NAME + " (" +
                    BookTable._ID + " INTEGER PRIMARY KEY," +
                    BookTable.COLUMN_NAME_TITLE + " TEXT," +
                    BookTable.COLUMN_NAME_AUTHOR + " TEXT," +
                    BookTable.COLUMN_NAME_ISBN + " TEXT)";

    private static final String SQL_DROP_BOOK_TABLE =
            "DROP TABLE IF EXISTS " + BookTable.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //  in real world upgrade cases we should upgrade the schema not just delete all the data
        db.execSQL(SQL_DROP_BOOK_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // not required, but useful in some cases
        onUpgrade(db, oldVersion, newVersion);
    }
}
