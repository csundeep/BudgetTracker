package com.example.sandy.budgettracker.contracts;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class BudgetsContract {
    public static final String CONTENT_AUTHORITY = "com.example.sandy.budgettracker.budgets";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_BUDGETS = "budgets";


    public static class BudgetsEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_BUDGETS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUDGETS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BUDGETS;


        public static final String TABLE_NAME = "budgets";
        public static final String _Id = BaseColumns._ID;
        public static final String COLUMN_BUDGET_NAME = "name";
        public static final String COLUMN_BUDGET_AMOUNT = "amount";
        public static final String COLUMN_BUDGET_EXPENSES = "expenses";
        public static final String COLUMN_BUDGET_START_DATE = "start_date";
        public static final String COLUMN_BUDGET_END_DATE = "end_date";
        public static final String COLUMN_BUDGET_NOTIFICATIONS = "isNotificationsRequired";
        public static final String COLUMN_BUDGET_USER_ID = "user_id";
        public static final String COLUMN_BUDGET_CREATED_DATE = "created_date";


    }
}
