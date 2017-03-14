package edu.montclair.mobilecomputing.mymac.assignment_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.montclair.mobilecomputing.mymac.assignment_1.util.SecurePreferences;



/**
 * Created by Admin on 03-11-2017.  Landing page
 */

public class LandingScreen extends AppCompatActivity {
    protected boolean _active = true;
    protected int _splashTime = 3000; // time to display the splash screen in ms

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_screen);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime)) {
                        sleep(100);
                        if (_active) {
                            waited += 100;
                        }
                    }
                } catch (Exception e) {

                } finally {

                    if (SecurePreferences.getBooleanPreference(getApplicationContext(), "isLogin")) {
                        startActivity(new Intent(LandingScreen.this,
                                HomeActivity.class));
                        finish();
                    } else {
                        startActivity(new Intent(LandingScreen.this,
                                MainActivity.class));
                        finish();
                    }

                }
            }

            ;
        };
        splashTread.start();

    }
}
