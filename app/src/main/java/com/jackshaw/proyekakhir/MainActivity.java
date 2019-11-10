package com.jackshaw.proyekakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jackshaw.proyekakhir.fragment.FavouriteFragment;
import com.jackshaw.proyekakhir.fragment.MovieFragment;
import com.jackshaw.proyekakhir.fragment.TVShowFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView btn_navigation;
    private FragmentManager fragmentManager;
    private Fragment fragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //menambahkan menu ke action bar
//        getMenuInflater().inflate(R.menu.search, menu);
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }


    public void onComposeAction(MenuItem mi){
        Intent SettingIntent = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(SettingIntent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_navigation = findViewById(R.id.btn_navigation);

        fragmentManager = getSupportFragmentManager();

        fragmentManager.beginTransaction().replace(R.id.main_container, new MovieFragment()).commit();

        btn_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.movie:
                        fragment = new MovieFragment();
                        break;
                    case R.id.tvshow:
                        fragment = new TVShowFragment();
                        break;
                    case R.id.favourite:
                        fragment = new FavouriteFragment();
                        break;
                }
                final FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_container, fragment).commit();
                return true;
            }
        });
    }
}
