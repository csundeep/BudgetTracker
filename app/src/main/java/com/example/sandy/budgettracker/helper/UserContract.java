package com.example.sandy.budgettracker.helper;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class UserContract {
    public static final String CONTENT_AUTHORITY = "com.example.sandy.budgettracker.users";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_USERS = "users";


    public static class UserEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_USERS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;


        public static final String TABLE_NAME = "users";

        public static final String _Id = BaseColumns._ID;
        public static final String COLUMN_USER_FIRST_NAME = "first_name";
        public static final String COLUMN_USER_LAST_NAME = "last_name";
        public static final String COLUMN_USER_EMAIL = "email";
        public static final String COLUMN_USER_PASSWORD = "password";
        public static final String COLUMN_USER_MOBILE = "mobile";
        public static final String COLUMN_USER_WALLET_AMOUNT = "initial_amount";

        public static final String COLUMN_USER_CREATED_DATE = "created_date";


    }
}
