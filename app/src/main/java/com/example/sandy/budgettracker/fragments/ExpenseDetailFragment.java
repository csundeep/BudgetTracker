package com.example.sandy.budgettracker.fragments;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.activities.MainActivity;
import com.example.sandy.budgettracker.data.ExpenseData;
import com.example.sandy.budgettracker.helper.ExpensesContract;
import com.example.sandy.budgettracker.helper.Session;
import com.example.sandy.budgettracker.util.ImageAndColorUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExpenseDetailFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialog datePickerDialog;
    private TextView dateTextView;
    private View view;
    private ExpenseData selectedExpenseData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_expense_detail, container, false);

        selectedExpenseData = (ExpenseData) getArguments().getSerializable("selectedExpenseData");

        Button deleteButton = (Button) view.findViewById(R.id.deleteButton);
        TextView amountTextView = (TextView) getActivity().findViewById(R.id.amount);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.expenseToolbar);
        LinearLayout linearLayout = (LinearLayout) getActivity().findViewById(R.id.expenseInfoLayout);
        TabLayout tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs1);
        EditText notesEditText = (EditText) view.findViewById(R.id.comments);
        ViewGroup dateViewGroup = (ViewGroup) view.findViewById(R.id.calender);
        ImageButton storeExpenceButton = (ImageButton) getActivity().findViewById(R.id.addExpense);
        ImageButton backButton = (ImageButton) getActivity().findViewById(R.id.appBarExpenseImage);

        if (selectedExpenseData != null && selectedExpenseData.getExpenseName() != null) {
            if (selectedExpenseData.getId() != 0)
                deleteButton.setVisibility(View.VISIBLE);
            else
                deleteButton.setVisibility(View.GONE);
            amountTextView.setText(Double.valueOf(selectedExpenseData.getExpenseAmount()).toString());

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

            this.dateTextView = (TextView) view.findViewById(R.id.date);

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


        if (storeExpenceButton != null)
            storeExpenceButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    EditText notesEditText = (EditText) view.findViewById(R.id.comments);
                    String notes = notesEditText.getText().toString();

                    TextView calenderTextView = (TextView) view.findViewById(R.id.date);
                    String date = calenderTextView.getText().toString();

                    TextView amountTextView = (TextView) getActivity().findViewById(R.id.amount);
                    double amount = Double.valueOf(amountTextView.getText().toString());

                    selectedExpenseData.setExpenseAmount(amount);
                    selectedExpenseData.setNote(notes);
                    selectedExpenseData.setExpenseDate(date);
                    if (selectedExpenseData.getId() != 0)
                        updateExpense(v, selectedExpenseData);
                    else
                        storeExpense(v, selectedExpenseData);
                }
            });


        if (backButton != null)
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle args = new Bundle();
                    EditText notesEditText = (EditText) view.findViewById(R.id.comments);
                    String notes = notesEditText.getText().toString();
                    TextView calenderTextView = (TextView) view.findViewById(R.id.date);
                    String date = calenderTextView.getText().toString();

                    selectedExpenseData.setNote(notes);
                    selectedExpenseData.setExpenseDate(date);

                    args.putSerializable("selectedExpenseData", selectedExpenseData);
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    ExpenseSelectionFragment expenseSelectionFragment = new ExpenseSelectionFragment();
                    expenseSelectionFragment.setArguments(args);
                    transaction.replace(R.id.contentExpense, expenseSelectionFragment);
                    transaction.commit();
                    getActivity().overridePendingTransition(R.anim.push_left_out, R.anim.push_left_in);
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

    public void storeExpense(@SuppressWarnings("unused") View view, ExpenseData expenseData) {


        ContentValues values = new ContentValues();
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME, expenseData.getExpenseName());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE, expenseData.getExpenseType());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT, expenseData.getExpenseAmount());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES, expenseData.getNote());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE, expenseData.getExpenseDate());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_USER_ID, new Session(getActivity().getBaseContext()).getuserId());

        Uri uri = getActivity().getContentResolver().insert(ExpensesContract.ExpenseEntry.CONTENT_URI, values);

        if (uri == null) {
            Toast.makeText(this.getActivity(), R.string.editor_save_expense_failed,
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getActivity(), R.string.editor_save_expense_successful,
                    Toast.LENGTH_SHORT).show();
        }
        getActivity().finish();
        Intent homepage = new Intent(this.getActivity(), MainActivity.class);
        startActivity(homepage);
    }

    public void updateExpense(@SuppressWarnings("unused") View view, ExpenseData expenseData) {
        Uri currentExpenseURI = ContentUris.withAppendedId(ExpensesContract.ExpenseEntry.CONTENT_URI, expenseData.getId());


        ContentValues values = new ContentValues();
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME, expenseData.getExpenseName());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_TYPE, expenseData.getExpenseType());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT, expenseData.getExpenseAmount());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES, expenseData.getNote());
        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE, expenseData.getExpenseDate());
        int rowsAffected = getActivity().getContentResolver().update(currentExpenseURI, values, null, null);

        if (rowsAffected == 0) {
            Toast.makeText(this.getActivity(), getString(R.string.editor_update_expense_failed),
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this.getContext(), getString(R.string.editor_update_expense_successful),
                    Toast.LENGTH_SHORT).show();
        }
        getActivity().finish();
        Intent homepage = new Intent(this.getActivity(), MainActivity.class);
        startActivity(homepage);
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
        Intent homepage = new Intent(this.getActivity(), MainActivity.class);
        startActivity(homepage);
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

}
