package com.example.sandy.budgettracker.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.adapters.RecyclerItemClickListener;
import com.example.sandy.budgettracker.adapters.SelectionItemAdapter;
import com.example.sandy.budgettracker.contracts.ItemsContract;
import com.example.sandy.budgettracker.data.BudgetData;
import com.example.sandy.budgettracker.data.ExpenseItem;

import java.util.ArrayList;
import java.util.HashSet;


public class ItemSelectionFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView recyclerView;
    private HashSet<String> selectedItems;
    private BudgetData budgetData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_selection, container, false);

        if (getArguments() != null && getArguments().getSerializable("expenses") != null)
            selectedItems = (HashSet<String>) getArguments().getSerializable("expenses");
        else
            selectedItems = new HashSet<>();
        if (getArguments() != null && getArguments().getSerializable("selectedBudgetData") != null) {
            budgetData = (BudgetData) getArguments().getSerializable("selectedBudgetData");
        }


        Toolbar toolbar =  view.findViewById(R.id.itemsSelectionToolbar);
        ImageButton cancelIB =  view.findViewById(R.id.itemSelectionSubmit);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.select_items_title);
        }

        if (cancelIB != null)
            cancelIB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                    Bundle args = new Bundle();
                    Fragment fragment = new BudgetCreationFragment();
                    args.putSerializable("expenses", selectedItems);
                    args.putSerializable("selectedBudgetData", budgetData);
                    fragment.setArguments(args);
                    transaction.replace(R.id.contentBudget, fragment);
                    transaction.commit();

                }
            });

        recyclerView =  view.findViewById(R.id.items);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this.getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        View viewItem = recyclerView.getLayoutManager().findViewByPosition(position);

                        TextView itemNameTV =  viewItem.findViewById(R.id.itemName);
                        ImageView checkedImageView =  viewItem.findViewById(R.id.itemSelectionImage);
                        if (selectedItems.contains(itemNameTV.getText().toString())) {
                            selectedItems.remove(itemNameTV.getText().toString());
                            checkedImageView.setImageResource(R.drawable.ic_action_not_done);
                        } else {
                            selectedItems.add(itemNameTV.getText().toString());
                            checkedImageView.setImageResource(R.drawable.ic_action_done_green);
                        }
                    }
                })
        );
        getLoaderManager().initLoader(0, null, this);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                ItemsContract.ItemsEntry._Id,
                ItemsContract.ItemsEntry.COLUMN_ITEM_NAME,
                ItemsContract.ItemsEntry.COLUMN_ITEM_TYPE,
                ItemsContract.ItemsEntry.COLUMN_ITEM_LOGO,
                ItemsContract.ItemsEntry.COLUMN_ITEM_COLOR,
                ItemsContract.ItemsEntry.COLUMN_ITEM_CREATED_DATE};

        String selection = ItemsContract.ItemsEntry.COLUMN_ITEM_TYPE + "=?";

        String[] selectionArgs = {
                "Expense"
        };

        return new CursorLoader(this.getActivity(),
                ItemsContract.ItemsEntry.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        ArrayList<ExpenseItem> items = new ArrayList<>();

        try {
            int nameColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_ITEM_NAME);
            int typeColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_ITEM_TYPE);
            int logoColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_ITEM_LOGO);
            int colorColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_ITEM_COLOR);
            while (cursor.moveToNext()) {
                String name = cursor.getString(nameColumnIndex);
                String type = cursor.getString(typeColumnIndex);
                int ImageContentId = cursor.getInt(logoColumnIndex);
                int colorContentId = cursor.getInt(colorColumnIndex);
                ExpenseItem expenseItem = new ExpenseItem(name, type, ImageContentId, colorContentId);
                items.add(expenseItem);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        SelectionItemAdapter itemsAdapter = new SelectionItemAdapter(this.getActivity(), items, selectedItems);
        recyclerView.setAdapter(itemsAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
