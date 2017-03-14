package edu.montclair.mobilecomputing.mymac.assignment_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.montclair.mobilecomputing.mymac.assignment_1.util.SecurePreferences;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.UserContainer;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.UserInfoDatabase;



/**
 * Created by Admin on 03-11-2017.  main activity all activities are declared and executed
 */

public class MainActivity extends AppCompatActivity {
    EditText editText_name_MA1, editText_password_MA2;
    TextView txtforgotpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_name_MA1 = (EditText) findViewById(R.id.editText_name_MA1);
        editText_password_MA2 = (EditText) findViewById(R.id.editText_password_MA2);
        txtforgotpassword = (TextView) findViewById(R.id.txtforgotpassword);


        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editText_name_MA1.getText().toString();
                String password = editText_password_MA2.getText().toString();

                if (username.equals("") || password.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                } else {
                    UserInfoDatabase userInfoDatabase = new UserInfoDatabase(getApplicationContext());
                    UserContainer userContainer = userInfoDatabase.checkUerExist(username, password);
                    if (userContainer.getUsername().equals("")) {
                        Toast.makeText(getApplicationContext(), "Username and password are wrong!!", Toast.LENGTH_SHORT).show();
                    } else {
                        SecurePreferences.savePreferences(getApplicationContext(), "user_id", userContainer.getUser_id());
                        SecurePreferences.savePreferences(getApplicationContext(), "username", userContainer.getUsername());
                        SecurePreferences.savePreferences(getApplicationContext(), "email", userContainer.getEmail());
                        SecurePreferences.savePreferences(getApplicationContext(), "major", userContainer.getMajor());
                        SecurePreferences.savePreferences(getApplicationContext(), "password", userContainer.getPassword());
                        SecurePreferences.savePreferences(getApplicationContext(), "name", userContainer.getName());
                        SecurePreferences.savePreferences(getApplicationContext(), "isLogin", true);
                        loginSucessFully();
                    }
                }
            }
        });
        txtforgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
                startActivity(intent);
            }
        });
        Button mRegistrationForm = (Button) findViewById(R.id.btn_register_MA);
        mRegistrationForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrationForm();
            }
        });

    }

    void forgetPasswordForm() {
        Intent gecis = new Intent(MainActivity.this, ForgetPassword.class);
        //gecis.putExtra("key", number);
        //gecis.putExtra("number", number);
        startActivity(gecis);
    }

    void registrationForm() {
        Intent gecis = new Intent(MainActivity.this, RegistrationPage.class);
        //gecis.putExtra("key", number);
        //gecis.putExtra("number", number);
        startActivity(gecis);
    }

    private void loginSucessFully() {
        Intent gecis = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(gecis);
        finish();
    }

}
