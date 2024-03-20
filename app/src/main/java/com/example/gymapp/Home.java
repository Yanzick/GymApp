package com.example.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.example.gymapp.ui.home.HomeViewModel;
import com.example.gymapp.ui.slideshow.SlideshowFragment;
import com.example.gymapp.ui.slideshow.SlideshowViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.gymapp.databinding.ActivityHomeBinding;
import androidx.lifecycle.ViewModelProvider;
public class Home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    private String userEmail;
    private String tenKH;
    private String sdtKH;
    private SlideshowViewModel slideshowViewModel;
    private HomeViewModel homeViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome.toolbar);
        slideshowViewModel = new ViewModelProvider(this).get(SlideshowViewModel.class);
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        Intent intent = getIntent();
        if (intent != null) {
            slideshowViewModel.setEmail(intent.getStringExtra("userEmail"));
            slideshowViewModel.setTenKH(intent.getStringExtra("tenKH"));
            slideshowViewModel.setSdtKH(intent.getStringExtra("sdtKH"));
            homeViewModel.setTenKH(intent.getStringExtra("tenKH"));
        }
        Log.d("Email","Email: " + userEmail);
        Log.d("Ten","Ten: " + tenKH);
        Log.d("STD","STD: " + sdtKH);
        binding.appBarHome.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, BaiTap.class);
                startActivity(intent);
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_profile, R.id.nav_setting)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


}