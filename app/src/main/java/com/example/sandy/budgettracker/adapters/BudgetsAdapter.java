package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.BudgetData;
import com.example.sandy.budgettracker.util.CustomProgress;

import java.util.ArrayList;

public class BudgetsAdapter extends RecyclerView.Adapter<BudgetsAdapter.GeneralViewHolder> {
    private ArrayList<BudgetData> budgetDatas;
    private Activity activity;

    class GeneralViewHolder extends RecyclerView.ViewHolder {
        GeneralViewHolder(View view) {
            super(view);
        }
    }

    private class CustomViewHolder extends GeneralViewHolder {
        TextView budgetNameTextView;
        TextView totalBudgetExpenseTextView;
        CustomProgress customProgressShowProgress;
        TextView budgetStartDateTextView;
        TextView budgetEndDateTextView;

        TextView addBudgetTextView;

        CustomViewHolder(View view) {
            super(view);
            budgetNameTextView = (TextView) view.findViewById(R.id.budgetName);
            totalBudgetExpenseTextView = (TextView) view.findViewById(R.id.totalBudgetExpense);
            customProgressShowProgress = (CustomProgress) view.findViewById(R.id.customProgressShowProgress);
            budgetStartDateTextView = (TextView) view.findViewById(R.id.budgetStartDate);
            budgetEndDateTextView = (TextView) view.findViewById(R.id.budgetEndDate);

            addBudgetTextView = (TextView) view.findViewById(R.id.add);

        }

    }

    private class AddBudgetViewHolder extends GeneralViewHolder {

        AddBudgetViewHolder(View view) {
            super(view);

        }

    }

    public BudgetsAdapter(Activity activity, ArrayList<BudgetData> budgetDatas) {
        budgetDatas.add(new BudgetData());
        this.budgetDatas = budgetDatas;
        this.activity = activity;
    }

    @Override
    public int getItemViewType(int position) {
        //Some logic to know which type will come next;
        return position == budgetDatas.size() - 1 ? -1 : position;
    }

    @Override
    public GeneralViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        GeneralViewHolder holder;
        View view;
        if (viewType == -1) {
            view = LayoutInflater.from(activity)
                    .inflate(R.layout.add_budget_card, viewGroup, false);

            holder = new AddBudgetViewHolder(view); //Of type GeneralViewHolder
        } else {
            view = LayoutInflater.from(activity)
                    .inflate(R.layout.budgets_list, viewGroup, false);
            holder = new CustomViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(GeneralViewHolder holder, int position) {
        if (getItemViewType(position) >= 0) {
            CustomViewHolder holder1 = (CustomViewHolder) holder;
            holder1.budgetNameTextView.setText(budgetDatas.get(position).getBudgetName());
            holder1.totalBudgetExpenseTextView.setText("$" + budgetDatas.get(position).getTotalExpenses() + " out of $" + budgetDatas.get(position).getBudgetAmount());
            float per = (float) budgetDatas.get(position).getTotalExpenses() / (float) budgetDatas.get(position).getBudgetAmount();
            holder1.customProgressShowProgress.setMaximumPercentage(per);
            // customProgressShowProgress.useRoundedRectangleShape(30.0f);
            if (per * 100 > 80) {
                holder1.customProgressShowProgress.setProgressColor(ContextCompat.getColor(activity, R.color.red_500));
                holder1.customProgressShowProgress.setProgressBackgroundColor(ContextCompat.getColor(activity, R.color.red_200));
            } else if (per * 100 > 40) {
                holder1.customProgressShowProgress.setProgressColor(ContextCompat.getColor(activity, R.color.Gold));
                holder1.customProgressShowProgress.setProgressBackgroundColor(ContextCompat.getColor(activity, R.color.Yellow));
            } else {
                holder1.customProgressShowProgress.setProgressColor(ContextCompat.getColor(activity, R.color.green_500));
                holder1.customProgressShowProgress.setProgressBackgroundColor(ContextCompat.getColor(activity, R.color.green_200));
            }
            holder1.customProgressShowProgress.setShowingPercentage(true);
            try {
                holder1.budgetStartDateTextView.setText(budgetDatas.get(position).getStartDate());
                holder1.budgetEndDateTextView.setText(budgetDatas.get(position).getEndDate());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return budgetDatas.size();
    }
}
