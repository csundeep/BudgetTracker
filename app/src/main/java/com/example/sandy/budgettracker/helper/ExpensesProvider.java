package com.example.sandy.budgettracker.helper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Sundeep.Chilukur on 3/3/2017.
 */
public class ExpensesProvider extends ContentProvider {

    public static final String LOG_TAG = ExpensesProvider.class.getSimpleName();

    private ExpensesDbHelper helper;
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
        helper = new ExpensesDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
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

        Integer gender = values.getAsInteger(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT);
        if (gender == null ) {
            throw new IllegalArgumentException("Expense requires valid amount");
        }

        Integer weight = values.getAsInteger(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE);
        if (weight != null ) {
            throw new IllegalArgumentException("Expense requires created expense date");
        }


        SQLiteDatabase database = helper.getWritableDatabase();

        long id = database.insert(ExpensesContract.ExpenseEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
