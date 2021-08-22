package com.example.mysaved;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class NavigationBarActivity extends AppCompatActivity {

        RecyclerView my_listing_recyclerview;
        Button nav_logout,nav_login;
        DrawerLayout drawerLayout;
        TextView nav_home_txt,nav_createjob_txt,nav_myjobs_txt,nav_requestedjobs_txt,nav_myreqjobs_txt,nav_savedjobs_txt,nav_profile_txt;
        ProgressBar progressBar_listings;
        Context context;
        AlertDialog.Builder builder;



           @Override
           protected void onCreate(Bundle savedInstanceState) {
               super.onCreate(savedInstanceState);
               setContentView(R.layout.activity_navigation_bar);
               drawerLayout = findViewById(R.id.drawer_layout);

               nav_login = findViewById(R.id.Nav_login);
               nav_logout = findViewById(R.id.nav_logout);

               nav_home_txt = findViewById(R.id.nav_home_txt);
               nav_createjob_txt = findViewById(R.id.nav_createjob_txt);
               nav_myjobs_txt = findViewById(R.id.nav_myjobs_txt);
               nav_requestedjobs_txt = findViewById(R.id.nav_requestedjobs_txt);
               nav_myreqjobs_txt = findViewById(R.id.nav_myreqjobs_txt);
               nav_savedjobs_txt = findViewById(R.id.nav_savedjobs_txt);
               nav_profile_txt = findViewById(R.id.nav_profile_txt);
               progressBar_listings = findViewById(R.id.progressBar_listings);
               my_listing_recyclerview = findViewById(R.id.my_listing_recyclerview);

           }

      }
