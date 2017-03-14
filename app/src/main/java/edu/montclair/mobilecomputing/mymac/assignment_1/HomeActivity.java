package edu.montclair.mobilecomputing.mymac.assignment_1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import edu.montclair.mobilecomputing.mymac.assignment_1.fragment.BrowserHistoryFragment;
import edu.montclair.mobilecomputing.mymac.assignment_1.fragment.HomeFragment;
import edu.montclair.mobilecomputing.mymac.assignment_1.fragment.ProfileFragment;
import edu.montclair.mobilecomputing.mymac.assignment_1.util.SecurePreferences;


/**
 * Created by Admin on 03-11-2017.  home activity with link to profile logout home activitity
 */
public class HomeActivity extends AppCompatActivity {
    Toolbar toolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    LinearLayout ll_logout, ll_about, ll_home, ll_profile;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);

        ll_logout = (LinearLayout) findViewById(R.id.ll_logout);
        ll_about = (LinearLayout) findViewById(R.id.ll_about);
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_profile = (LinearLayout) findViewById(R.id.ll_profile);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        getSupportActionBar().setHomeButtonEnabled(true);
        changeFragment(new BrowserHistoryFragment());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUp((DrawerLayout) findViewById(R.id.my_drawer_layout));

        ll_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                SecurePreferences.savePreferences(getApplicationContext(), "isLogin", false);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        ll_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                changeFragment(new HomeFragment());
            }
        });
        ll_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                changeFragment(new BrowserHistoryFragment());

            }
        });
        ll_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
                changeFragment(new ProfileFragment());

            }
        });
    }

    public void setUp(DrawerLayout drawerLayout) {
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(HomeActivity.this, drawerLayout, toolbar, R.string.open,
                R.string.close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public void changeFragment(Fragment fragment) {
//        .addToBackStack(fragment.getClass().getName())
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_replaceFrame, fragment, fragment.getClass().getName())
                .commit();
    }
}
