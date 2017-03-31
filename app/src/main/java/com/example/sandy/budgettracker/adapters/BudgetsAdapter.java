package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.BudgetData;
import com.example.sandy.budgettracker.helper.CustomProgress;

import java.util.ArrayList;

public class BudgetsAdapter extends RecyclerView.Adapter<BudgetsAdapter.CustomViewHolder> {
    private ArrayList<BudgetData> budgetDatas;
    private Activity activity;

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView budgetNameTextView;

        CustomProgress customProgressShowProgress;

        CustomViewHolder(View view) {
            super(view);
            budgetNameTextView = (TextView) view.findViewById(R.id.budgetName);

            customProgressShowProgress = (CustomProgress) view.findViewById(R.id.customProgressShowProgress);
            customProgressShowProgress.setMaximumPercentage(0.5f);
           // customProgressShowProgress.useRoundedRectangleShape(30.0f);
            customProgressShowProgress.setProgressColor(view.getResources().getColor(R.color.green_500));
            customProgressShowProgress.setProgressBackgroundColor(view.getResources().getColor(R.color.green_200));
            customProgressShowProgress.setShowingPercentage(true);

        }

    }

    public BudgetsAdapter(Activity activity, ArrayList<BudgetData> budgetDatas) {
        this.budgetDatas = budgetDatas;
        this.activity = activity;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.budgets_list, null);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {

        holder.budgetNameTextView.setText("sandy " + position);
    }

    @Override
    public int getItemCount() {
        return budgetDatas.size();
    }
}
