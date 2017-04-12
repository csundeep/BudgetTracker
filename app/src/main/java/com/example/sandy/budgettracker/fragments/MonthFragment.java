package com.example.sandy.budgettracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.RecyclerItemClickListener;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TransactionsAdapter itemsAdapter;
        final RecyclerView recyclerView;

        View view = inflater.inflate(R.layout.fragment_month, container, false);
        ArrayList<ExpenseData> expenseDatas = getArguments().getParcelableArrayList("expensedatas");

        double walletBalance = getArguments().getDouble("walletBalance");
        double totalExpenseAmount = getArguments().getDouble("totalExpenseAmount");


        if (expenseDatas != null && expenseDatas.size() != 0) {

            ArrayList<ArrayList<ExpenseData>> exp = new ArrayList<>();
            exp.add(new ArrayList<ExpenseData>());

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
            recyclerView = (RecyclerView) view.findViewById(R.id.months);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            itemsAdapter = new TransactionsAdapter(this.getActivity(), exp, walletBalance, totalExpenseAmount);
            recyclerView.setAdapter(itemsAdapter);


            recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this.getContext(), new RecyclerItemClickListener.OnItemClickListener()

                    {
                        @Override
                        public void onItemClick(View view, int position) {
                            View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
                            RecyclerView r1 = (RecyclerView) viewItem.findViewById(R.id.recyclerviewList);
                            Log.v("@@@@@@@@@@@@ ", r1 + " ");
//                            r1.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener()
//
//                                    {
//                                        @Override
//                                        public void onItemClick(View view, int position) {
//                                            View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
//                                            TextView expenseNameTextView = (TextView) viewItem.findViewById(R.id.trans_expense_name);
//                                            Log.v("@@@@@@@@@@@@ ", expenseNameTextView.getText().toString());
//                                        }
//                                    })
//                            );


                        }
                    })
            );


        }
        return view;
    }

}
