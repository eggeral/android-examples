package ese.database;


import android.provider.BaseColumns;

public class BookStoreContract {

    // there are no instances of contracts
    private BookStoreContract() {
    }

    // Describes a table
    // Implementing BaseColumns makes the entity available for things like lists etc
    public static class BookTable implements BaseColumns {
        public static final String TABLE_NAME = "book";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_AUTHOR = "author";
        public static final String COLUMN_NAME_ISBN = "isbn";
    }
}


