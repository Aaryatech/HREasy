package com.ats.hreasy.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hreasy.R;
import com.ats.hreasy.fragment.ClaimApprovalPendingFragment;
import com.ats.hreasy.fragment.ClaimFragment;
import com.ats.hreasy.fragment.EmployeeListFragment;
import com.ats.hreasy.fragment.HomeFragment;
import com.ats.hreasy.fragment.LeaveApprovalPendingFragment;
import com.ats.hreasy.fragment.LeaveFragment;
import com.ats.hreasy.fragment.PendingClaimListFragment;
import com.ats.hreasy.fragment.PendingLeaveListFragment;
import com.ats.hreasy.fragment.UpdateClaimStatusFragment;
import com.ats.hreasy.fragment.UpdateLeaveStatusFragment;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean doubleBackToExitPressedOnce = false;
    Login loginUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String userStr = CustomSharedPreference.getString(this, CustomSharedPreference.KEY_USER);
        Gson gson = new Gson();
        loginUser = gson.fromJson(userStr, Login.class);
        Log.e("HOME_ACTIVITY : ", "--------USER-------" + loginUser);


        View header = navigationView.getHeaderView(0);

        TextView tvNavHeadName = header.findViewById(R.id.tvNavHeadName);
        TextView tvNavHeadDesg = header.findViewById(R.id.tvNavHeadDesg);
        CircleImageView ivNavHeadPhoto = header.findViewById(R.id.ivNavHeadPhoto);

        if (loginUser != null) {
            tvNavHeadName.setText("" + loginUser.getEmpFname() + " " + loginUser.getEmpMname() + " " + loginUser.getEmpSname());
            tvNavHeadDesg.setText("" + loginUser.getEmpDeptName());
        }

        if (loginUser == null) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();

        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
        ft.commit();
    }


    @Override
    public void onBackPressed() {

        Fragment exit = getSupportFragmentManager().findFragmentByTag("Exit");
        Fragment homeFragment = getSupportFragmentManager().findFragmentByTag("HomeFragment");
        Fragment leaveApprovalPendingFragment = getSupportFragmentManager().findFragmentByTag("LeaveApprovalPendingFragment");
        Fragment employeeListFragment = getSupportFragmentManager().findFragmentByTag("EmployeeListFragment");
        Fragment claimApprovalPendingFragment = getSupportFragmentManager().findFragmentByTag("ClaimApprovalPendingFragment");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (exit instanceof HomeFragment && exit.isVisible()) {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            doubleBackToExitPressedOnce = true;
            Toast.makeText(HomeActivity.this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else if (homeFragment instanceof LeaveApprovalPendingFragment && homeFragment.isVisible() ||
                homeFragment instanceof ClaimApprovalPendingFragment && homeFragment.isVisible() ||
                homeFragment instanceof EmployeeListFragment && homeFragment.isVisible() ||
                homeFragment instanceof PendingLeaveListFragment && homeFragment.isVisible() ||
                homeFragment instanceof PendingClaimListFragment && homeFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
            ft.commit();

        } else if (leaveApprovalPendingFragment instanceof UpdateLeaveStatusFragment && leaveApprovalPendingFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new LeaveApprovalPendingFragment(), "HomeFragment");
            ft.commit();

        } else if (claimApprovalPendingFragment instanceof UpdateClaimStatusFragment && claimApprovalPendingFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new ClaimApprovalPendingFragment(), "HomeFragment");
            ft.commit();

        } else if (employeeListFragment instanceof LeaveFragment && employeeListFragment.isVisible() ||
                employeeListFragment instanceof ClaimFragment && employeeListFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new EmployeeListFragment(), "HomeFragment");
            ft.commit();

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
        int id = item.getItemId();

        if (id == R.id.action_notification) {

            startActivity(new Intent(HomeActivity.this, NotificationActivity.class));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
            ft.commit();

        } else if (id == R.id.nav_add_leave) {

            Fragment adf = new EmployeeListFragment();
            Bundle args = new Bundle();
            args.putString("type", "leave");
            adf.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

        } else if (id == R.id.nav_add_claim) {

            Fragment adf = new EmployeeListFragment();
            Bundle args = new Bundle();
            args.putString("type", "claim");
            adf.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

        } else if (id == R.id.nav_logout) {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this, R.style.AlertDialogTheme);
            builder.setTitle("Logout");
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    CustomSharedPreference.deletePreference(HomeActivity.this);
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
