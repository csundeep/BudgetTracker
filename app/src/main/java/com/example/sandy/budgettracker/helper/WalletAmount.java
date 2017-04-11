package com.example.sandy.budgettracker.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class WalletAmount {

    private SharedPreferences prefs;

    public WalletAmount(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setTransWalletAmount(double walletAmount) {
        prefs.edit().putLong("transWalletAmount", (long) walletAmount).commit();
    }

    public double getTransWalletAmount() {

        return (double) prefs.getLong("transWalletAmount", 0);
    }

    public void removeTransWalletAmount() {
        prefs.edit().remove("userId").commit();
    }
}