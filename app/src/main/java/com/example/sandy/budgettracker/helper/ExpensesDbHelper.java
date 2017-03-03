package com.example.sandy.budgettracker.helper;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ExpensesDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = ExpensesDbHelper.class.getSimpleName();

    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "budget.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link ExpensesDbHelper}.
     *
     * @param context of the app
     */
    public ExpensesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the expenses table
        String SQL_CREATE_EXPENSES_TABLE = "CREATE TABLE " + ExpensesContract.ExpenseEntry.TABLE_NAME + " ("
                + ExpensesContract.ExpenseEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME + " TEXT NOT NULL, "
                + ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT + " DOUBLE NOT NULL DEFAULT 0, "
                + ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES + " TEXT , "
                + ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE + " DATE NOT NULL);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_EXPENSES_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
