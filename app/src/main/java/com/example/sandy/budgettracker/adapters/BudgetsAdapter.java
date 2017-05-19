package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.activities.AddBudgetActivity;
import com.example.sandy.budgettracker.activities.BudgetDetailsActivity;
import com.example.sandy.budgettracker.contracts.BudgetsContract;
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
        TextView totalBudgetSpentTextView;
        TextView totalBudgetExpenseTextView;
        CustomProgress customProgressShowProgress;
        TextView budgetStartDateTextView;
        TextView budgetEndDateTextView;
        ImageButton menuButton;
        RelativeLayout rl;

        CustomViewHolder(View view) {
            super(view);
            budgetNameTextView = (TextView) view.findViewById(R.id.budgetName);
            totalBudgetSpentTextView = (TextView) view.findViewById(R.id.totalBudgetSpent);
            totalBudgetExpenseTextView = (TextView) view.findViewById(R.id.totalBudgetExpense);
            customProgressShowProgress = (CustomProgress) view.findViewById(R.id.customProgressShowProgress);
            budgetStartDateTextView = (TextView) view.findViewById(R.id.budgetStartDate);
            budgetEndDateTextView = (TextView) view.findViewById(R.id.budgetEndDate);
            rl = (RelativeLayout) view.findViewById(R.id.budgetDetails);

            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id = (Integer) budgetNameTextView.getTag();
                    Intent intent = new Intent(activity, BudgetDetailsActivity.class);
                    Uri currentBudgetUri = ContentUris.withAppendedId(BudgetsContract.BudgetsEntry.CONTENT_URI, id);
                    intent.setData(currentBudgetUri);
                    activity.startActivity(intent);
                    activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                }
            });
            menuButton = (ImageButton) view.findViewById(R.id.budgetMenu);

            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(activity, menuButton);
                    popup.getMenuInflater().inflate(R.menu.budget_card_menu, popup.getMenu());

                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            int id;
                            switch (item.getItemId()) {
                                case R.id.deleteBudget:
                                    id = (Integer) budgetNameTextView.getTag();
                                    deleteExpense(id);
                                    break;
                                case R.id.editBudget:
                                    id = (Integer) budgetNameTextView.getTag();
                                    Intent intent = new Intent(activity, AddBudgetActivity.class);
                                    Uri currentExpenseUri = ContentUris.withAppendedId(BudgetsContract.BudgetsEntry.CONTENT_URI, id);
                                    intent.setData(currentExpenseUri);
                                    activity.startActivity(intent);
                                    activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                                    break;
                            }

                            return true;
                        }
                    });
                    popup.show();
                }
            });

        }


    }

    private void deleteExpense(int budgetId) {

        Uri currentExpenseURI = ContentUris.withAppendedId(BudgetsContract.BudgetsEntry.CONTENT_URI, budgetId);

        int rowsDeleted = activity.getContentResolver().delete(currentExpenseURI, null, null);
        if (rowsDeleted == 0) {
            Toast.makeText(activity, activity.getString(R.string.editor_delete_budget_failed), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(activity, activity.getString(R.string.editor_delete_budget_successful), Toast.LENGTH_SHORT).show();
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
            holder = new AddBudgetViewHolder(view);
        } else {
            view = LayoutInflater.from(activity)
                    .inflate(R.layout.budgets_list, null);
            holder = new CustomViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(GeneralViewHolder holder, int position) {
        if (getItemViewType(position) >= 0) {
            CustomViewHolder holder1 = (CustomViewHolder) holder;
            holder1.budgetNameTextView.setText(budgetDatas.get(position).getBudgetName());
            holder1.budgetNameTextView.setTag(budgetDatas.get(position).getId());
            holder1.totalBudgetSpentTextView.setText("$" + budgetDatas.get(position).getTotalExpenses());
            holder1.totalBudgetExpenseTextView.setText(" from $" + budgetDatas.get(position).getBudgetAmount());
            float per = (float) budgetDatas.get(position).getTotalExpenses() / (float) budgetDatas.get(position).getBudgetAmount();
            holder1.customProgressShowProgress.setMaximumPercentage(per);
            // customProgressShowProgress.useRoundedRectangleShape(30.0f);
            if (per * 100 > 80) {
                holder1.totalBudgetSpentTextView.setTextColor(ContextCompat.getColor(activity, R.color.red_500));
                holder1.customProgressShowProgress.setProgressColor(ContextCompat.getColor(activity, R.color.red_500));
                holder1.customProgressShowProgress.setProgressBackgroundColor(ContextCompat.getColor(activity, R.color.red_200));
            } else if (per * 100 > 40) {
                holder1.totalBudgetSpentTextView.setTextColor(ContextCompat.getColor(activity, R.color.orange_500));
                holder1.customProgressShowProgress.setProgressColor(ContextCompat.getColor(activity, R.color.orange_500));
                holder1.customProgressShowProgress.setProgressBackgroundColor(ContextCompat.getColor(activity, R.color.orange_200));
            } else {
                holder1.totalBudgetSpentTextView.setTextColor(ContextCompat.getColor(activity, R.color.colorPrimary));
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
