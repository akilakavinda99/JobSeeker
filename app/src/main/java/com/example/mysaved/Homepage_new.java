package com.example.mysaved;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Homepage_new extends AppCompatActivity {

    Button nav_logout, nav_login;
    DrawerLayout drawerLayout;
    TextView nav_home_txt, nav_savedreqjobs_txt, nav_myjobs_txt, nav_requestedjobs_txt, nav_myreqjobs_txt,
            nav_savedjobs_txt, nav_profile_txt;
    FirebaseAuth fAuth;
    String userID;

    private RecyclerView recyclerView;
    private DatabaseReference root;
    private ViewHolder_Homepage viewHolder_homepage;
    private ArrayList<HomeList> list;
//    private CheckBox save;
    private SearchView search;
    private ProgressBar load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_new);

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

        load = findViewById(R.id.d_progressBar4);


        recyclerView = findViewById(R.id.d_recycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(Homepage_new.this));

        list = new ArrayList<>();


//        DatabaseReference dbsave = FirebaseDatabase.getInstance().getReference("user")
//                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                .child("savejobs");

//        save = findViewById(R.id.d_img_bookmark);
//        save.setChecked(true);

        root = FirebaseDatabase.getInstance().getReference();

        root.child("create_job").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String id = dataSnapshot.getKey();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String jobid = ds.getKey();
                        String date = ds.child("date").getValue(String.class);
                        String name = ds.child("name").getValue(String.class);
                        String title = ds.child("title").getValue(String.class);
                        String salary1 = ds.child("salary1").getValue(String.class);
                        String job_type = ds.child("job_type").getValue(String.class);
                        String description = ds.child("description").getValue(String.class);
                        String email1 = ds.child("email1").getValue(String.class);
                        String phone1 = ds.child("phone1").getValue(String.class);
                        String district = ds.child("district").getValue(String.class);
                        String img = ds.child("img").getValue(String.class);

                        HomeList home = new HomeList(id, jobid,date,name, title, salary1, job_type, description, email1, phone1, district, img);
                        list.add(home);
                        load.setVisibility(View.INVISIBLE);
                    }
//                    Collections.reverse(list);
                    Collections.sort(list, new Comparator<HomeList>() {
                        @Override
                        public int compare(HomeList homeList, HomeList t1) {
                            return homeList.date.compareToIgnoreCase(t1.date);
                        }
                    });
                    Collections.reverse(list);
                    viewHolder_homepage = new ViewHolder_Homepage(Homepage_new.this, list);

                    recyclerView.setAdapter(viewHolder_homepage);
                    viewHolder_homepage.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("The read failed: " + error.getCode());
                Toast.makeText(Homepage_new.this, "DataBase Error try again", Toast.LENGTH_SHORT).show();
            }
        });
        search = (SearchView) findViewById(R.id.d_search);
        search.setQueryHint("Search Jobs");
        search.setImeOptions(EditorInfo.IME_ACTION_DONE);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                viewHolder_homepage.getFilter().filter(s);
                System.out.println(s);
                return false;
            }
        });
        if(!isNetworkAvailable())
        {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Internet Connection Alert")
                    .setMessage("Please Check Your Internet Connection")
                    .setPositiveButton("Close", null).show();
        }
        else if(isNetworkAvailable())
        {
            Toast.makeText(Homepage_new.this, "Welcome", Toast.LENGTH_LONG).show();
        }

    }

    public boolean isNetworkAvailable() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {

                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {

                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {

                        return true;
                    }
                }
            }
        }

        return false;

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

        Intent i = new Intent(getApplicationContext(), UserloginActivity.class).putExtra("from", "main");
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