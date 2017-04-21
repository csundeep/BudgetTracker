package com.example.sandy.budgettracker.helper;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.contracts.BudgetItemContract;
import com.example.sandy.budgettracker.contracts.BudgetsContract;
import com.example.sandy.budgettracker.contracts.ExpensesContract;
import com.example.sandy.budgettracker.contracts.ItemsContract;
import com.example.sandy.budgettracker.contracts.UserContract;
import com.example.sandy.budgettracker.data.ExpenseItem;

import java.util.ArrayList;


public class BudgetTrackerDbHelper extends SQLiteOpenHelper {


    /**
     * Name of the database file
     */
    private static final String DATABASE_NAME = "budget.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link BudgetTrackerDbHelper}.
     *
     * @param context of the app
     */
    public BudgetTrackerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create a String that contains the SQL statement to create the items table
        String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE " + ItemsContract.ItemsEntry.TABLE_NAME + " ("
                + ItemsContract.ItemsEntry._Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ItemsContract.ItemsEntry.COLUMN_ITEM_NAME + " TEXT NOT NULL, "
                + ItemsContract.ItemsEntry.COLUMN_ITEM_TYPE + " TEXT NOT NULL, "
                + ItemsContract.ItemsEntry.COLUMN_ITEM_LOGO + " INTEGER NOT NULL DEFAULT 0, "
                + ItemsContract.ItemsEntry.COLUMN_ITEM_COLOR + " INTEGER NOT NULL DEFAULT 0, "
                + ItemsContract.ItemsEntry.COLUMN_ITEM_CREATED_DATE + " DATE NOT NULL DEFAULT CURRENT_TIMESTAMP);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_ITEMS_TABLE);

        // Create a String that contains the SQL statement to create the users table
        String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + UserContract.UserEntry.TABLE_NAME + " ("
                + UserContract.UserEntry._Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + UserContract.UserEntry.COLUMN_USER_EMAIL + " TEXT NOT NULL unique, "
                + UserContract.UserEntry.COLUMN_USER_PASSWORD + " TEXT NOT NULL, "
                + UserContract.UserEntry.COLUMN_USER_FIRST_NAME + " TEXT , "
                + UserContract.UserEntry.COLUMN_USER_LAST_NAME + " TEXT , "
                + UserContract.UserEntry.COLUMN_USER_MOBILE + " TEXT , "
                + UserContract.UserEntry.COLUMN_USER_WALLET_AMOUNT + " DOUBLE NOT NULL DEFAULT 0 , "
                + UserContract.UserEntry.COLUMN_USER_CREATED_DATE + " TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_USERS_TABLE);

        // Create a String that contains the SQL statement to create the budgets table
        String SQL_CREATE_BUDGETS_TABLE = "CREATE TABLE " + BudgetsContract.BudgetsEntry.TABLE_NAME + " ("
                + BudgetsContract.BudgetsEntry._Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME + " TEXT NOT NULL, "
                + BudgetsContract.BudgetsEntry.COLUMN_BUDGET_AMOUNT + " DOUBLE NOT NULL DEFAULT 0, "
                + BudgetsContract.BudgetsEntry.COLUMN_BUDGET_START_DATE + " DATE, "
                + BudgetsContract.BudgetsEntry.COLUMN_BUDGET_END_DATE + " DATE, "
                + BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NOTIFICATIONS + " NUMERIC NOT NULL, "
                + BudgetsContract.BudgetsEntry.COLUMN_BUDGET_USER_ID + " INTEGER NOT NULL , "
                + BudgetsContract.BudgetsEntry.COLUMN_BUDGET_CREATED_DATE + " DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,  " +
                "FOREIGN KEY(" + BudgetsContract.BudgetsEntry.COLUMN_BUDGET_USER_ID + ") REFERENCES "
                + UserContract.UserEntry.TABLE_NAME + "(" + UserContract.UserEntry._ID + "));";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BUDGETS_TABLE);

        // Create a String that contains the SQL statement to create the budget item table
        String SQL_CREATE_BUDGETS_ITEMS_TABLE = "CREATE TABLE " + BudgetItemContract.BudgetItemEntry.TABLE_NAME + " ("
                + BudgetItemContract.BudgetItemEntry._Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BudgetItemContract.BudgetItemEntry.COLUMN_BUDGET_ITEMS_BUDGET_ID + " INTEGER NOT NULL, "
                + BudgetItemContract.BudgetItemEntry.COLUMN_BUDGET_ITEMS_ITEM_ID + " INTEGER NOT NULL, "
                + BudgetItemContract.BudgetItemEntry.COLUMN_BUDGET_ITEMS_USER_ID + " INTEGER NOT NULL, "
                + BudgetItemContract.BudgetItemEntry.COLUMN_BUDGET_ITEMS_CREATED_DATE + " DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,  " +
                "FOREIGN KEY(" + BudgetItemContract.BudgetItemEntry.COLUMN_BUDGET_ITEMS_USER_ID + ") REFERENCES "
                + UserContract.UserEntry.TABLE_NAME + "(" + UserContract.UserEntry._ID + ")," +
                "FOREIGN KEY(" + BudgetItemContract.BudgetItemEntry.COLUMN_BUDGET_ITEMS_BUDGET_ID + ") REFERENCES "
                + BudgetsContract.BudgetsEntry.TABLE_NAME + "(" + BudgetsContract.BudgetsEntry._ID + ")," +
                "FOREIGN KEY(" + BudgetItemContract.BudgetItemEntry.COLUMN_BUDGET_ITEMS_ITEM_ID + ") REFERENCES "
                + ItemsContract.ItemsEntry.TABLE_NAME + "(" + ItemsContract.ItemsEntry._ID + "));";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_BUDGETS_ITEMS_TABLE);

        // Create a String that contains the SQL statement to create the expenses table
        String SQL_CREATE_EXPENSES_TABLE = "CREATE TABLE " + ExpensesContract.ExpenseEntry.TABLE_NAME + " ("
                + ExpensesContract.ExpenseEntry._Id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME + " TEXT NOT NULL, "
                + ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE + " TEXT NOT NULL, "
                + ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT + " DOUBLE NOT NULL DEFAULT 0, "
                + ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES + " TEXT , "
                + ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_USER_ID + " INTEGER NOT NULL , "
                + ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE + " DATE NOT NULL DEFAULT CURRENT_TIMESTAMP,  " +
                "FOREIGN KEY(" + ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_USER_ID + ") REFERENCES "
                + UserContract.UserEntry.TABLE_NAME + "(" + UserContract.UserEntry._ID + "));";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_EXPENSES_TABLE);

        insertItems(db);
    }

    private void insertItems(SQLiteDatabase db) {

        ArrayList<ExpenseItem> items = new ArrayList<>();
        items.add(new ExpenseItem("Car", "Expense", R.drawable.ic_sports_car, R.color.RebeccaPurple));
        items.add(new ExpenseItem("Travel", "Expense", R.drawable.ic_aeroplane, R.color.PaleVioletRed));
        items.add(new ExpenseItem("Food", "Expense", R.drawable.ic_cutlery, R.color.Brown));
        items.add(new ExpenseItem("Family", "Expense", R.drawable.ic_user, R.color.DarkRed));
        items.add(new ExpenseItem("Bills", "Expense", R.drawable.ic_payment_method, R.color.DarkGreen));
        items.add(new ExpenseItem("Entertainment", "Expense", R.drawable.ic_video_camera, R.color.Yellow));
        items.add(new ExpenseItem("Home", "Expense", R.drawable.ic_house, R.color.Tomato));
        items.add(new ExpenseItem("Utilities", "Expense", R.drawable.ic_light_bulb, R.color.DarkGray));
        items.add(new ExpenseItem("Shopping", "Expense", R.drawable.ic_shopping_cart, R.color.LightGreen));
        items.add(new ExpenseItem("Hotel", "Expense", R.drawable.ic_rural_hotel_of_three_stars, R.color.LightBlue));
        items.add(new ExpenseItem("Health Care", "Expense", R.drawable.ic_first_aid_kit, R.color.IndianRed));
        items.add(new ExpenseItem("Other", "Expense", R.drawable.ic_paper_plane, R.color.MediumPurple));
        items.add(new ExpenseItem("Clothing", "Expense", R.drawable.ic_shirt, R.color.YellowGreen));
        items.add(new ExpenseItem("Transport", "Expense", R.drawable.ic_van, R.color.Lime));
        items.add(new ExpenseItem("Groceries", "Expense", R.drawable.ic_groceries, R.color.SandyBrown));
        items.add(new ExpenseItem("Drinks", "Expense", R.drawable.ic_cocktail_glass, R.color.RosyBrown));
        items.add(new ExpenseItem("Hobbies", "Expense", R.drawable.ic_soccer_ball_variant, R.color.PaleTurquoise));
        items.add(new ExpenseItem("Pets", "Expense", R.drawable.ic_animal_prints, R.color.Peru));
        items.add(new ExpenseItem("Education", "Expense", R.drawable.ic_atomic, R.color.RoyalBlue));
        items.add(new ExpenseItem("Cinema", "Expense", R.drawable.ic_film, R.color.PowderBlue));
        items.add(new ExpenseItem("Love", "Expense", R.drawable.ic_like, R.color.DeepPink));
        items.add(new ExpenseItem("Kids", "Expense", R.drawable.ic_windmill, R.color.Chocolate));
        items.add(new ExpenseItem("Rent", "Expense", R.drawable.ic_rent, R.color.LightSlateGray));
        items.add(new ExpenseItem("iTunes", "Expense", R.drawable.ic_itunes_logo_of_amusical_note_inside_a_circle, R.color.MediumSeaGreen));
        items.add(new ExpenseItem("Savings", "Expense", R.drawable.ic_piggy_bank, R.color.Marroon));
        items.add(new ExpenseItem("Gifts", "Expense", R.drawable.ic_gift, R.color.OrangeRed));


        items.add(new ExpenseItem("Salary", "Income", R.drawable.ic_incomes, R.color.Indigo));
        items.add(new ExpenseItem("Business ", "Income", R.drawable.ic_briefcase, R.color.Gold));
        items.add(new ExpenseItem("Gifts", "Income", R.drawable.ic_gift, R.color.OrangeRed));
        items.add(new ExpenseItem("Loan", "Income", R.drawable.ic_contract, R.color.Kakhi));
        items.add(new ExpenseItem("Extra Income", "Income", R.drawable.ic_salary, R.color.DarkSlateBlue));

        for (ExpenseItem expenseItem : items) {
            ContentValues values = new ContentValues();
            values.put(ItemsContract.ItemsEntry.COLUMN_ITEM_NAME, expenseItem.getName());
            values.put(ItemsContract.ItemsEntry.COLUMN_ITEM_TYPE, expenseItem.getType());
            values.put(ItemsContract.ItemsEntry.COLUMN_ITEM_LOGO, expenseItem.getImageContentId());
            values.put(ItemsContract.ItemsEntry.COLUMN_ITEM_COLOR, expenseItem.getColorContentId());
            db.insert(ItemsContract.ItemsEntry.TABLE_NAME, null, values);
        }


    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
