package com.example.sandy.budgettracker.adapters;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.data.ExpenseItem;
import com.example.sandy.budgettracker.util.ImageAndColorUtil;

import java.util.ArrayList;
import java.util.HashSet;

public class SelectionItemAdapter extends RecyclerView.Adapter<SelectionItemAdapter.CustomViewHolderList> {
    private ArrayList<ExpenseItem> expenseItems;
    private Activity context;
    private HashSet<String> selectExpenseItems;

    public SelectionItemAdapter(Activity context, ArrayList<ExpenseItem> expenseItems, HashSet<String> selectExpenseItems) {
        this.expenseItems = expenseItems;
        this.context = context;
        this.selectExpenseItems = selectExpenseItems;
    }

    class CustomViewHolderList extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemName;
        private ImageView itemSelectedImage;

        CustomViewHolderList(View view) {
            super(view);

            this.itemImage = (ImageView) view.findViewById(R.id.itemImage);
            this.itemName = (TextView) view.findViewById(R.id.itemName);
            this.itemSelectedImage = (ImageView) view.findViewById(R.id.itemSelectionImage);

        }
    }

    @Override
    public SelectionItemAdapter.CustomViewHolderList onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.items_list, null);
        return new SelectionItemAdapter.CustomViewHolderList(view);
    }

    @Override
    public void onBindViewHolder(SelectionItemAdapter.CustomViewHolderList holder, int position) {


        ExpenseItem expenseItem = expenseItems.get(position);

        holder.itemName.setText(expenseItem.getName());

        holder.itemImage.setImageResource(ImageAndColorUtil.getWhiteImageContentId(expenseItems.get(position).getName()));
        Drawable background = holder.itemImage.getBackground();

        if (background instanceof ShapeDrawable) {
            ((ShapeDrawable) background).getPaint().setColor(ContextCompat.getColor(context, ImageAndColorUtil.getColorContentId(expenseItems.get(position).getName())));
        } else if (background instanceof GradientDrawable) {
            ((GradientDrawable) background).setColor(ContextCompat.getColor(context, ImageAndColorUtil.getColorContentId(expenseItems.get(position).getName())));
        } else if (background instanceof ColorDrawable) {
            ((ColorDrawable) background).setColor(ContextCompat.getColor(context, ImageAndColorUtil.getColorContentId(expenseItems.get(position).getName())));
        }

        if (selectExpenseItems != null) {
            if (selectExpenseItems.contains(expenseItem.getName()))
                holder.itemSelectedImage.setImageResource(R.drawable.ic_action_done_green);
            else
                holder.itemSelectedImage.setImageResource(R.drawable.ic_action_not_done);
        }


    }

    @Override
    public int getItemCount() {
        return expenseItems.size();
    }


}
