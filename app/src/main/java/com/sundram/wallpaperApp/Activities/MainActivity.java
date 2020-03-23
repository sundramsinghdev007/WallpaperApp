package com.sundram.wallpaperApp.Activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.sundram.wallpaperApp.Fragments.CollectionsFragment;
import com.sundram.wallpaperApp.Fragments.FavouriteFragment;
import com.sundram.wallpaperApp.Fragments.PhotosFragment;
import com.sundram.wallpaperApp.R;
import com.sundram.wallpaperApp.Utils.Functions;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    int white;
    private static TextView connectionCheck;
    String msg;
    Handler mHandler;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        white = getResources().getColor(R.color.white);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Wallpaper");
        toolbar.setTitleTextColor(white);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.dashBoard_layout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        drawerToggle.syncState();

        navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        PhotosFragment photosFragment = new PhotosFragment();
        Functions.changeMainFragment(MainActivity.this, photosFragment);

        //Check Connection State
        connectionCheck = findViewById(R.id.connectionTextView);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int itemId = menuItem.getItemId();
        if (itemId == R.id.photos) {

            PhotosFragment photosFragment = new PhotosFragment();
            Functions.changeMainFragment(MainActivity.this, photosFragment);

        } else if (itemId == R.id.collection) {

            CollectionsFragment collectionsFragment = new CollectionsFragment();
            Functions.changeMainFragment(MainActivity.this, collectionsFragment);

        } else if (itemId == R.id.fab) {

            FavouriteFragment favouriteFragment = new FavouriteFragment();
            Functions.changeMainFragment(MainActivity.this, favouriteFragment);
        }

        DrawerLayout drawer = findViewById(R.id.dashBoard_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.dashBoard_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }
}

