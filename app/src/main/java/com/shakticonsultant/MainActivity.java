package com.shakticonsultant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.ui.NavigationViewKt;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.shakticonsultant.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

  //  private AppBarConfiguration mAppBarConfiguration;

    NavHostFragment navHostFragment;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());




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
    }

    public void setUpNavigation(){
        navHostFragment = (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController= ((NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment)).getNavController();

                NavigationUI.setupWithNavController(binding.bottomNavigationView,
                navHostFragment.getNavController());
   //     NavigationUI.setupWithNavController(binding.navView,navHostFragment.getNavController());
    }

    public void setUpNavigationForDrawer(){
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        NavigationUI.setupWithNavController(binding.navView,
                navHostFragment.getNavController());
    }

    public void openDrawer(){
        DrawerLayout drawer = findViewById(R.id.navigation_drawer);
        drawer.openDrawer(GravityCompat.START);
    }

    public void closeDrawer(){
        DrawerLayout drawer = findViewById(R.id.navigation_drawer);
        drawer.closeDrawers();
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

        if (item.getItemId() == R.id.drawer_about_us){
            startActivity(new Intent(getApplicationContext(), AboutUsActivity.class));
        }
        else if (item.getItemId() == R.id.drawer_home){
            navController.navigate(R.id.bottom_home);
            closeDrawer();
        }
        else if (item.getItemId() == R.id.drawer_invite_friends){

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Share this app.");
            startActivity(Intent.createChooser(intent, "Share using"));
          //  startActivity(new Intent(getApplicationContext(), HomeFragment.class));
        }


        else if (item.getItemId() == R.id.drawer_reset_password){
            startActivity(new Intent(getApplicationContext(), ForgotPasswordActivity.class));
        }
        else if (item.getItemId() == R.id.drawer_contact_us){
            startActivity(new Intent(getApplicationContext(), ContactUsActivity.class));
        }
        else if (item.getItemId() == R.id.drawer_rate_us){
            startActivity(new Intent(getApplicationContext(), RateUsActivity.class));
        }
        else if (item.getItemId() == R.id.drawer_chat_with_us){
            startActivity(new Intent(getApplicationContext(), ChatWIthUsActivity.class));
        }
        else if(item.getItemId() == R.id.drawer_faq){
            navController.navigate(R.id.bottom_faq);
            closeDrawer();
        }
        else if (item.getItemId() == R.id.drawer_jobs){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean b = !binding.navView.getMenu().findItem(R.id.drawer_jobs1).isVisible();
                    if (b){
                        arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    }
                    else {
                        arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    }
                    binding.navView.getMenu().findItem(R.id.drawer_jobs1).setVisible(b);
                    binding.navView.getMenu().findItem(R.id.drawer_jobs2).setVisible(b);
                    binding.navView.getMenu().findItem(R.id.drawer_jobs3).setVisible(b);
                }
            }, 50);
        }
        else if (item.getItemId() == R.id.drawer_job_app_status){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    boolean b = !binding.navView.getMenu().findItem(R.id.drawer_job_app_status1).isVisible();
                    if (b){
                        arrow_1.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                    }
                    else {
                        arrow_1.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                    }
                    binding.navView.getMenu().findItem(R.id.drawer_job_app_status1).setVisible(b);
                    binding.navView.getMenu().findItem(R.id.drawer_job_app_status2).setVisible(b);
                }
            }, 50);
        }
        else if (item.getItemId() == R.id.drawer_jobs1){
            navController.navigate(R.id.bottom_jobs);
            closeDrawer();
        }
        else if (item.getItemId() == R.id.drawer_jobs2){
//            startActivity(new Intent(getApplicationContext(), JobsShortListedActivity.class));
        }
        else if (item.getItemId() == R.id.drawer_jobs3){
//            startActivity(new Intent(getApplicationContext(), JobsRecommendedActivity.class));
        }
        else if (item.getItemId() == R.id.drawer_subscription_plans){
            startActivity(new Intent(getApplicationContext(), PackageActivity.class));
        }
        else if (item.getItemId() == R.id.drawer_resume_preview){
            startActivity(new Intent(getApplicationContext(), ResumeActivity.class));
        }


        else if (item.getItemId() == R.id.drawer_job_app_status1){
//            startActivity(new Intent(getApplicationContext(), ScheduledInterviews1Activity.class));
        }
//        else if (item.getItemId() == R.id.drawer_job_app_status2){
//            startActivity(new Intent(getApplicationContext(), RejectedApplicationActivity.class));
//        }


        return super.onOptionsItemSelected(item);
    }


    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, fragment)
                .commit();
    }
}