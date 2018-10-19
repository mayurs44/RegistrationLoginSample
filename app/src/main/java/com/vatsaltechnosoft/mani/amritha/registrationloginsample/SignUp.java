package com.vatsaltechnosoft.mani.amritha.registrationloginsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amritha on 6/25/18.
 */
public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = SignUp.this;

    NestedScrollView nestedScrollview;

    TextInputLayout textInputLayoutFirstName, textInputLayoutLastName, textInputLayoutUserName, textInputLayoutEmail,
            textInputLayoutPassword, textInputLayoutConfirmPassword;

    TextInputEditText firstName, lastName, emailId, userName, password, confirmPassword;

    AppCompatRadioButton maleRadioButton, femaleRadioButton;

    AppCompatSpinner userType;

    RadioGroup radioGroup;

    AppCompatCheckBox readingBookHobbies, listeningMusicHobbies, sportsHobbies;

    AppCompatButton appCompatButtonRegister;

    AppCompatTextView appCompatTextViewLoginLink;

    InputValidation inputValidation;

    DatabaseHelper databaseHelper;

    String selectedItemText;

    Intent intent;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.signup);

        //going to previous activity

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initViews();

        initListeners();

        initObjects();


    }

    private void initViews() {

        nestedScrollview = findViewById(R.id.nestedScrollView);

        textInputLayoutFirstName = findViewById(R.id.layout_first_name);

        textInputLayoutLastName = findViewById(R.id.layout_last_name);

        textInputLayoutEmail = findViewById(R.id.layout_email);

        textInputLayoutUserName = findViewById(R.id.layout_username);

        textInputLayoutPassword = findViewById(R.id.layout_password);

        textInputLayoutConfirmPassword = findViewById(R.id.layout_confirm_password);

        firstName = findViewById(R.id.first_name_editText);

        lastName = findViewById(R.id.last_name_editText);

        emailId = findViewById(R.id.email_id_editText);

        userName = findViewById(R.id.user_name_editText);

        password = findViewById(R.id.password_editText);

        confirmPassword = findViewById(R.id.confirm_password_editText);

        radioGroup = findViewById(R.id.group_id);

        maleRadioButton = findViewById(R.id.male_gender_radioButton);

        femaleRadioButton = findViewById(R.id.female_gender_radioButton);

        userType = findViewById(R.id.userType_spinner);

        readingBookHobbies = findViewById(R.id.reading_hobbies_checkbox);

        listeningMusicHobbies = findViewById(R.id.music_hobbies_checkbox);

        sportsHobbies = findViewById(R.id.sport_hobbies_checkbox);

        appCompatButtonRegister = findViewById(R.id.submit_button);

        appCompatTextViewLoginLink = findViewById(R.id.login_link);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.user_type, R.layout.support_simple_spinner_dropdown_item);

        userType.setAdapter(adapter);

        userType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedItemText = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    /**
     * This method is to initialize listeners
     */

    private void initListeners() {

        appCompatButtonRegister.setOnClickListener(this);

        appCompatTextViewLoginLink.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */


    private void initObjects() {

        inputValidation = new InputValidation(activity);

        databaseHelper = new DatabaseHelper(activity);

        user = new User();

    }

    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.submit_button:
                postDataToSQLite();
                break;

            case R.id.login_link:
                finish();
                break;
        }

    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */

    private void postDataToSQLite() {

        if (!inputValidation.isInputEditTextFilled(firstName, textInputLayoutFirstName, "Enter First Name")) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(lastName, textInputLayoutLastName, "Enter Last Name")) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(emailId, textInputLayoutEmail, "Enter valid Email")) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(userName, textInputLayoutUserName, "Enter user name")) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(password, textInputLayoutPassword, "Enter password")) {
            return;
        }

        if (!inputValidation.isInputEditTextValidate(password, textInputLayoutPassword, "Password minimum 6 ")) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(confirmPassword, textInputLayoutConfirmPassword, "Confirm password")) {
            return;
        }

        //have to check fore radio button,checkbox, spinner

        if (!inputValidation.isInputEditTextMatches(password, confirmPassword, textInputLayoutConfirmPassword, "Password does not match")) {
            return;
        }


        if (!databaseHelper.checkUser(userName.getText().toString().trim())) {

            user.setFirstName(firstName.getText().toString().trim());
            user.setLastName(lastName.getText().toString().trim());
            user.setEmail(emailId.getText().toString());
            user.setUserName(userName.getText().toString().trim());
            user.setPassword(password.getText().toString());
            user.setConfirmPassword(confirmPassword.getText().toString());

            // get selected radio button from radioGroup
            int selectedId = radioGroup.getCheckedRadioButtonId();

            // find the radiobutton by returned id
            RadioButton radioButton = findViewById(selectedId);

            user.setGender(radioButton.getText().toString());

            user.setUserType(selectedItemText);

            if (readingBookHobbies.isChecked()) {
                user.setHobbies(readingBookHobbies.getText().toString());
            }

            if (listeningMusicHobbies.isChecked()) {
                user.setHobbies(listeningMusicHobbies.getText().toString());
            }
            if (sportsHobbies.isChecked()) {
                user.setHobbies(sportsHobbies.getText().toString());
            }

            databaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Toast.makeText(activity, "Registration Successful", Toast.LENGTH_SHORT).show();
            emptyInputEditText();

        } else {

            Toast.makeText(activity, "User Name Already Exists", Toast.LENGTH_LONG).show();

        }

    }

    /**
     * This method is to empty all input edit text
     */

    private void emptyInputEditText() {

        firstName.setText(null);
        lastName.setText(null);
        userName.setText(null);
        emailId.setText(null);
        password.setText(null);
        confirmPassword.setText(null);

    }

}
