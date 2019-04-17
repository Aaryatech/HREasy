package com.ats.hreasy.activity;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ats.hreasy.R;
import com.ats.hreasy.fragment.AddLeaveFragment;
import com.ats.hreasy.fragment.HomeFragment;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    private FloatingActionButton fab1, fab2, fab;
//    private Animation fab_open, fab_close, fab_clock, fab_anticlock;
//    TextView tv_fab1, tv_fab2;
//    Boolean isOpen = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        tv_fab1 = (TextView) findViewById(R.id.tv_fab1);
//        tv_fab2 = (TextView) findViewById(R.id.tv_fab2);
//
//        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
//        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
//
//        fab = (FloatingActionButton) findViewById(R.id.fab);
//         fab1 = (FloatingActionButton) findViewById(R.id.fab1);
//         fab2 = (FloatingActionButton) findViewById(R.id.fab2);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//
//                if (isOpen) {
//
//                    tv_fab1.setVisibility(View.INVISIBLE);
//                    tv_fab2.setVisibility(View.INVISIBLE);
//                    fab1.startAnimation(fab_close);
//                    fab2.startAnimation(fab_close);
//                    fab1.setClickable(false);
//                    fab2.setClickable(false);
//                    isOpen = false;
//
//                } else {
//                    tv_fab1.setVisibility(View.VISIBLE);
//                    tv_fab2.setVisibility(View.VISIBLE);
//                    fab1.setClickable(true);
//                    fab2.setClickable(true);
//                    fab1.startAnimation(fab_open);
//                    fab2.startAnimation(fab_open);
//                    isOpen = true;
//                }
//
//            }
//        });
//
//        fab1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Toast.makeText(getApplicationContext(), "Fab1", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        fab2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(), "Fab2", Toast.LENGTH_SHORT).show();
//
//            }
//        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_addLeave) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new AddLeaveFragment(), "HomeFragment");
            ft.commit();
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
