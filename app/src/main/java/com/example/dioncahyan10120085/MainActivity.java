package com.example.dioncahyan10120085;
//!--Nama : Dion Cahyan-->
//        <!--Kelas : IF2-->
//        <!--Nim : 10120085-->
//        <!--matkul Akb
//        -->
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private ProfileFragment profileFragment =new ProfileFragment();
    private NearbyFragment2 nearbyFragment2 = new NearbyFragment2();
    private MapsFragment mapsFragment = new MapsFragment();

    private infoFragment infoFragment = new infoFragment();

    private TabLayout tabLayout;
    private ViewPager viewPager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.profile);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, profileFragment).commit();
                return true;
            case R.id.lokasi:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, nearbyFragment2).commit();
                return true;
            case R.id.mylocation:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, mapsFragment).commit();
                return true;
            case R.id.info:
                getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, infoFragment).commit();
                return true;

        }

        return false;
    }
}