package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.BudgetData;
import com.example.sandy.budgettracker.util.CustomProgress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BudgetsAdapter extends RecyclerView.Adapter<BudgetsAdapter.GeneralViewHolder> {
    private ArrayList<BudgetData> budgetDatas;
    private Activity activity;

    class GeneralViewHolder extends RecyclerView.ViewHolder {
        GeneralViewHolder(View view) {
            super(view);
        }
    }

    class CustomViewHolder extends GeneralViewHolder {
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

    class AddBudgetViewHolder extends GeneralViewHolder {

        AddBudgetViewHolder(View view) {
            super(view);

        }

    }

    public BudgetsAdapter(Activity activity, ArrayList<BudgetData> budgetDatas) {
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
        if (getItemViewType(position) == -1) {
            AddBudgetViewHolder holder1 = (AddBudgetViewHolder) holder;
        } else {
            CustomViewHolder holder1 = (CustomViewHolder) holder;
            holder1.budgetNameTextView.setText(budgetDatas.get(position).getBudgetName());
            holder1.totalBudgetExpenseTextView.setText("$500 out of $" + budgetDatas.get(position).getBudgetAmount());
            holder1.customProgressShowProgress.setMaximumPercentage(0.5f);
            // customProgressShowProgress.useRoundedRectangleShape(30.0f);
            holder1.customProgressShowProgress.setProgressColor(activity.getResources().getColor(R.color.green_500));
            holder1.customProgressShowProgress.setProgressBackgroundColor(activity.getResources().getColor(R.color.green_200));
            holder1.customProgressShowProgress.setShowingPercentage(true);
            try {
                holder1.budgetStartDateTextView.setText((new SimpleDateFormat("MMM, yyyy", Locale.US).parse(budgetDatas.get(position).getStartDate().toString())).toString());
                holder1.budgetEndDateTextView.setText((new SimpleDateFormat("MMM, yyyy", Locale.US).parse(budgetDatas.get(position).getEndDate().toString())).toString());
            } catch (Exception e) {

            }
        }
    }

    @Override
    public int getItemCount() {
        return budgetDatas.size();
    }
}
