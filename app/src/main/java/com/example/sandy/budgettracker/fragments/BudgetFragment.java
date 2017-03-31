package com.example.sandy.budgettracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.BudgetsAdapter;
import com.example.sandy.budgettracker.adapters.TransactionsAdapter;
import com.example.sandy.budgettracker.data.BudgetData;

import java.util.ArrayList;


public class BudgetFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private BudgetsAdapter itemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<BudgetData> budgetDatas = new ArrayList<BudgetData>();
        view = inflater.inflate(R.layout.fragment_budget, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.budgets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        budgetDatas.add(new BudgetData());
        budgetDatas.add(new BudgetData());


        itemsAdapter = new BudgetsAdapter(this.getActivity(), budgetDatas);
        recyclerView.setAdapter(itemsAdapter);

        return view;
    }

}
