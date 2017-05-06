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

import com.example.sandy.budgettracker.contracts.UserContract;
import com.example.sandy.budgettracker.helper.BudgetTrackerDbHelper;


public class UserProvider extends ContentProvider {

    public static final String LOG_TAG = UserProvider.class.getSimpleName();

    private BudgetTrackerDbHelper helper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int USERS = 100;
    private static final int USER_ID = 101;

    // Static initializer. This is run the first time anything is called from this class.
    static {

        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USERS, USERS);

        sUriMatcher.addURI(UserContract.CONTENT_AUTHORITY, UserContract.PATH_USERS + "/#", USER_ID);
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
            case USERS:
                cursor = database.query(UserContract.UserEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case USER_ID:
                selection = UserContract.UserEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(UserContract.UserEntry.TABLE_NAME, projection, selection, selectionArgs,
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
            case USERS:
                return insertUser(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertUser(Uri uri, ContentValues values) {
        // Check that the name is not null
        String email = values.getAsString(UserContract.UserEntry.COLUMN_USER_EMAIL);
        if (email == null) {
            throw new IllegalArgumentException("User requires a email");
        }

        String password = values.getAsString(UserContract.UserEntry.COLUMN_USER_PASSWORD);
        if (password == null) {
            throw new IllegalArgumentException("User requires valid password");
        }

        SQLiteDatabase database = helper.getWritableDatabase();

        long id = database.insert(UserContract.UserEntry.TABLE_NAME, null, values);
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
            case USERS:
                rowsDeleted = database.delete(UserContract.UserEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case USER_ID:
                selection = UserContract.UserEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(UserContract.UserEntry.TABLE_NAME, selection, selectionArgs);
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
            case USERS:
                return updateUser(uri, values, selection, selectionArgs);
            case USER_ID:
                selection = UserContract.UserEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateUser(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateUser(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.size() == 0) {
            return 0;
        }

        String email = values.getAsString(UserContract.UserEntry.COLUMN_USER_EMAIL);
        if (email == null) {
            throw new IllegalArgumentException("User requires a email");
        }

        String password = values.getAsString(UserContract.UserEntry.COLUMN_USER_PASSWORD);
        if (password == null) {
            throw new IllegalArgumentException("User requires valid password");
        }



        SQLiteDatabase database = helper.getWritableDatabase();
        int rowsUpdated = database.update(UserContract.UserEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            if (getContext() != null && getContext().getContentResolver() != null)
                getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }
}
