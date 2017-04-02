package com.example.sandy.budgettracker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.activities.AddBudgetActivity;
import com.example.sandy.budgettracker.adapters.BudgetsAdapter;
import com.example.sandy.budgettracker.adapters.RecyclerItemClickListener;
import com.example.sandy.budgettracker.data.BudgetData;

import java.util.ArrayList;
import java.util.Date;


public class BudgetFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view;
        final RecyclerView recyclerView;
        BudgetsAdapter itemsAdapter;

        final ArrayList<BudgetData> budgetDatas = new ArrayList<>();
        view = inflater.inflate(R.layout.fragment_budget, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.budgets);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        budgetDatas.add(new BudgetData("sandy 0", 1000, new Date(), new Date()));
        budgetDatas.add(new BudgetData("sandy 1", 1000, new Date(), new Date()));

        //TODO : Query the database for budgets list
        budgetDatas.add(new BudgetData());

        itemsAdapter = new BudgetsAdapter(this.getActivity(), budgetDatas);
        recyclerView.setAdapter(itemsAdapter);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

//                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);
                        if (position == budgetDatas.size() - 1) {
                            openExpenseActivity();
                        }

                    }
                })
        );

        return view;
    }

    private void openExpenseActivity() {
        Intent intent = new Intent(this.getActivity(), AddBudgetActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

}
