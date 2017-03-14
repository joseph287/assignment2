package edu.montclair.mobilecomputing.mymac.assignment_1.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.montclair.mobilecomputing.mymac.assignment_1.MainActivity;
import edu.montclair.mobilecomputing.mymac.assignment_1.R;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.SecurePreferences;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.UserContainer;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.UserInfoDatabase;


/**
 * Created by Admin on 03-11-2017.  profile activity to update as needed
 */
public class ProfileFragment extends Fragment {
    private EditText Reg_UserName, Reg_SName, Reg_SMajor, Reg_Email, Reg_pass1, Reg_newpassword, Reg_confirmpassword;
    Button btn_Reg;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.proilefragment, container, false);
        Reg_UserName = (EditText) view.findViewById(R.id.Reg_UserName);
        Reg_SName = (EditText) view.findViewById(R.id.Reg_SName);
        Reg_SMajor = (EditText) view.findViewById(R.id.Reg_SMajor);
        Reg_Email = (EditText) view.findViewById(R.id.Reg_Email);

        Reg_newpassword = (EditText) view.findViewById(R.id.Reg_newpassword);
        Reg_pass1 = (EditText) view.findViewById(R.id.Reg_pass1);

        Reg_confirmpassword = (EditText) view.findViewById(R.id.Reg_confirmpassword);
        btn_Reg = (Button) view.findViewById(R.id.btn_Reg);


        SecurePreferences.getStringPreference(getActivity(), "user_id");
        Reg_UserName.setText(SecurePreferences.getStringPreference(getActivity(), "username"));
        Reg_Email.setText(SecurePreferences.getStringPreference(getActivity(), "email"));
        Reg_SMajor.setText(SecurePreferences.getStringPreference(getActivity(), "major"));
        Reg_SName.setText(SecurePreferences.getStringPreference(getActivity(), "name"));
        btn_Reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                control();
            }
        });

        return view;
    }

    void control() {
        if (!Reg_Email.getText().toString().contains(getResources().getString(R.string.emailcheck))) {
            Toast.makeText(getActivity(), "This is not montclair e-mail address", Toast.LENGTH_LONG).show();
            return;
        } else if (!isEditTextContainEmail(Reg_Email)) {
            Toast.makeText(getActivity(), "E-Mail Address is wrong!", Toast.LENGTH_LONG).show();
            return;
        } else if (Reg_UserName.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Enter Username", Toast.LENGTH_LONG).show();
        } else if (Reg_SName.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Enter name", Toast.LENGTH_LONG).show();
        } else if (Reg_SMajor.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "Enter your Major", Toast.LENGTH_LONG).show();
        } else {
            if (Reg_pass1.getText().toString().equals("")) {
                UserInfoDatabase userInfoDatabase = new UserInfoDatabase(getActivity());
                final UserContainer userContainer = new UserContainer();
                userContainer.setUser_id(SecurePreferences.getStringPreference(getActivity(), "user_id"));
                userContainer.setUsername(Reg_UserName.getText().toString());
                userContainer.setName(Reg_SName.getText().toString());
                userContainer.setEmail(Reg_Email.getText().toString());
                userContainer.setMajor(Reg_SMajor.getText().toString());
                userInfoDatabase.updateUserWithoutPassword(userContainer);

                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setCancelable(true);
                builder1.setTitle("Thank you");
                builder1.setMessage("update Profile Successfully");
                builder1.setPositiveButton("Dismiss",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                SecurePreferences.savePreferences(getActivity(), "user_id", userContainer.getUser_id());
                                SecurePreferences.savePreferences(getActivity(), "username", userContainer.getUsername());
                                SecurePreferences.savePreferences(getActivity(), "email", userContainer.getEmail());
                                SecurePreferences.savePreferences(getActivity(), "major", userContainer.getMajor());
                                SecurePreferences.savePreferences(getActivity(), "name", userContainer.getName());
                            }
                        });
                AlertDialog alert1 = builder1.create();
                alert1.show();
            } else {
                if (Reg_pass1.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Password", Toast.LENGTH_LONG).show();
                } else if (Reg_newpassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter New Password", Toast.LENGTH_LONG).show();
                } else if (Reg_confirmpassword.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Enter Confirm Password", Toast.LENGTH_LONG).show();
                }
                String password = Reg_newpassword.getText().toString();
                String digit = password.replaceAll("[^0-9]", "");
                String character = password.replaceAll("[^A-Za-z]+", "");

                if (digit.length() == 0 || character.length() == 0) {
                    Toast.makeText(getActivity(), "Enter password combination of character and digit.", Toast.LENGTH_LONG).show();
                } else {
                    if (Reg_confirmpassword.getText().toString().equals(Reg_newpassword.getText().toString()) && Reg_pass1.getText().toString().equals(SecurePreferences.getStringPreference(getActivity(), "password"))) {

                        UserInfoDatabase userInfoDatabase = new UserInfoDatabase(getActivity());
                        final UserContainer userContainer = new UserContainer();
                        userContainer.setUsername(Reg_UserName.getText().toString());
                        userContainer.setName(Reg_SName.getText().toString());
                        userContainer.setEmail(Reg_Email.getText().toString());
                        userContainer.setMajor(Reg_SMajor.getText().toString());
                        userContainer.setPassword(Reg_newpassword.getText().toString());
                        userInfoDatabase.updateUserWithPassword(userContainer);

                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                        builder1.setCancelable(true);
                        builder1.setTitle("Thank you");
                        builder1.setMessage("update Profile Successfully");
                        builder1.setPositiveButton("Dismiss",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        SecurePreferences.savePreferences(getActivity(), "user_id", userContainer.getUser_id());
                                        SecurePreferences.savePreferences(getActivity(), "username", userContainer.getUsername());
                                        SecurePreferences.savePreferences(getActivity(), "email", userContainer.getEmail());
                                        SecurePreferences.savePreferences(getActivity(), "major", userContainer.getMajor());
                                        SecurePreferences.savePreferences(getActivity(), "password", userContainer.getPassword());
                                        SecurePreferences.savePreferences(getActivity(), "name", userContainer.getName());
                                    }
                                });
                        AlertDialog alert1 = builder1.create();
                        alert1.show();
                    } else {
                        Toast.makeText(getActivity(), "Old Password does not match", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
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

}
