package com.example.sandy.budgettracker.contracts;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class BudgetItemContract {
    public static final String CONTENT_AUTHORITY = "com.example.sandy.budgettracker.budgetItems";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BUDGET_ITEMS = "budgetItems";


    public static class BudgetItemEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BUDGET_ITEMS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUDGET_ITEMS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUDGET_ITEMS;


        public static final String TABLE_NAME = "BUDGET_ITEMS";

        public static final String _Id = BaseColumns._ID;
        public static final String COLUMN_BUDGET_ITEMS_BUDGET_ID = "budget_id";
        public static final String COLUMN_BUDGET_ITEMS_ITEM_ID = "item_id";
        public static final String COLUMN_BUDGET_ITEMS_USER_ID = "user_id";
        public static final String COLUMN_BUDGET_ITEMS_CREATED_DATE = "created_date";


    }
}
