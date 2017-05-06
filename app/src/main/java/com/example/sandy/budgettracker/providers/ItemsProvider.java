package com.example.sandy.budgettracker.providers;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.sandy.budgettracker.contracts.ItemsContract;
import com.example.sandy.budgettracker.helper.BudgetTrackerDbHelper;


public class ItemsProvider extends ContentProvider {

    public static final String LOG_TAG = ItemsProvider.class.getSimpleName();

    private BudgetTrackerDbHelper helper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int ITEMS = 100;
    private static final int ITEMS_ID = 101;

    // Static initializer. This is run the first time anything is called from this class.
    static {

        sUriMatcher.addURI(ItemsContract.CONTENT_AUTHORITY, ItemsContract.PATH_ITEMS, ITEMS);

        sUriMatcher.addURI(ItemsContract.CONTENT_AUTHORITY, ItemsContract.PATH_ITEMS + "/#", ITEMS_ID);
    }

    @Override
    public boolean onCreate() {
        helper = new BudgetTrackerDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                cursor = database.query(ItemsContract.ItemsEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ITEMS_ID:
                selection = ItemsContract.ItemsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ItemsContract.ItemsEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        if (getContext() != null && getContext().getContentResolver() != null)
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return insertITEM(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertITEM(Uri uri, ContentValues values) {
        // Check that the name is not null
        String name = values.getAsString(ItemsContract.ItemsEntry.COLUMN_ITEM_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Item requires a name");
        }

        String type = values.getAsString(ItemsContract.ItemsEntry.COLUMN_ITEM_TYPE);
        if (type == null) {
            throw new IllegalArgumentException("Item requires valid type");
        }


        SQLiteDatabase database = helper.getWritableDatabase();

        long id = database.insert(ItemsContract.ItemsEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        if (getContext() != null && getContext().getContentResolver() != null)
            getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = helper.getWritableDatabase();
        int rowsDeleted;
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                rowsDeleted = database.delete(ItemsContract.ItemsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case ITEMS_ID:
                selection = ItemsContract.ItemsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ItemsContract.ItemsEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            if (getContext() != null && getContext().getContentResolver() != null)
                getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return updateItem(uri, values, selection, selectionArgs);
            case ITEMS_ID:
                selection = ItemsContract.ItemsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateItem(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateItem(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.size() == 0) {
            return 0;
        }

        String name = values.getAsString(ItemsContract.ItemsEntry.COLUMN_ITEM_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Items requires a name");
        }

        String type = values.getAsString(ItemsContract.ItemsEntry.COLUMN_ITEM_TYPE);
        if (type == null) {
            throw new IllegalArgumentException("Item requires valid type");
        }


        SQLiteDatabase database = helper.getWritableDatabase();
        int rowsUpdated = database.update(ItemsContract.ItemsEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            if (getContext() != null && getContext().getContentResolver() != null)
                getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }


}
