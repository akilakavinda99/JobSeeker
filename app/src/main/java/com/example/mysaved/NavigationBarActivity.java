package com.example.mysaved;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;


public class NavigationBarActivity extends AppCompatActivity {

    Button nav_logout, nav_login;
    DrawerLayout drawerLayout;
    TextView nav_home_txt, nav_savedreqjobs_txt, nav_myjobs_txt, nav_requestedjobs_txt, nav_myreqjobs_txt,
            nav_savedjobs_txt, nav_profile_txt;
    FirebaseAuth fAuth;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);

        drawerLayout = findViewById(R.id.drawer_layout);

        nav_login = findViewById(R.id.btn_nav_login);
        nav_logout = findViewById(R.id.btn_nav_logout);

        nav_home_txt = findViewById(R.id.tv_nav_home);
        nav_myjobs_txt = findViewById(R.id.tv_nav_myjobs);
        nav_savedjobs_txt = findViewById(R.id.tv_nav_savedjobs);
        nav_requestedjobs_txt = findViewById(R.id.tv_nav_requestedjobs);
        nav_myreqjobs_txt = findViewById(R.id.tv_nav_myreqjobs);
        nav_savedreqjobs_txt = findViewById(R.id.tv_nav_savedreqjobs);
        nav_profile_txt = findViewById(R.id.tv_nav_profile);



        //get current instance of firebase authentication
        fAuth = FirebaseAuth.getInstance();

        //check if user is already logged in
        if (fAuth.getCurrentUser() != null) {
            userID = fAuth.getCurrentUser().getUid();
            nav_login.setVisibility(View.GONE);
            nav_logout.setVisibility(View.VISIBLE);
        } else {
            nav_logout.setVisibility(View.GONE);
            nav_login.setVisibility(View.VISIBLE);

        }

    }


    public void ClickMenu(View view) {
        //open drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout) {
        //open drawer layer
        drawerLayout.openDrawer(GravityCompat.START);

    }

    //navigation drawer button functions
    public void login(View view) {

        Intent i = new Intent(getApplicationContext(), UserloginActivity.class).putExtra("from", "NavigationBarActivity");
        startActivity(i);

    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        nav_logout.setVisibility(View.GONE);
        nav_login.setVisibility(View.VISIBLE);

    }

    //side_nav buttons
    public void navClickHome(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.black));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));

        startActivity(new Intent(getApplicationContext(), Homepage_new.class));


    }

    public void navClickMyjobs(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.black));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));

        startActivity(new Intent(getApplicationContext(), MyJobListings.class));

    }

    public void navClickSavedjobs(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.black));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));

        startActivity(new Intent(getApplicationContext(), New_Saved_Jobs.class));

    }

    public void navClickRequestedjobs(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.black));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));

        startActivity(new Intent(getApplicationContext(), NewReqJob_Homepage.class));

    }

    public void navClickMyreqjobs(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.black));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));

        startActivity(new Intent(getApplicationContext(), MyRequestedJobs.class));

    }

    public void navClickSavedReqjobs(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.black));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));

        startActivity(new Intent(getApplicationContext(), New_SaveReq_Jobs.class));

    }

    public void navClickProfile(View view) {
        nav_home_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_requestedjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_myreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_savedreqjobs_txt.setTextColor(ContextCompat.getColor(this, R.color.nav));
        nav_profile_txt.setTextColor(ContextCompat.getColor(this, R.color.black));

        startActivity(new Intent(getApplicationContext(), Profile.class));

    }
}
