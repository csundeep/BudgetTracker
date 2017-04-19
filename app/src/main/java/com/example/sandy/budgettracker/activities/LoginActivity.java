package com.example.sandy.budgettracker.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sandy.budgettracker.R;
import com.example.sandy.budgettracker.util.Session;
import com.example.sandy.budgettracker.contracts.UserContract;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @Bind(R.id.email)
    EditText emailET;
    @Bind(R.id.password)
    EditText passwordET;
    @Bind(R.id.login)
    Button loginBT;
    @Bind(R.id.signup)
    TextView signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginBT.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginBT.setEnabled(false);

        String userName = emailET.getText().toString();
        String password = passwordET.getText().toString();
        final int userid = validateLogin(userName, password);
        if (userid != -1) {
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this,
                    R.style.AppTheme_Dark_Dialog);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Authenticating...");
            progressDialog.show();
            new android.os.Handler().postDelayed(
                    new Runnable() {
                        public void run() {
                            // On complete call either onLoginSuccess or onLoginFailed
                            onLoginSuccess(userid);
                            // onLoginFailed();
                            progressDialog.dismiss();
                        }
                    }, 3000);
        } else {
            onLoginFailed();
        }
    }

    private int validateLogin(String username, String password) {
        int userId = -1;

        String[] projection = {
                UserContract.UserEntry._Id,
                UserContract.UserEntry.COLUMN_USER_EMAIL,
                UserContract.UserEntry.COLUMN_USER_PASSWORD};

        Cursor cursor = getContentResolver().query(UserContract.UserEntry.CONTENT_URI, projection, null, null, null);
        if (cursor != null) {
            try {
                int idColumnIndex = cursor.getColumnIndex(UserContract.UserEntry._ID);
                int nameColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_EMAIL);
                int breedColumnIndex = cursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_PASSWORD);

                while (cursor.moveToNext()) {
                    int id = cursor.getInt(idColumnIndex);
                    String usernameDB = cursor.getString(nameColumnIndex);
                    String passwordDB = cursor.getString(breedColumnIndex);
                    if (username.equals(usernameDB) && password.equals(passwordDB)) {
                        userId = id;
                        break;
                    }
                }
            } finally {
                cursor.close();
            }
        }
        //Log.v(")))))))))))))) ", "" + userId);
        return userId;
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess(int userId) {
        loginBT.setEnabled(true);
        new Session(getBaseContext()).setuserId(userId);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        loginBT.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailET.getText().toString();
        String password = passwordET.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError("enter a valid email address");
            valid = false;
        } else {
            emailET.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 15) {
            passwordET.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordET.setError(null);
        }

        return valid;
    }
}
