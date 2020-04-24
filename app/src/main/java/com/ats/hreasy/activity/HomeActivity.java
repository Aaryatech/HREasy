package com.ats.hreasy.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.hreasy.BuildConfig;
import com.ats.hreasy.R;
import com.ats.hreasy.constant.Constants;
import com.ats.hreasy.fcm.SharedPrefManager;
import com.ats.hreasy.fragment.ChangePasswordFragment;
import com.ats.hreasy.fragment.ClaimApprovalPendingFragment;
import com.ats.hreasy.fragment.ClaimFragment;
import com.ats.hreasy.fragment.EmployeeListFragment;
import com.ats.hreasy.fragment.HomeFragment;
import com.ats.hreasy.fragment.LeaveApprovalPendingFragment;
import com.ats.hreasy.fragment.LeaveFragment;
import com.ats.hreasy.fragment.PendingClaimListFragment;
import com.ats.hreasy.fragment.PendingLeaveListFragment;
import com.ats.hreasy.fragment.UpdateClaimInfoFragment;
import com.ats.hreasy.fragment.UpdateClaimStatusFragment;
import com.ats.hreasy.fragment.UpdateLeaveInfoFragment;
import com.ats.hreasy.fragment.UpdateLeaveStatusFragment;
import com.ats.hreasy.model.DashboardCount;
import com.ats.hreasy.model.Info;
import com.ats.hreasy.model.Login;
import com.ats.hreasy.sqlite.DatabaseHandler;
import com.ats.hreasy.utils.CommonDialog;
import com.ats.hreasy.utils.CustomSharedPreference;
import com.ats.hreasy.utils.PermissionsUtil;
import com.ats.hreasy.utils.RealPathUtil;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    boolean doubleBackToExitPressedOnce = false;
    Login loginUser;

    private CircleImageView ivNavHeadPhoto;

    DashboardCount dashboardCount = new DashboardCount();

    File folder = new File(Environment.getExternalStorageDirectory() + File.separator, "HREasy_Media");
    File f;

    Bitmap myBitmap = null;
    public static String path, imagePath;
    int userId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (PermissionsUtil.checkAndRequestPermissions(this)) {
        }

        createFolder();

      //  String token = SharedPrefManager.getmInstance(HomeActivity.this).getDeviceToken();
      //  Log.e("Token : ", "----*********************-----" + token);


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

        if (loginUser!=null){

            Log.e("isVisit","------------------------------- "+loginUser.getIsVisit());
            try{
                if (loginUser.getIsVisit()==1){
                    Fragment adf = new ChangePasswordFragment();
                    Bundle args = new Bundle();
                    adf.setArguments(args);
                    getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

                }else{
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                    ft.commit();
                }
            }catch (Exception e){
                e.printStackTrace();
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
                ft.commit();
            }
        }


        View header = navigationView.getHeaderView(0);

        TextView tvNavHeadName = header.findViewById(R.id.tvNavHeadName);
        TextView tvNavHeadDesg = header.findViewById(R.id.tvNavHeadDesg);
        ivNavHeadPhoto = header.findViewById(R.id.ivNavHeadPhoto);

        ivNavHeadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraDialog();
            }
        });

        if (loginUser != null) {
            tvNavHeadName.setText("" + loginUser.getEmpFname() + " " + loginUser.getEmpMname() + " " + loginUser.getEmpSname());
            tvNavHeadDesg.setText("" + loginUser.getEmpDeptName());

            try {
                Picasso.with(HomeActivity.this).load(Constants.IMAGE_URL + "" + loginUser.getEmpPhoto()).placeholder(getResources().getDrawable(R.drawable.profile)).into(ivNavHeadPhoto);

            } catch (Exception e) {
                e.printStackTrace();
            }

            getDashboardCount(loginUser.getEmpId());

        }

        if (loginUser == null) {
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();

        }

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
                homeFragment instanceof PendingClaimListFragment && homeFragment.isVisible() ||
                homeFragment instanceof LeaveFragment && homeFragment.isVisible() ||
                homeFragment instanceof ChangePasswordFragment && homeFragment.isVisible() ||
                homeFragment instanceof ClaimFragment && homeFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new HomeFragment(), "Exit");
            ft.commit();

        } else if (leaveApprovalPendingFragment instanceof UpdateLeaveStatusFragment && leaveApprovalPendingFragment.isVisible() ||
                leaveApprovalPendingFragment instanceof UpdateLeaveInfoFragment && leaveApprovalPendingFragment.isVisible()) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, new LeaveApprovalPendingFragment(), "HomeFragment");
            ft.commit();

        } else if (claimApprovalPendingFragment instanceof UpdateClaimStatusFragment && claimApprovalPendingFragment.isVisible() ||
                claimApprovalPendingFragment instanceof UpdateClaimInfoFragment && claimApprovalPendingFragment.isVisible()) {

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

            Log.e("AUTH", "------------------------------------ " + dashboardCount.getIsAuthorized());

            Fragment adf = new EmployeeListFragment();
            Bundle args = new Bundle();
            args.putString("type", "leave");
            args.putString("isAuth", "" + dashboardCount.getIsAuthorized());
            adf.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();


//
//            Fragment adf = new EmployeeListFragment();
//            Bundle args = new Bundle();
//            args.putString("type", "leave");
//            adf.setArguments(args);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

        } else if (id == R.id.nav_add_claim) {

            Fragment adf = new EmployeeListFragment();
            Bundle args = new Bundle();
            args.putString("type", "claim");
            args.putString("isAuth", "" + dashboardCount.getIsAuthorizedClaim());
            adf.setArguments(args);
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();


//            Fragment adf = new EmployeeListFragment();
//            Bundle args = new Bundle();
//            args.putString("type", "claim");
//            adf.setArguments(args);
//            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, adf, "HomeFragment").commit();

        } else if (id == R.id.nav_change_pass) {

            Fragment adf = new ChangePasswordFragment();
            Bundle args = new Bundle();
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

                    DatabaseHandler db = new DatabaseHandler(HomeActivity.this);
                    db.deleteAll();

                    updateToken(loginUser.getEmpId(), "");


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


    private void getDashboardCount(Integer empId) {
        Log.e("PARAMETERS : ", "       EMPID : " + empId);
        if (Constants.isOnline(HomeActivity.this)) {
            final CommonDialog commonDialog = new CommonDialog(HomeActivity.this, "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<DashboardCount> listCall = Constants.myInterface.getDashboardCount(authHeader, empId);
            listCall.enqueue(new Callback<DashboardCount>() {
                @Override
                public void onResponse(Call<DashboardCount> call, Response<DashboardCount> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("Dashboard Count : ", "------------" + response.body());
                            commonDialog.dismiss();

                            dashboardCount = response.body();

                        } else {
                            commonDialog.dismiss();
                            Log.e("Data Null : ", "-----------");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<DashboardCount> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            Toast.makeText(HomeActivity.this, "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }


    private void updateToken(int empId, String token) {
        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            String base = Constants.userName + ":" + Constants.password;
            String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

            Call<Info> listCall = Constants.myInterface.updateUserToken(authHeader, empId, token);
            listCall.enqueue(new Callback<Info>() {
                @Override
                public void onResponse(Call<Info> call, Response<Info> response) {
                    try {
                        if (response.body() != null) {

                            Log.e("INFO Data : ", "------------" + response.body());

                            Info data = response.body();
                            if (data.getError()) {
                                commonDialog.dismiss();
                                //Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            } else {

                                commonDialog.dismiss();

                                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            commonDialog.dismiss();
                            //Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                            Log.e("Data Null : ", "-----------");

                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        //Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                        Log.e("Exception : ", "-----------" + e.getMessage());
                        e.printStackTrace();

                        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(Call<Info> call, Throwable t) {
                    commonDialog.dismiss();
                    // Toast.makeText(LoginActivity.this, "Unable to login", Toast.LENGTH_SHORT).show();
                    Log.e("onFailure : ", "-----------" + t.getMessage());
                    t.printStackTrace();

                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    public void showCameraDialog() {


       /* final android.support.v7.app.AlertDialog.Builder alertadd = new android.support.v7.app.AlertDialog.Builder(HomeActivity.this);
        LayoutInflater factory = LayoutInflater.from(HomeActivity.this);
        final View view = factory.inflate(R.layout.custom_dialog_camera, null);
        alertadd.setView(view);

        final AlertDialog ad = alertadd.create();

        LinearLayout llCamera=view.findViewById(R.id.llCamera);
        LinearLayout llGallery=view.findViewById(R.id.llGallery);

        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        f = new File(folder + File.separator, "Camera.jpg");

                        String authorities = BuildConfig.APPLICATION_ID + ".provider";
                        Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), authorities, f);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 102);

                        ad.dismiss();



                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        f = new File(folder + File.separator, "Camera.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 102);

                        ad.dismiss();



                    }
                } catch (Exception e) {
                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
                }

            }
        });

        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pictureActionIntent = null;
                pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, 101);
                ad.dismiss();

            }
        });
        alertadd.show();*/

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this, R.style.AlertDialogTheme);
        builder.setTitle("Choose");
        builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent pictureActionIntent = null;
                pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, 101);
            }
        });
        builder.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        f = new File(folder + File.separator, "Camera.jpg");

                        String authorities = BuildConfig.APPLICATION_ID + ".provider";
                        Uri imageUri = FileProvider.getUriForFile(getApplicationContext(), authorities, f);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 102);

                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        f = new File(folder + File.separator, "Camera.jpg");
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        startActivityForResult(intent, 102);

                    }
                } catch (Exception e) {
                    ////Log.e("select camera : ", " Exception : " + e.getMessage());
                }
            }
        });
        builder.show();
    }

    public void createFolder() {
        if (!folder.exists()) {
            folder.mkdir();
        }
    }


    //--------------------------IMAGE-----------------------------------------

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String realPath;
        Bitmap bitmap = null;

        if (resultCode == RESULT_OK && requestCode == 102) {
            try {
                String path = f.getAbsolutePath();
                File imgFile = new File(path);
                if (imgFile.exists()) {
                    myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    ivNavHeadPhoto.setImageBitmap(myBitmap);

                    myBitmap = shrinkBitmap(imgFile.getAbsolutePath(), 720, 720);

                    imagePath = f.getAbsolutePath();

                    try {
                        FileOutputStream out = new FileOutputStream(path);
                        myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();
                        Log.e("Image Saved  ", "---------------");

                        sendImage(loginUser.getUserId());

                    } catch (Exception e) {
                        Log.e("Exception : ", "--------" + e.getMessage());
                        e.printStackTrace();
                    }
                }



                //tvImageName.setText("" + f.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == 101) {
            try {
                realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                Uri uriFromPath = Uri.fromFile(new File(realPath));
                myBitmap = getBitmapFromCameraData(data, this);

                ivNavHeadPhoto.setImageBitmap(myBitmap);
                imagePath = uriFromPath.getPath();
               // tvImageName.setText("" + uriFromPath.getPath());

                try {

                    FileOutputStream out = new FileOutputStream(uriFromPath.getPath());
                    myBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();
                    //Log.e("Image Saved  ", "---------------");

                    sendImage(loginUser.getUserId());

                } catch (Exception e) {
                    // Log.e("Exception : ", "--------" + e.getMessage());
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
                // Log.e("Image Compress : ", "-----Exception : ------" + e.getMessage());
            }
        }
    }


    public static Bitmap getBitmapFromCameraData(Intent data, Context context) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

        String picturePath = cursor.getString(columnIndex);
        path = picturePath;
        cursor.close();

        Bitmap bitm = shrinkBitmap(picturePath, 720, 720);
        Log.e("Image Size : ---- ", " " + bitm.getByteCount());

        return bitm;
        // return BitmapFactory.decodeFile(picturePath, options);
    }

    public static Bitmap shrinkBitmap(String file, int width, int height) {
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);

        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);

        if (heightRatio > 1 || widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio;
            }
        }

        bmpFactoryOptions.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(file, bmpFactoryOptions);
        return bitmap;
    }


    private void sendImage(int empId) {

        Log.e("SEND IMAGE","-******************************-        PARAMETER :  "+empId);

        final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
        commonDialog.show();

        File imgFile = new File(imagePath);

        RequestBody requestFile = RequestBody.create(MediaType.parse("image"), imgFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("profilePic" +
                "", imgFile.getName(), requestFile);

        String base = Constants.userName + ":" + Constants.password;
        String authHeader = "Basic " + Base64.encodeToString(base.getBytes(), Base64.NO_WRAP);

        Call<Info> call = Constants.myInterface.profileImageUpload(authHeader,empId,body);
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
               // commonDialog.dismiss();
                imagePath = "";
                Log.e("Response : ", "-**************************-" + response.body());

                if (response.body()!=null){
                    Info info=response.body();

                    commonDialog.dismiss();

                    loginUser.setEmpPhoto(info.getMsg());

                    Gson gson = new Gson();
                    String json = gson.toJson(loginUser);
                    CustomSharedPreference.putString(HomeActivity.this, CustomSharedPreference.KEY_USER, json);

                    Toast.makeText(HomeActivity.this, "Profile picture updated successfully", Toast.LENGTH_SHORT).show();

                }else{
                    commonDialog.dismiss();
                    Toast.makeText(HomeActivity.this, "Unable to process!", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                Log.e("Error : ", "---Send Image---" + t.getMessage());
                commonDialog.dismiss();
                t.printStackTrace();
                Toast.makeText(HomeActivity.this, "Unable To Process!", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
