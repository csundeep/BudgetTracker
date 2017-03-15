package com.example.sandy.budgettracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.ExpenseItemAdapter;
import com.example.sandy.budgettracker.adapters.TransactionListAdapter;
import com.example.sandy.budgettracker.adapters.TransactionsAdapter;
import com.example.sandy.budgettracker.data.ExpenseData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class MonthFragment extends Fragment {

    private TransactionsAdapter itemsAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_month, container, false);
        ArrayList<ExpenseData> expenseDatas = getArguments().getParcelableArrayList("expensedatas");


        if (expenseDatas != null && expenseDatas.size() != 0) {

            ArrayList<ArrayList<ExpenseData>> exp = new ArrayList<ArrayList<ExpenseData>>();

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


            for (Map.Entry<Date, ArrayList<ExpenseData>> entry : expenses.entrySet()) {
                ArrayList<ExpenseData> datas = new ArrayList<>();
                for (ExpenseData expenseData : entry.getValue()) {
                    datas.add(expenseData);
                }
                exp.add(datas);
            }

            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            itemsAdapter = new TransactionsAdapter(this.getActivity(), exp);
            recyclerView.setAdapter(itemsAdapter);


        }
        return view;
    }

}
