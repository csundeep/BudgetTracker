package com.example.sandy.budgettracker.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.example.sandy.budgettracker.data.ExpenseData;

import java.util.ArrayList;


public class MonthFragment extends Fragment {

    private ListView listView;
    private TransactionListAdapter itemsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_month, container, false);
        ArrayList<ExpenseData> expenseDatas = getArguments().getParcelableArrayList("expensedatas");
        listView = (ListView) view.findViewById(R.id.transactions);
        if (expenseDatas != null && expenseDatas.size() != 0) {
            itemsAdapter = new TransactionListAdapter(this.getActivity(), expenseDatas);
            listView.setAdapter(itemsAdapter);
        }


        String value = "";
//        if (expenseDatas != null && expenseDatas.size() != 0) {
//            for (ExpenseData data : expenseDatas) {
////                Log.v("@@@@@@@@@@@ ", data.toString());
//                value += data.toString() + "\n \n";
//            }
//        }
        return view;
    }

}
