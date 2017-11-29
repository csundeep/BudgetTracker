package com.example.sandy.budgettracker.activities;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.contracts.UserContract;
import com.example.sandy.budgettracker.util.Session;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    EditText firstNameET;
    EditText lastNameET;
    EditText emailET;
    EditText mobileET;
    EditText passwordET;
    EditText rePasswordET;
    EditText initialAmountET;
    Button signUpBT;
    TextView loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firstNameET=findViewById(R.id.firstName);
        lastNameET=findViewById(R.id.lastName);
        emailET=findViewById(R.id.email);
        mobileET=findViewById(R.id.mobile);
        passwordET=findViewById(R.id.password);
        rePasswordET=findViewById(R.id.reEnterPassword);
        initialAmountET=findViewById(R.id.initialAmount);
        signUpBT=findViewById(R.id.signup);
        loginLink=findViewById(R.id.login);

        signUpBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");
        if (!validate()) {
            onSignupFailed();
            return;
        }

        signUpBT.setEnabled(false);

        String firstName = firstNameET.getText().toString();
        String lastName = lastNameET.getText().toString();
        String email = emailET.getText().toString();
        String mobile = mobileET.getText().toString();
        String password = passwordET.getText().toString();
        double initialAmount = Double.parseDouble(initialAmountET.getText().toString());
        final int userId = createUser(firstName, lastName, email, password, mobile, initialAmount);
        if (userId != -1) {
            final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Creating Account...");
            progressDialog.show();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            onSignupSuccess(userId);
                            progressDialog.dismiss();
                        }
                    }, 2000);
        }
    }

    private int createUser(String firstName, String lastName, String email, String password, String mobile, double initialAmount) {
        int userId = -1;
        ContentValues values = new ContentValues();
        values.put(UserContract.UserEntry.COLUMN_USER_EMAIL, email);
        values.put(UserContract.UserEntry.COLUMN_USER_PASSWORD, password);
        values.put(UserContract.UserEntry.COLUMN_USER_FIRST_NAME, firstName);
        values.put(UserContract.UserEntry.COLUMN_USER_LAST_NAME, lastName);
        values.put(UserContract.UserEntry.COLUMN_USER_MOBILE, mobile);
        values.put(UserContract.UserEntry.COLUMN_USER_WALLET_AMOUNT, initialAmount);
        Uri uri = getContentResolver().insert(UserContract.UserEntry.CONTENT_URI, values);
        if (uri != null)
            userId = Integer.parseInt(uri.getLastPathSegment());


        // Log.v("*************** ", uri + "   " + userId);
        if (uri == null || userId == -1) {
            onSignupFailed();
        }
        return userId;
    }

    public void onSignupSuccess(int userId) {
        signUpBT.setEnabled(true);
        Session session = new Session(getBaseContext());
        session.setuserId(userId);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        signUpBT.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String firstName = firstNameET.getText().toString();
        String lastName = lastNameET.getText().toString();
        String email = emailET.getText().toString();
        String mobile = mobileET.getText().toString();
        String password = passwordET.getText().toString();
        String reEnterPassword = rePasswordET.getText().toString();

        if (firstName.length() < 3) {
            firstNameET.setError("at least 3 characters");
            valid = false;
        } else {
            firstNameET.setError(null);
        }

        if (lastName.length() < 3) {
            lastNameET.setError("at least 3 characters");
            valid = false;
        } else {
            lastNameET.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("enter a valid email address");
            valid = false;
        } else {
            emailET.setError(null);
        }

        if (mobile.length() != 10) {
            mobileET.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            mobileET.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            passwordET.setError("between 4 and 15 alphanumeric characters");
            valid = false;
        } else {
            passwordET.setError(null);
        }

        if (!(reEnterPassword.equals(password))) {
            rePasswordET.setError("Password Do not match");
            valid = false;
        } else {
            rePasswordET.setError(null);
        }

        return valid;
    }
}