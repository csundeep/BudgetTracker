package com.example.sandy.budgettracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.ExpenseItemAdapter;
import com.example.sandy.budgettracker.adapters.TransactionListAdapter;
import com.example.sandy.budgettracker.adapters.TransactionsAdapter;
import com.example.sandy.budgettracker.data.ExpenseData;

import java.util.ArrayList;


public class MonthFragment extends Fragment {

    private TransactionsAdapter itemsAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        ArrayList<ExpenseData> expenseDatas = getArguments().getParcelableArrayList("expensedatas");
        if (expenseDatas != null && expenseDatas.size() != 0) {
//            recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            itemsAdapter = new TransactionsAdapter(this.getActivity(), expenseDatas);
//            recyclerView.setAdapter(itemsAdapter);


            ListView listView = (ListView) view.findViewById(R.id.trans);
            TransactionListAdapter listAdapter = new TransactionListAdapter(this.getActivity(), expenseDatas);
            listView.setAdapter(listAdapter);


        }
        return view;
    }

}
