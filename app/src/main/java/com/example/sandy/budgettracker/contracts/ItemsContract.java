package com.example.sandy.budgettracker.contracts;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class ItemsContract {
    public static final String CONTENT_AUTHORITY = "com.example.sandy.budgettracker.items";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_ITEMS = "items";


    public static class ItemsEntry implements BaseColumns {
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_ITEMS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_ITEMS;


        public static final String TABLE_NAME = "items";

        public static final String _Id = BaseColumns._ID;
        public static final String COLUMN_ITEM_NAME = "name";
        public static final String COLUMN_ITEM_TYPE = "type";
        public static final String COLUMN_ITEM_LOGO = "logo";
        public static final String COLUMN_ITEM_COLOR = "color";
        public static final String COLUMN_ITEM_CREATED_DATE = "created_date";


    }
}
