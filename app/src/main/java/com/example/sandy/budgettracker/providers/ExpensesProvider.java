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

import com.example.sandy.budgettracker.contracts.ExpensesContract;
import com.example.sandy.budgettracker.helper.BudgetTrackerDbHelper;


public class ExpensesProvider extends ContentProvider {

    public static final String LOG_TAG = ExpensesProvider.class.getSimpleName();

    private BudgetTrackerDbHelper helper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int EXPENSES = 100;
    private static final int EXPENSE_ID = 101;

    // Static initializer. This is run the first time anything is called from this class.
    static {

        sUriMatcher.addURI(ExpensesContract.CONTENT_AUTHORITY, ExpensesContract.PATH_EXPENSES, EXPENSES);

        sUriMatcher.addURI(ExpensesContract.CONTENT_AUTHORITY, ExpensesContract.PATH_EXPENSES + "/#", EXPENSE_ID);
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
            case EXPENSES:
                cursor = database.query(ExpensesContract.ExpenseEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case EXPENSE_ID:
                selection = ExpensesContract.ExpenseEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = database.query(ExpensesContract.ExpenseEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }
        if (getContext() != null && getContext().getContentResolver() != null) {
            cursor.setNotificationUri(getContext().getContentResolver(), uri);
        }
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
            case EXPENSES:
                return insertExpense(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertExpense(Uri uri, ContentValues values) {
        // Check that the name is not null
        String name = values.getAsString(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Expense requires a name");
        }

        Integer amount = values.getAsInteger(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT);
        if (amount == null) {
            throw new IllegalArgumentException("Expense requires valid amount");
        }


        SQLiteDatabase database = helper.getWritableDatabase();

        long id = database.insert(ExpensesContract.ExpenseEntry.TABLE_NAME, null, values);
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
            case EXPENSES:
                rowsDeleted = database.delete(ExpensesContract.ExpenseEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case EXPENSE_ID:
                selection = ExpensesContract.ExpenseEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ExpensesContract.ExpenseEntry.TABLE_NAME, selection, selectionArgs);
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
            case EXPENSES:
                return updateExpense(uri, values, selection, selectionArgs);
            case EXPENSE_ID:
                selection = ExpensesContract.ExpenseEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateExpense(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateExpense(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.size() == 0) {
            return 0;
        }

        String name = values.getAsString(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME);
        if (name == null) {
            throw new IllegalArgumentException("Expense requires a name");
        }

        Integer amount = values.getAsInteger(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT);
        if (amount == null) {
            throw new IllegalArgumentException("Expense requires valid amount");
        }


        SQLiteDatabase database = helper.getWritableDatabase();
        int rowsUpdated = database.update(ExpensesContract.ExpenseEntry.TABLE_NAME, values, selection, selectionArgs);
        if (rowsUpdated != 0) {
            if (getContext() != null && getContext().getContentResolver() != null)
                getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsUpdated;
    }


}
