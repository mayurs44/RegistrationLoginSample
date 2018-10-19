package com.vatsaltechnosoft.mani.amritha.registrationloginsample;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = MainActivity.this;

    NestedScrollView nestedScrollView;

    AppCompatButton login;

    TextInputEditText userName;

    TextInputEditText password;

    TextInputLayout userLayout;

    TextInputLayout passwordLayout;

    AppCompatTextView registerText;

    InputValidation inputValidation;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initViews();
        initListeners();
        initObjects();

    }


    /**
     * This method is to initialize views
     */

    private void initViews() {

        nestedScrollView = findViewById(R.id.nestedScrollView);

        userName = findViewById(R.id.login_username_editText);

        password = findViewById(R.id.login_password_editText);

        login = findViewById(R.id.login_submit_button);

        userLayout = findViewById(R.id.textInputLayoutUser);

        passwordLayout = findViewById(R.id.textInputLayoutPassword);

        registerText = findViewById(R.id.signup_button);
    }

    /**
     * This method is to initialize listeners
     */

    private void initListeners() {

        login.setOnClickListener(this);
        registerText.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */

    private void initObjects() {
        inputValidation = new InputValidation(activity);

        databaseHelper = new DatabaseHelper(activity);
    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.login_submit_button:
                verifyFromSqLite();
                break;

            case R.id.signup_button:

                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(MainActivity.this, SignUp.class);
                startActivity(intentRegister);
                break;

        }

    }

    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */

    private void verifyFromSqLite() {

        if (!inputValidation.isInputEditTextFilled(userName, userLayout, "Enter valid Name ")) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(password, passwordLayout, "Enter valid Password ")) {
            return;
        }

        if (databaseHelper.checkUser(userName.getText().toString().trim(), password.getText().toString().trim())) {

            Intent intent = new Intent(MainActivity.this, ListActivity.class);

            intent.putExtra("USER NAME", userName.getText().toString().trim());

            emptyInputEditText();

            startActivity(intent);

        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(nestedScrollView, "Not valid user name or password", Snackbar.LENGTH_LONG).show();
        }

    }

    /**
     * This method is to empty all input edit text
     */

    private void emptyInputEditText() {

        userName.setText(null);
        password.setText(null);

    }
}
