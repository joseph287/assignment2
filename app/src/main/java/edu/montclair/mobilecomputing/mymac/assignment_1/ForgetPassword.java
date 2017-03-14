package edu.montclair.mobilecomputing.mymac.assignment_1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.montclair.mobilecomputing.mymac.assignment_1.R;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.UserInfoDatabase;



/**
 * Created by Admin on 03-11-2017.  Forget activity to retrieve password if forgotten
 */

public class ForgetPassword extends AppCompatActivity {

    EditText Forget_Password_Username, Forget_Password_Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Forget_Password_Username = (EditText) findViewById(R.id.Forget_Password_Username);
        Forget_Password_Email = (EditText) findViewById(R.id.Forget_Password_Email);

        Button controlButton = (Button) findViewById(R.id.Forget_Password_Register);
        controlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control();
            }
        });

    }

    void control() {
        if (!isEditTextContainEmail(Forget_Password_Email.getText().toString())) {
            Toast.makeText(this.getBaseContext(), "E-Mail Address is wrong!", Toast.LENGTH_LONG).show();
            return;
        } else if (Forget_Password_Username.equals("")) {
            Toast.makeText(this.getBaseContext(), "Enter username", Toast.LENGTH_LONG).show();
        } else {
            UserInfoDatabase userInfoDatabas = new UserInfoDatabase(getApplicationContext());
            String password = userInfoDatabas.getForgotPAssword(Forget_Password_Email.getText().toString(), Forget_Password_Username.getText().toString());
            if (password.equals("")) {
                Toast.makeText(this.getBaseContext(), "Email address not found", Toast.LENGTH_LONG).show();
            } else {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{Forget_Password_Email.getText().toString()});
                email.putExtra(Intent.EXTRA_SUBJECT, "MSU Password");
                email.putExtra(Intent.EXTRA_TEXT, "Password is:- " + password);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email client :"));
            }

        }


        AlertDialog.Builder builder1 = new AlertDialog.Builder(ForgetPassword.this);
        builder1.setCancelable(true);
        builder1.setTitle("Thank you");
        builder1.setMessage("New Password sent to your e-mail");
        builder1.setPositiveButton("Dismiss",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        finish();
                    }
                });
        builder1.setNegativeButton("",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert1 = builder1.create();
        alert1.show();

        return;
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

    public static boolean isEditTextContainEmail(String eemail) {

        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            Matcher matcher = pattern.matcher(eemail);
            return matcher.matches();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
