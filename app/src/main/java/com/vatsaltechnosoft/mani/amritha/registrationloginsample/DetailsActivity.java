package com.vatsaltechnosoft.mani.amritha.registrationloginsample;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.function.Function;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener {

    InputValidation inputValidation;
    NestedScrollView nestedScrollView;
    Intent intent;
    AppCompatTextView textFirst, textLast, textEmail, textUserName, textPasswordView, textHobbies;
    TextInputEditText firstName, lastName, emailId, userName, password, hobbies;
    AppCompatTextView fullName, gender, userType;
    AppCompatButton submit;
    User user;
    int index;
    private AppCompatActivity activity = DetailsActivity.this;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        //going to previous activity

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        databaseHelper = new DatabaseHelper(activity);

        initViews();

        intent = getIntent();

        getValues();

        initListeners();

        initObjects();


    }

    private void getValues() {

        //Getting the passed value from ListActivity

        index = intent.getIntExtra("id", 0);

        textFirst.setText(intent.getStringExtra("firstName"));

        textLast.setText(intent.getStringExtra("lastName"));

        String name = intent.getStringExtra("firstName") + " " + intent.getStringExtra("lastName");

        fullName.setText(name);

        textEmail.setText(intent.getStringExtra("emailId"));

        textUserName.setText(intent.getStringExtra("userName"));

        textPasswordView.setText(intent.getStringExtra("password"));

        gender.setText(intent.getStringExtra("gender"));

        userType.setText(intent.getStringExtra("userType"));

        textHobbies.setText(intent.getStringExtra("hobbies"));

    }

    private void initViews() {

        textFirst = findViewById(R.id.text_first_name);

        textLast = findViewById(R.id.text_last_name);

        textEmail = findViewById(R.id.text_email);

        textUserName = findViewById(R.id.text_user_name);

        textPasswordView = findViewById(R.id.text_password);

        textHobbies = findViewById(R.id.text_hobbies);

        firstName = findViewById(R.id.textViewFirstName);

        lastName = findViewById(R.id.textViewLastName);

        fullName = findViewById(R.id.textViewFullName);

        emailId = findViewById(R.id.textViewEmailId);

        userName = findViewById(R.id.textViewUserName);

        password = findViewById(R.id.textViewPassword);

        gender = findViewById(R.id.textViewGender);

        userType = findViewById(R.id.textViewUserType);

        hobbies = findViewById(R.id.textViewHobbies);

        submit = findViewById(R.id.textViewSubmit);

    }


    private void initListeners() {

        submit.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */


    private void initObjects() {

        inputValidation = new InputValidation(activity);

        databaseHelper = new DatabaseHelper(activity);

        user = new User();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.textViewSubmit:
                postDataToSQLite();
                break;
        }

    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */

    private void postDataToSQLite() {

        user.setId(index);

        if (!databaseHelper.checkUser(userName.getText().toString().trim())) {

            if (firstName.getText().toString().isEmpty()) {
                user.setFirstName(textFirst.getText().toString());
            } else {

                user.setFirstName(firstName.getText().toString().trim());

            }

            if (lastName.getText().toString().isEmpty()) {
                user.setLastName(textLast.getText().toString());
            } else {

                user.setLastName(lastName.getText().toString().trim());

            }

            if (emailId.getText().toString().isEmpty()) {
                user.setEmail(textEmail.getText().toString());
            } else {

                user.setEmail(emailId.getText().toString().trim());

            }

            if (userName.getText().toString().isEmpty()) {
                user.setUserName(textUserName.getText().toString());
            } else {

                user.setUserName(userName.getText().toString().trim());

            }

            if (password.getText().toString().isEmpty()) {
                user.setPassword(textPasswordView.getText().toString());
            } else {

                user.setPassword(password.getText().toString().trim());

            }

            user.setGender(gender.getText().toString());

            user.setUserType(userType.getText().toString());

            if (hobbies.getText().toString().isEmpty()) {
                user.setHobbies(textHobbies.getText().toString());
            } else {

                user.setHobbies(hobbies.getText().toString().trim());

            }

            databaseHelper.updateUser(user);

            // Snack Bar to show success message that record saved successfully
            Toast.makeText(activity, "Updated Successfully", Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(activity, "User Name Already Exists", Toast.LENGTH_LONG).show();

        }
    }

}
