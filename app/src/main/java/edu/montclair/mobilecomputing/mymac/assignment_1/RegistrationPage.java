package edu.montclair.mobilecomputing.mymac.assignment_1;


/*created by admin 3-11-2017 registration page, link to forget password, link to register */


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.montclair.mobilecomputing.mymac.assignment_1.util.SecurePreferences;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.UserContainer;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.UserInfoDatabase;

public class RegistrationPage extends AppCompatActivity {
    private EditText regDob, Reg_UserName, Reg_SName, Reg_SMajor, Reg_Email, Reg_pass1, Reg_confirmpassword;
    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Button controlButton = (Button) findViewById(R.id.btn_Reg);
        controlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control();
            }
        });


        regDob = (EditText) findViewById(R.id.Reg_Dob);
        Reg_UserName = (EditText) findViewById(R.id.Reg_UserName);
        Reg_SName = (EditText) findViewById(R.id.Reg_SName);
        Reg_SMajor = (EditText) findViewById(R.id.Reg_SMajor);
        Reg_Email = (EditText) findViewById(R.id.Reg_Email);
        Reg_pass1 = (EditText) findViewById(R.id.Reg_pass1);
        Reg_confirmpassword = (EditText) findViewById(R.id.Reg_confirmpassword);

        regDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });
    }

    void control() {
        if (!Reg_Email.getText().toString().contains(getResources().getString(R.string.emailcheck))) {
            Toast.makeText(this.getBaseContext(), "This is not montclair e-mail address", Toast.LENGTH_LONG).show();
            return;
        } else if (!isEditTextContainEmail(Reg_Email)) {
            Toast.makeText(this.getBaseContext(), "E-Mail Address is wrong!", Toast.LENGTH_LONG).show();
            return;
        } else if (Reg_UserName.getText().toString().equals("")) {
            Toast.makeText(this.getBaseContext(), "Enter Username", Toast.LENGTH_LONG).show();
        } else if (Reg_SName.getText().toString().equals("")) {
            Toast.makeText(this.getBaseContext(), "Enter name", Toast.LENGTH_LONG).show();
        } else if (Reg_SMajor.getText().toString().equals("")) {
            Toast.makeText(this.getBaseContext(), "Enter your Major", Toast.LENGTH_LONG).show();
        } else if (regDob.getText().toString().equals("")) {
            Toast.makeText(this.getBaseContext(), "Select date of birth", Toast.LENGTH_LONG).show();
        } else if (Reg_pass1.getText().toString().equals("")) {
            Toast.makeText(this.getBaseContext(), "Enter Password", Toast.LENGTH_LONG).show();
        } else if (Reg_confirmpassword.getText().toString().equals("")) {
            Toast.makeText(this.getBaseContext(), "Enter Confirm Password", Toast.LENGTH_LONG).show();
        } else {
            String password = Reg_pass1.getText().toString();
            String digit = password.replaceAll("[^0-9]", "");
            String character = password.replaceAll("[^A-Za-z]+", "");
            if (digit.length() == 0 || character.length() == 0) {
                Toast.makeText(this.getBaseContext(), "Enter password combination of character and digit.", Toast.LENGTH_LONG).show();
            } else {
                if (Reg_confirmpassword.getText().toString().equals(Reg_pass1.getText().toString())) {

                    UserInfoDatabase userInfoDatabase = new UserInfoDatabase(getApplicationContext());
                    UserContainer userContainer = new UserContainer();
                    userContainer.setUsername(Reg_UserName.getText().toString());
                    userContainer.setName(Reg_SName.getText().toString());
                    userContainer.setEmail(Reg_Email.getText().toString());
                    userContainer.setMajor(Reg_SMajor.getText().toString());
                    userContainer.setDob(regDob.getText().toString());
                    userContainer.setPassword(Reg_pass1.getText().toString());
                    boolean isLogin = userInfoDatabase.addNewUer(userContainer);
                    if (isLogin) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(RegistrationPage.this);
                        builder1.setCancelable(true);
                        builder1.setTitle("Thank you");
                        builder1.setMessage("Registration Successful");
                        builder1.setPositiveButton("Dismiss",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        SecurePreferences.savePreferences(getApplicationContext(), "username", Reg_UserName.getText().toString());
                                        SecurePreferences.savePreferences(getApplicationContext(), "email", Reg_Email.getText().toString());
                                        SecurePreferences.savePreferences(getApplicationContext(), "isLogin", true);

                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        RegistrationPage.this.finish();
                                    }
                                });
                        AlertDialog alert1 = builder1.create();
                        alert1.show();
                    } else {
                        Toast.makeText(getApplicationContext(), "User Already exist", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this.getBaseContext(), "Confirm Password does not match", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static boolean isEditTextContainEmail(EditText argEditText) {

        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(argEditText.getText().toString().trim());
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        regDob.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }


}

/* registration page with user registration and forget password link */
