package com.example.sandy.budgettracker.contracts;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class ExpensesContract {
    public static final String CONTENT_AUTHORITY = "com.example.sandy.budgettracker.expenses";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_EXPENSES = "expenses";


    public static class ExpenseEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EXPENSES);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXPENSES;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EXPENSES;


        public static final String TABLE_NAME = "expenses";

        public static final String _Id = BaseColumns._ID;
        public static final String COLUMN_EXPENSE_NAME = "name";
        public static final String COLUMN_EXPENSE_TYPE = "type";
        public static final String COLUMN_EXPENSE_AMOUNT = "amount";
        public static final String COLUMN_EXPENSE_NOTES = "notes";
        public static final String COLUMN_EXPENSE_USER_ID = "user_id";
        public static final String COLUMN_EXPENSE_ITEM_ID = "item_id";
        public static final String COLUMN_EXPENSE_CREATED_DATE = "created_date";


    }
}
