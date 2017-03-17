package com.example.sandy.budgettracker.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ExpenseDetailFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private DatePickerDialog datePickerDialog;
    private TextView dateTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_expense_detail, container, false);

        this.dateTextView = (TextView) view.findViewById(R.id.date);
        this.dateTextView.setText(new SimpleDateFormat("MMM dd, yyyy", Locale.US).format(new Date()));

        ViewGroup dateViewGroup = (ViewGroup) view.findViewById(R.id.calender);
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
                    datePickerDialog.setAccentColor(Color.parseColor("#009688"));
                    datePickerDialog.setTitle(dayOfWeek);
                    datePickerDialog.show(getActivity().getFragmentManager(), "DatePickerDialog");
                }
            });
        ImageButton b = (ImageButton) view.findViewById(R.id.submitExpense);
        if (b != null)
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView amountTextView = (TextView) getActivity().findViewById(R.id.amountFinal);
                    double amount = Double.parseDouble(amountTextView.getText().toString());
                    EditText notesEditText = (EditText) getActivity().findViewById(R.id.comments);
                    String notes = notesEditText.getText().toString();
                    TextView calenderTextView = (TextView) getActivity().findViewById(R.id.date);
                    String date = calenderTextView.getText().toString();
                    storeExpense(v, "", amount, notes, date);
                }
            });
        return view;
    }

    public void storeExpense(@SuppressWarnings("unused") View view, String expenseItem, double amount, String notes, String date) {

        Log.v("@@@@@@@@@@@@@@", expenseItem + " " + amount + " " + notes + " " + date);

//        ContentValues values = new ContentValues();
//        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NAME, expenseItem);
//        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_AMOUNT, amount);
//        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_NOTES, notes);
//        values.put(ExpensesContract.ExpenseEntry.COLUMN_EXPENSE_CREATED_DATE, date);
//
//        Uri uri = getActivity().getContentResolver().insert(ExpensesContract.ExpenseEntry.CONTENT_URI, values);
//
//        if (uri == null) {
//            Toast.makeText(this.getActivity(), "Unable to add expense",
//                    Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(this.getActivity(), "Expense added successful",
//                    Toast.LENGTH_SHORT).show();
//        }
//        getActivity().finish();
//        Intent homepage = new Intent(this.getActivity(), MainActivity.class);
//        startActivity(homepage);
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
