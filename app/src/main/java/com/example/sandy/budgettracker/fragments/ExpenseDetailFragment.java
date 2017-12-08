package com.example.sandy.budgettracker.fragments;

import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.activities.MainActivity;
import com.example.sandy.budgettracker.contracts.BudgetsContract;
import com.example.sandy.budgettracker.contracts.ExpensesContract;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.util.ImageAndColorUtil;
import com.example.sandy.budgettracker.util.Session;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class ExpenseDetailFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String BASE_URL = "https://maps.googleapis.com/maps/api/staticmap?zoom=13&size=600x300&maptype=roadmap&key=AIzaSyCfDC7Ns9LcgoKuEjyOsPDm_CMM0VQACRo&markers=color:red%7Clabel:C%7C";
    private DatePickerDialog datePickerDialog;
    private TextView dateTextView;
    private ImageView locationImageView;
    private View view;
    private ExpenseData selectedExpenseData;
    private int PLCA_PICKER_REQUEST = 1;
    Activity activity = null;
    private double latitude;
    private double longitude;
    private String laLo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_expense_detail, container, false);
        activity = this.getActivity();

        selectedExpenseData = (ExpenseData) getArguments().getSerializable("selectedExpenseData");

        Button deleteButton = view.findViewById(R.id.deleteButton);
        TextView amountTextView = getActivity().findViewById(R.id.amount);
        Toolbar toolbar = getActivity().findViewById(R.id.expenseToolbar);
        LinearLayout linearLayout = getActivity().findViewById(R.id.expenseInfoLayout);
        TabLayout tabLayout = getActivity().findViewById(R.id.expense_tabs);
        EditText notesEditText = view.findViewById(R.id.comments);
        ViewGroup dateViewGroup = view.findViewById(R.id.calender);
        ImageButton storeExpenseButton = getActivity().findViewById(R.id.addExpense);
        ImageButton backButton = getActivity().findViewById(R.id.appBarExpenseImage);
        this.dateTextView = view.findViewById(R.id.date);
        this.locationImageView = view.findViewById(R.id.location);
        storeExpenseButton.setImageResource(R.drawable.ic_action_done);

        if (selectedExpenseData != null && selectedExpenseData.getExpenseName() != null) {
            if (selectedExpenseData.getId() != 0)
                deleteButton.setVisibility(View.VISIBLE);
            else
                deleteButton.setVisibility(View.GONE);

            latitude = selectedExpenseData.getLatitude();
            longitude = selectedExpenseData.getLongitude();
            if (latitude != 0 && longitude != 0) {
                laLo = latitude + "," + longitude;
                loadMap();
            }
            amountTextView.setText(String.format(Locale.US, "%1$,.2f", selectedExpenseData.getExpenseAmount()));

            backButton.setImageResource(ImageAndColorUtil.getWhiteImageContentId(selectedExpenseData.getExpenseName()));

            toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), ImageAndColorUtil.getColorContentId(selectedExpenseData.getExpenseName())));
            ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

            linearLayout.setBackgroundColor(ContextCompat.getColor(getContext(), ImageAndColorUtil.getColorContentId(selectedExpenseData.getExpenseName())));


            tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(), ImageAndColorUtil.getColorContentId(selectedExpenseData.getExpenseName())));
            tabLayout.setTabTextColors(
                    ContextCompat.getColor(getContext(), ImageAndColorUtil.getColorContentId(selectedExpenseData.getExpenseName())),
                    ContextCompat.getColor(getContext(), ImageAndColorUtil.getColorContentId(selectedExpenseData.getExpenseName()))
            );

            tabLayout.setBackgroundColor(ContextCompat.getColor(getContext(), ImageAndColorUtil.getColorContentId(selectedExpenseData.getExpenseName())));

            if (selectedExpenseData.getExpenseDate() != null && this.dateTextView != null)
                this.dateTextView.setText(selectedExpenseData.getExpenseDate());
            else
                this.dateTextView.setText(new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(new Date()));

            if (selectedExpenseData.getNote() != null)
                notesEditText.setText(selectedExpenseData.getNote());
        }


        if (dateViewGroup != null)
            dateViewGroup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar c = Calendar.getInstance();
                    int year = c.get(Calendar.YEAR);
                    int month = c.get(Calendar.MONTH);
                    int day = c.get(Calendar.DAY_OF_MONTH);
                    String dayOfWeek = c.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

                    datePickerDialog = DatePickerDialog.newInstance(ExpenseDetailFragment.this, year, month, day);
                    datePickerDialog.setThemeDark(false);
                    datePickerDialog.showYearPickerFirst(false);
                    datePickerDialog.setAccentColor(ContextCompat.getColor(getContext(), ImageAndColorUtil.getColorContentId(selectedExpenseData.getExpenseName())));
                    datePickerDialog.setTitle(dayOfWeek);
                    datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
                }
            });

        if (locationImageView != null)
            locationImageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();
                    try {
                        Intent intent = intentBuilder.build(activity);
                        startActivityForResult(intent, PLCA_PICKER_REQUEST);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        storeExpenseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText notesEditText = view.findViewById(R.id.comments);
                String notes = notesEditText.getText().toString();

                TextView calenderTextView = view.findViewById(R.id.date);
                String date = calenderTextView.getText().toString();

                TextView amountTextView = getActivity().findViewById(R.id.amount);
                double amount = Double.valueOf(amountTextView.getText().toString());

                selectedExpenseData.setExpenseAmount(amount);
                selectedExpenseData.setNote(notes);
                selectedExpenseData.setExpenseDate(date);

                selectedExpenseData.setLatitude(latitude);
                selectedExpenseData.setLongitude(longitude);
                if (selectedExpenseData.getId() != 0)
                    updateExpense(v, selectedExpenseData);
                else
                    storeExpense(v, selectedExpenseData);
            }
        });


        EditText amountET = getActivity().findViewById(R.id.amount);
        amountET.setSelection(amountET.getText().length());
        amountET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et = (EditText) v;
                et.setSelection(et.getText().length());
            }
        });


        if (backButton != null)
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    EditText notesEditText = view.findViewById(R.id.comments);
                    String notes = notesEditText.getText().toString();
                    TextView calenderTextView = view.findViewById(R.id.date);
                    String date = calenderTextView.getText().toString();

                    selectedExpenseData.setNote(notes);
                    selectedExpenseData.setExpenseDate(date);

                    args.putSerializable("selectedExpenseData", selectedExpenseData);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right);
                    ExpenseSelectionFragment expenseSelectionFragment = new ExpenseSelectionFragment();
                    expenseSelectionFragment.setArguments(args);
                    transaction.replace(R.id.contentExpense, expenseSelectionFragment);
                    transaction.commit();
                }
            });


        if (deleteButton != null)
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteExpense(v, selectedExpenseData);
                }
            });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLCA_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getContext(), data);
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                laLo = latitude + "," + longitude;

                loadMap();

            }
        }
    }

    private void loadMap() {
        try {
            AsyncTask<Void, Void, Bitmap> setImageFromUrl = new AsyncTask<Void, Void, Bitmap>() {
                @Override
                protected Bitmap doInBackground(Void... params) {
                    Bitmap bmp = null;
                    try {
                        String path = BASE_URL + laLo;
                        Log.v("Static Maps URl ", path);
                        InputStream in = makeHttpRequest(createUrl(path));
                        bmp = BitmapFactory.decodeStream(in);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    return bmp;
                }

                protected void onPostExecute(Bitmap bmp) {
                    if (bmp != null) {

                        locationImageView.setImageBitmap(bmp);
                        locationImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        locationImageView.setPadding(0, 0, 0, 0);
                        laLo = "";
                    }

                }
            };
            setImageFromUrl.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    private static InputStream makeHttpRequest(URL url) throws IOException {
        InputStream inputStream = null;

        // If the URL is null, then return early.
        if (url == null) {
            return null;
        }


        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    public void storeExpense(@SuppressWarnings("unused") View view, ExpenseData expenseData) {


        ContentValues values = new ContentValues();
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME, expenseData.getExpenseName());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE, expenseData.getExpenseType());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT, expenseData.getExpenseAmount() < 0 ? expenseData.getExpenseAmount() * -1 : expenseData.getExpenseAmount());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES, expenseData.getNote());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_LATITUDE, expenseData.getLatitude());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_LONGITUDE, expenseData.getLongitude());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE, expenseData.getExpenseDate());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_USER_ID, new Session(getActivity().getBaseContext()).getuserId());
        Uri uri = getActivity().getContentResolver().insert(ExpensesContract.ExpenseEntry.CONTENT_URI, values);

        if (uri == null) {
            Toast.makeText(this.getActivity(), R.string.editor_save_expense_failed,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getActivity(), R.string.editor_save_expense_successful,
                    Toast.LENGTH_SHORT).show();
            checkBudgets(expenseData.getExpenseName());
        }
        getActivity().finish();

        Intent intent = new Intent(this.getActivity(), MainActivity.class); //remove this after fix
        startActivity(intent);//remove this after fix

    }

    public void updateExpense(@SuppressWarnings("unused") View view, ExpenseData expenseData) {
        Uri currentExpenseURI = ContentUris.withAppendedId(ExpensesContract.ExpenseEntry.CONTENT_URI, expenseData.getId());


        ContentValues values = new ContentValues();
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME, expenseData.getExpenseName());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE, expenseData.getExpenseType());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT, expenseData.getExpenseAmount() < 0 ? expenseData.getExpenseAmount() * -1 : expenseData.getExpenseAmount());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES, expenseData.getNote());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_LATITUDE, expenseData.getLatitude());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_LONGITUDE, expenseData.getLongitude());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE, expenseData.getExpenseDate());
        int rowsAffected = getActivity().getContentResolver().update(currentExpenseURI, values, null, null);

        if (rowsAffected == 0) {
            Toast.makeText(this.getActivity(), getString(R.string.editor_update_expense_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getContext(), getString(R.string.editor_update_expense_successful),
                    Toast.LENGTH_SHORT).show();
            checkBudgets(expenseData.getExpenseName());
        }
        getActivity().finish();

        Intent intent = new Intent(this.getActivity(), MainActivity.class); //remove this after fix
        startActivity(intent);//remove this after fix
    }

    public void deleteExpense(@SuppressWarnings("unused") View view, ExpenseData expenseData) {

        Uri currentExpenseURI = ContentUris.withAppendedId(ExpensesContract.ExpenseEntry.CONTENT_URI, expenseData.getId());


        int rowsDeleted = getActivity().getContentResolver().delete(currentExpenseURI, null, null);
        if (rowsDeleted == 0) {
            Toast.makeText(this.getActivity(), getString(R.string.editor_delete_expense_failed), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getActivity(), getString(R.string.editor_delete_expense_successful), Toast.LENGTH_SHORT).show();
        }

        getActivity().finish();

        Intent intent = new Intent(this.getActivity(), MainActivity.class); //remove this after fix
        startActivity(intent);//remove this after fix
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date d = cal.getTime();
        dateTextView.setText(new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(d));
    }

    private void checkBudgets(String presentExpense) {
        String[] projection = {
                BudgetsContract.BudgetsEntry._Id,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_AMOUNT,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_EXPENSES,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_START_DATE,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_END_DATE,
                BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NOTIFICATIONS};

        String selection = BudgetsContract.BudgetsEntry.COLUMN_BUDGET_USER_ID + "=?";

        String[] selectionArgs = {
                String.valueOf(new Session(getActivity().getBaseContext()).getuserId())
        };
        Cursor cursor = getActivity().getContentResolver().query(BudgetsContract.BudgetsEntry.CONTENT_URI, projection, selection, selectionArgs, null);
        //Log.v("@@@@@@@@@@@@ ",cursor.getCount()+"");
        if (cursor != null) {
            try {
                int nameColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NAME);
                int amountColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_AMOUNT);
                int expensesColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_EXPENSES);
                int startDateColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_START_DATE);
                int endDateColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_END_DATE);
                int notificationColumnIndex = cursor.getColumnIndex(BudgetsContract.BudgetsEntry.COLUMN_BUDGET_NOTIFICATIONS);
                while (cursor.moveToNext()) {
                    double amount = cursor.getDouble(amountColumnIndex);
                    String name = cursor.getString(nameColumnIndex);
                    String expenses = cursor.getString(expensesColumnIndex);
                    String startDate = cursor.getString(startDateColumnIndex);
                    String endDate = cursor.getString(endDateColumnIndex);
                    int isNotify = cursor.getInt(notificationColumnIndex);
                    double totalExpense = calculateTotalExpenses(expenses, startDate, endDate);
                    Log.v("!!!!!!!!!!!!!!!!!!", totalExpense + " " + amount);
                    if (!expenses.contains(presentExpense))
                        continue;
                    if (totalExpense >= amount) {
                        if (isNotify == 1)
                            Toast.makeText(this.getActivity(), "Budget with name " + name + " exceeding", Toast.LENGTH_SHORT).show();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                cursor.close();
            }
        }

    }


    private double calculateTotalExpenses(String expenses, String startDate, String endDate) {
        double totalExpenses = 0;
        String[] projection = {
                ExpensesContract.ExpenseEntry._Id,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT,
                ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE};

        String selection = ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_USER_ID + "=? ";


        String[] selectionArgs = {
                String.valueOf(new Session(getActivity().getBaseContext()).getuserId())
        };

        Cursor expensesCursor = getActivity().getContentResolver().query(ExpensesContract.ExpenseEntry.CONTENT_URI, projection, selection, selectionArgs, null);
        if (expensesCursor != null) {
            try {
                int expenseNameIndex = expensesCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME);
                int expenseTypeIndex = expensesCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE);
                int expenseAmountIndex = expensesCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT);
                int dateIndex = expensesCursor.getColumnIndex(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE);
                while (expensesCursor.moveToNext()) {
                    String expenseName = expensesCursor.getString(expenseNameIndex);
                    String expenseType = expensesCursor.getString(expenseTypeIndex);
                    double expenseAmount = expensesCursor.getDouble(expenseAmountIndex);
                    String date = expensesCursor.getString(dateIndex);
                    Date expenseDate = null, start = null, end = null;
                    try {
                        expenseDate = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(date);
                        start = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(startDate);
                        end = new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(endDate);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (expenseDate == null || start == null || end == null)
                        continue;
                    if (!(expenseDate.compareTo(start) >= 0 && expenseDate.compareTo(end) <= 0))
                        continue;

                    if (!expenses.equals("All Expenses")) {
                        if (!expenses.contains(expenseName))
                            continue;
                    }
                    if (expenseType.equals("Expense"))
                        totalExpenses += expenseAmount;
                    else
                        totalExpenses -= expenseAmount;

                }
            } finally {
                expensesCursor.close();
            }
        }

        if (totalExpenses < 0)
            return 0;
        else
            return totalExpenses;
    }

}
