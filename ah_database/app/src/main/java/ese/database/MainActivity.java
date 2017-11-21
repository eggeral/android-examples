package ese.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ese.database.BookStoreContract.BookTable;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "ese.database";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // == 01 create contract class BookStoreContract
        // == 02 create SqlOpenHelper
        BookStoreDbOpenHelper dbHelper = new BookStoreDbOpenHelper(this);

        // == 03 write some data
        // This is long running and should be done in the background
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(BookTable.COLUMN_NAME_TITLE, "Lord of the Rings - The Fellowship of the Ring");
        values.put(BookTable.COLUMN_NAME_AUTHOR, "J. R. R. Tolkien");
        values.put(BookTable.COLUMN_NAME_ISBN, "12345678");

        // Insert the new row, returning the primary key value of the new row
        long newRowId = dbWriter.insert(BookTable.TABLE_NAME, null, values);

        values = new ContentValues();
        values.put(BookTable.COLUMN_NAME_TITLE, "The Hitchhiker's Guide to the Galaxy");
        values.put(BookTable.COLUMN_NAME_AUTHOR, "Douglas Adams");
        values.put(BookTable.COLUMN_NAME_ISBN, "0909090909");

        newRowId = dbWriter.insert(BookTable.TABLE_NAME, null, values);
        dbWriter.close(); // Keep around the dbWriter instance as long as possible. Do this in onDestroy()


        // == 04 read the data again
        SQLiteDatabase dbReader = dbHelper.getReadableDatabase();

        String[] projection = {
                BookTable._ID,
                BookTable.COLUMN_NAME_TITLE,
                BookTable.COLUMN_NAME_AUTHOR,
                BookTable.COLUMN_NAME_ISBN
        };

        String selection = BookTable.COLUMN_NAME_TITLE + " = ?";
        String[] selectionArgs = {"The Hitchhiker's Guide to the Galaxy"};

        // How you want the results sorted in the resulting Cursor
        String sortOrder =
                BookTable.COLUMN_NAME_TITLE + " DESC";

        Cursor cursor = dbReader.query(
                BookTable.TABLE_NAME,                          // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                sortOrder                                 // The sort order
        );

        final List<Map<String, Object>> books = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(BookTable._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(BookTable.COLUMN_NAME_TITLE));
            String author = cursor.getString(cursor.getColumnIndexOrThrow(BookTable.COLUMN_NAME_AUTHOR));
            String isbn = cursor.getString(cursor.getColumnIndexOrThrow(BookTable.COLUMN_NAME_ISBN));
            Map<String, Object> data = new HashMap<>();
            data.put(BookTable._ID, id);
            data.put(BookTable.COLUMN_NAME_TITLE, title);
            data.put(BookTable.COLUMN_NAME_AUTHOR, author);
            data.put(BookTable.COLUMN_NAME_ISBN, isbn);
            books.add(data);
            Log.i(TAG, "onCreate: " + title);
        }
        cursor.close();
        dbReader.close();

        // == 05 delete entries
//        dbWriter = dbHelper.getWritableDatabase();
//        selection = BookTable.COLUMN_NAME_TITLE + " LIKE ?";
//        selectionArgs = new String[]{"The%"};
//        dbWriter.delete(BookTable.TABLE_NAME, selection, selectionArgs);
//
//        // == 06 update entries
//        dbWriter = dbHelper.getWritableDatabase();
//
//        values = new ContentValues();
//        values.put(BookTable.COLUMN_NAME_TITLE, "Updated Title");
//
//        selection = BookTable.COLUMN_NAME_TITLE + " LIKE ?";
//        selectionArgs = new String[]{"Lord%"};
//
//        int count = dbWriter.update(
//                BookTable.TABLE_NAME,
//                values,
//                selection,
//                selectionArgs);
//        Log.i(TAG, "onCreate: Updated items:" + count);

        // == 07 show items in a list
        // add list to layout
        String[] fromColumns = {BookTable.COLUMN_NAME_TITLE, BookTable.COLUMN_NAME_AUTHOR, BookTable.COLUMN_NAME_ISBN};
        int[] toViews = {android.R.id.text1, android.R.id.text2, android.R.id.text2};
        ListView bookList = (ListView) findViewById(R.id.bookList);
        ListAdapter adapter = new SimpleAdapter(
                this,
                books,
                android.R.layout.simple_list_item_1,
                fromColumns,
                toViews
        );
        bookList.setAdapter(adapter);
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(TAG, "onItemClick: position: " + position + ", id: " + id);
                Log.i(TAG, "onItemClick: book selected has database id: " + books.get(position).get(BookTable._ID));
            }
        });

        // Example: Create a simple shopping list (special task; add images to the items)
    }
}
