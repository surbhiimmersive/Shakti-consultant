package com.shakticonsultant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shakticonsultant.databinding.ActivityMainBinding;
import com.shakticonsultant.responsemodel.ProfileResponse;
import com.shakticonsultant.retrofit.ApiClient;
import com.shakticonsultant.retrofit.ApiInterface;
import com.shakticonsultant.utils.AppPrefrences;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;


import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    String name, email, mobile, img;
    boolean homePressed = true, doubleBackToExitPressedOnce = false;

    //  private AppBarConfiguration mAppBarConfiguration;
    NavHostFragment navHostFragment;
    NavController navController;
    TextView tvname, tvemail, tvmobile, textView36;
    ImageView imageView14;

    private static final int REQUEST_CODE_LOCATION = 9544;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String userid = getIntent().getStringExtra("userid");


// Since the Terminal is a singleton, you can call getInstance whenever you need it
        // Terminal.getInstance();

        //  Toast.makeText(this, "" + AppPrefrences.getUserid(MainActivity.this), Toast.LENGTH_SHORT).show();
        /* Navigation Drawer */
        binding.navView.setNavigationItemSelectedListener(this::onOptionsItemSelected);

//        /* Navigation Drawer with nav component */
//        setUpNavigationForDrawer();

        binding.navView.getMenu().getItem(2).setActionView(R.layout.action_layout_drawer);
        binding.navView.getMenu().getItem(6).setActionView(R.layout.action_layout_drawer);


        //    NavigationViewKt wnavigationView= findViewById(R.id.nav_view);

//        mAppBarConfiguration= new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setDrawerLayout(drawer).build();


        //   NavController navController= Navigation.findNavController(this, R.id.nav_host_fragment);
        //  NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        //    NavigationUI.setupWithNavController(binding.navView, navController);

        /* Bottom Nav */
        setUpNavigation();

        View headerView = binding.navView.inflateHeaderView(R.layout.drawer_header);

        tvname = headerView.findViewById(R.id.textView33);
        tvemail = headerView.findViewById(R.id.textView34);
        tvmobile = headerView.findViewById(R.id.textView35);
        imageView14 = headerView.findViewById(R.id.imageView14);
        textView36 = headerView.findViewById(R.id.textView36);
        getprofiledata();

        textView36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, EditProfileActivity.class);
                i.putExtra("name", name);
                i.putExtra("email", email);
                i.putExtra("mobile", mobile);
                i.putExtra("profile_img", img);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);

            }
        });
        imageView14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, EditProfileActivity.class);
                i.putExtra("name", name);
                i.putExtra("email", email);
                i.putExtra("mobile", mobile);
                i.putExtra("profile_img", img);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right,
                        R.anim.slide_out_left);
            }
        });
    }

    public void setUpNavigation() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = ((NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment)).getNavController();

        NavigationUI.setupWithNavController(binding.bottomNavigationView,
                navHostFragment.getNavController());
        //     NavigationUI.setupWithNavController(binding.navView,navHostFragment.getNavController());
    }

    public void setUpNavigationForDrawer() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavigationUI.setupWithNavController(binding.navView,
                navHostFragment.getNavController());
    }

    public void openDrawer() {
        DrawerLayout drawer = findViewById(R.id.navigation_drawer);
        drawer.openDrawer(GravityCompat.START);
    }

    public void closeDrawer() {
        DrawerLayout drawer = findViewById(R.id.navigation_drawer);
        drawer.closeDrawers();
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_LOCATION && grantResults.length > 0
                && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            throw new RuntimeException("Location services are required in order to " +
                    "connect to a reader.");
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        /* Jobs  */
        LinearLayout actionLayout = (LinearLayout) binding.navView.getMenu().getItem(2).getActionView();
        ImageView arrow = actionLayout.findViewById(R.id.igv_arrow);

        /* Job Application Status */
        LinearLayout actionLayout_1 = (LinearLayout) binding.navView.getMenu().getItem(6).getActionView();
        ImageView arrow_1 = actionLayout_1.findViewById(R.id.igv_arrow);
        Fragment selectedScreen;

        if (item.getItemId() == R.id.drawer_about_us) {
            startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (item.getItemId() == R.id.drawer_home) {
            navController.navigate(R.id.bottom_home);
            closeDrawer();
        } else if (item.getItemId() == R.id.drawer_invite_friends) {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Invite your friend to \n  https://play.google.com/store/apps/details?id=com.shakticonsultant&pli=1");
            startActivity(Intent.createChooser(intent, "Share using"));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);
            //  startActivity(new Intent(getApplicationContext(), HomeFragment.class));
        } else if (item.getItemId() == R.id.drawer_reset_password) {
           /* startActivity(new Intent(getApplicationContext(), ResetPasswordActivity.class));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);*/

            startActivity(new Intent(getApplicationContext(), ResetActivity.class));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);

        } else if (item.getItemId() == R.id.drawer_contact_us) {
            startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (item.getItemId() == R.id.drawer_rate_us) {
            //startActivity(new Intent(getApplicationContext(), RateUsActivity.class));
            //showRateDialog(MainActivity.this);

            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);
        }
       /* else if (item.getItemId() == R.id.drawer_chat_with_us){
            startActivity(new Intent(getApplicationContext(), ChatWIthUsActivity.class));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);
        }*/
        else if (item.getItemId() == R.id.drawer_faq) {
            navController.navigate(R.id.bottom_faq);
            closeDrawer();

        } else if (item.getItemId() == R.id.drawerour_client) {
            startActivity(new Intent(getApplicationContext(), OurClientActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        } else if (item.getItemId() == R.id.drawer_jobs) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean b = !binding.navView.getMenu().findItem(R.id.drawer_jobs1).isVisible();
                    if (b) {
                        arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    } else {
                        arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    }
                    binding.navView.getMenu().findItem(R.id.drawer_jobs1).setVisible(b);
                    binding.navView.getMenu().findItem(R.id.drawer_jobs2).setVisible(b);
                    binding.navView.getMenu().findItem(R.id.drawer_jobs3).setVisible(false);
                }
            }, 50);
        } else if (item.getItemId() == R.id.drawer_job_app_status) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean b = !binding.navView.getMenu().findItem(R.id.drawer_job_app_status1).isVisible();
                    if (b) {
                        arrow_1.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    } else {
                        arrow_1.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    }
                    binding.navView.getMenu().findItem(R.id.drawer_job_app_status1).setVisible(b);
                    binding.navView.getMenu().findItem(R.id.drawer_job_app_status2).setVisible(b);
                }
            }, 50);
        } else if (item.getItemId() == R.id.drawer_job_app_status1) {
            startActivity(new Intent(getApplicationContext(), ScheduleInterviewActivity.class));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);

        } else if (item.getItemId() == R.id.drawer_job_app_status2) {
            startActivity(new Intent(getApplicationContext(), RejectedApplicationActivity.class));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (item.getItemId() == R.id.drawer_jobs1) {
            navController.navigate(R.id.bottom_jobs);
            closeDrawer();
        } else if (item.getItemId() == R.id.drawer_jobs2) {
            startActivity(new Intent(getApplicationContext(), JobsShortListedActivity.class));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);

        }
       /* else if (item.getItemId() == R.id.drawer_jobs3) {
            startActivity(new Intent(getApplicationContext(), JobsRecommendedActivity.class));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);

        }*/

        else if (item.getItemId() == R.id.drawer_subscription_plans) {
            startActivity(new Intent(getApplicationContext(), PackageActivity.class));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (item.getItemId() == R.id.drawer_resume_preview) {
            startActivity(new Intent(getApplicationContext(), ResumeActivity.class));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (item.getItemId() == R.id.drawer_testimonial) {
            startActivity(new Intent(getApplicationContext(), TestimonialActivity.class));
            overridePendingTransition(
                    R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (item.getItemId() == R.id.drawer_logout) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle("Shakti Consultant");
            builder.setIcon(R.drawable.shakti_consultant_logo);
            builder.setMessage("Are you sure you want to logout?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                   /*     System.exit(0);
                        int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                finishAffinity();*/

                    AppPrefrences.setLogin_status(MainActivity.this, false);
                    Intent i = new Intent(MainActivity.this, SignInActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP |
                            Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();


                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
//            startActivity(new Intent(getApplicationContext(), ScheduledInterviews1Activity.class));
        }


        return super.onOptionsItemSelected(item);
    }


    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
    }

    public void getprofiledata() {
        // binding..setVisibility(View.VISIBLE);

        Map<String, String> map = new HashMap<>();

        map.put("user_id", AppPrefrences.getUserid(MainActivity.this));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

        Call<ProfileResponse> resultCall = apiInterface.callgetProfileApi(map);

        resultCall.enqueue(new Callback<ProfileResponse>() {
            @Override
            public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {

                if (response.isSuccessful()) {
                    // binding.progressAbout.setVisibility(View.GONE);
                    if (response.body().isSuccess() == true) {


                        name = response.body().getData().getName();
                        email = response.body().getData().getEmail();
                        mobile = response.body().getData().getMobile();
                        img = response.body().getData().getProfile_image();
                        // Utils.showFailureDialog(SignInActivity.this, response.body().getMessage());
                        Picasso.get()
                                .load(ApiClient.Photourl + response.body().getData().getProfile_image())
                                .memoryPolicy(MemoryPolicy.NO_CACHE)
                                /*   .resize(400, 300)
                                   .centerCrop(Gravity.TOP)*/
                                .fit()
                                .into(imageView14);


                        tvname.setText(response.body().getData().getName());
                        tvemail.setText(response.body().getData().getEmail());
                        tvmobile.setText(response.body().getData().getMobile());
                    } else {
                        // Utils.showFailureDialog(AboutUsActivity.this, response.body().getMessage());


                    }
                }
            }

            @Override
            public void onFailure(Call<ProfileResponse> call, Throwable t) {
                // binding.progressAbout.setVisibility(View.GONE);
                // Utils.showFailureDialog(AboutUsActivity.this, "Something went wrong!");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getprofiledata();
    }

    @Override
    protected void onResume() {
        super.onResume();

        getprofiledata();
    }

    public static void showRateDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.app_name)
                .setMessage("Please, rate the app at PlayMarket")
                .setPositiveButton("RATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (context != null) {
                            String link = "market://details?id=";
                            try {
                                // play market available
                                context.getPackageManager()
                                        .getPackageInfo("com.android.vending", 0);
                                // not available
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                                // should use browser
                                link = "https://play.google.com/store/apps/details?id=com.shakticonsultant&pli=1";
                            }
                            // starts external action
                            context.startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse(link + "com.onlinecompetition.ucmas")));
                        }
                    }
                })
                .setNegativeButton("CANCEL", null);
        builder.show();
    }


   /* @Override
    public void onBackPressed() {
        //super.onBackPressed();


        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle(R.string.app_name);
        builder.setIcon(R.drawable.shakti_consultant_logo);
        builder.setMessage("Are you sure you want to exit?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                       *//* System.exit(0);
                        int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                finishAffinity();
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME );
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
*//*
                finishAffinity();
                System.exit(0);

            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }*/


    public void onBackPressed() {
        if (AppPrefrences.getCLose(MainActivity.this) == false) {
            super.onBackPressed();
        } else {
            // super.onBackPressed();

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            builder.setTitle(R.string.app_name);
            builder.setIcon(R.drawable.shakti_consultant_logo);
            builder.setMessage("Are you sure you want to exit?");

            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {

                    finishAffinity();
                    System.exit(0);

                   /* int pid = android.os.Process.myPid();
                    android.os.Process.killProcess(pid);
                    finishAffinity();
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory( Intent.CATEGORY_HOME );
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);

                    finishAffinity();
                    System.exit(0);
*/
                }
            });

            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                    // Do nothing
                    dialog.dismiss();
                }
            });

            AlertDialog alert = builder.create();
            alert.show();
        }
    }

}