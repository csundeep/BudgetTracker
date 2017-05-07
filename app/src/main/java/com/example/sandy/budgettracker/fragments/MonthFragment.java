package com.example.sandy.budgettracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.TransactionsAdapter;
import com.example.sandy.budgettracker.data.ExpenseData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;


public class MonthFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TransactionsAdapter itemsAdapter;
        final RecyclerView recyclerView;

        View view = inflater.inflate(R.layout.fragment_month, container, false);


        ArrayList<ExpenseData> expenseDatas = getArguments().getParcelableArrayList("expensedatas");

        double walletBalance = getArguments().getDouble("walletBalance");
        double totalExpenseAmount = getArguments().getDouble("totalExpenseAmount");
//        if (expenseDatas != null)
//            Log.v("*&^&*^&*^(*^(*^", "Month Fragment1 " + expenseDatas.size());
//        else
//            Log.v("*&^&*^&*^(*^(*^", "Month Fragment1 " + expenseDatas+" "+walletBalance);
        if (expenseDatas != null && expenseDatas.size() != 0) {

            ArrayList<ArrayList<ExpenseData>> exp = new ArrayList<>();

            Map<Date, ArrayList<ExpenseData>> expenses = new HashMap<>();

            try {
                for (ExpenseData expenseData : expenseDatas) {

                    if (expenses.containsKey(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(expenseData.getExpenseDate()))) {
                        expenses.get(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(expenseData.getExpenseDate())).add(expenseData);
                    } else {
                        ArrayList<ExpenseData> datas = new ArrayList<>();
                        datas.add(expenseData);
                        expenses.put(new SimpleDateFormat("MMM dd, yyyy", Locale.US).parse(expenseData.getExpenseDate()), datas);
                    }

                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            SortedSet<Date> keys = new TreeSet<>(expenses.keySet());
            int x = expenses.size();
            ArrayList<ExpenseData>[] exxx = new ArrayList[expenses.size() + 1];
            exxx[0] = new ArrayList<>();
            for (Date key : keys) {
                ArrayList<ExpenseData> value = expenses.get(key);
                exxx[x] = value;
                x--;

            }
            for (int i = 0; i < exxx.length; i++) {
                exp.add(exxx[i]);
            }
//            Log.v("*&^&*^&*^(*^(*^", "Month Fragment2");
            recyclerView = (RecyclerView) view.findViewById(R.id.months);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            itemsAdapter = new TransactionsAdapter(this.getActivity(), exp, walletBalance, totalExpenseAmount);
            recyclerView.setAdapter(itemsAdapter);


        }
        return view;
    }

}
