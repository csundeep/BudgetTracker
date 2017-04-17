package com.example.sandy.budgettracker.fragments;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.activities.LoginActivity;
import com.example.sandy.budgettracker.helper.Session;
import com.example.sandy.budgettracker.helper.UserContract;


public class ProfileFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private View view;
    private Uri currentUserUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.walletToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        if (((AppCompatActivity) getActivity()).getSupportActionBar() != null)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.wallet);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabWallet);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Session(getActivity().getBaseContext()).logout();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
        currentUserUri = ContentUris.withAppendedId(UserContract.UserEntry.CONTENT_URI, new Session(getActivity().getBaseContext()).getuserId());
        getLoaderManager().initLoader(0, null, this);
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                UserContract.UserEntry._ID,
                UserContract.UserEntry.COLUMN_USER_EMAIL,
                UserContract.UserEntry.COLUMN_USER_FIRST_NAME,
                UserContract.UserEntry.COLUMN_USER_LAST_NAME,
                UserContract.UserEntry.COLUMN_USER_MOBILE,
                UserContract.UserEntry.COLUMN_USER_WALLET_AMOUNT
        };


        return new CursorLoader(this.getActivity(), currentUserUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }
        if (cursor.moveToFirst()) {
            int emailColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_EMAIL);
            int firstNameColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_FIRST_NAME);
            int lastNameColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_LAST_NAME);
            int mobileColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_MOBILE);
            int walletAmountIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_WALLET_AMOUNT);

            String email = cursor.getString(emailColumnIndex);
            String firstName = cursor.getString(firstNameColumnIndex);
            String lastName = cursor.getString(lastNameColumnIndex);
            String mobile = cursor.getString(mobileColumnIndex);
            String walletAmount = cursor.getString(walletAmountIndex);

            TextView profileFirstName = (TextView) view.findViewById(R.id.profileFirstName);
            TextView profileLastName = (TextView) view.findViewById(R.id.profilelastName);
            TextView profileEmail = (TextView) view.findViewById(R.id.profileEmail);
            TextView profilePhone = (TextView) view.findViewById(R.id.profilePhone);
            TextView profileWallet = (TextView) view.findViewById(R.id.profileInitalWalletAmount);


            profileFirstName.setText(firstName);
            profileLastName.setText(lastName);
            profileEmail.setText(email);
            profilePhone.setText(mobile);
            profileWallet.setText(walletAmount);



        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
