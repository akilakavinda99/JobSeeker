package com.example.mysaved;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class Profile extends AppCompatActivity {
    TextView text_Pname,text_Pphone,text_Pgender,text_Pdistrict,text_Pemail,user_name,user_phone,user_gender,user_district,user_email;
    Button edit_user_btn,verify_now_btn,logout;
    ImageView user_back_btn,verification_badge;
    FirebaseAuth fAuth;
    DatabaseReference databaseReference;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        text_Pname = findViewById(R.id.tv_nameP);
        text_Pphone = findViewById(R.id.tv_mobilenoP);
        text_Pgender = findViewById(R.id.tv_genderP);
        text_Pdistrict = findViewById(R.id.tv_districtP);
        text_Pemail = findViewById(R.id.tv_emailP);
        user_name =findViewById(R.id.tv_user_name);
        user_phone =findViewById(R.id.tv_user_Mno);
        user_gender =findViewById(R.id.tv_user_gender);
        user_district =findViewById(R.id.tv_user_district);
        user_email =findViewById(R.id.tv_user_email);
        edit_user_btn = findViewById(R.id.btn_edit_user);
        user_back_btn = findViewById(R.id.userprofile_back_btn);
        logout = findViewById(R.id.btn_logout);
        verification_badge = findViewById(R.id.verified_badge);
        verify_now_btn = findViewById(R.id.btn_verify_now);

        //get current instance of firebase authentication
        fAuth = FirebaseAuth.getInstance();

        //check whether user is not logged in, return to Login page
        if (fAuth.getCurrentUser() == null){
            Intent i = new Intent(getApplicationContext(), Userlogin.class).putExtra("from", "profile");
            startActivity(i);
        }
        else{
            userID = fAuth.getCurrentUser().getUid();
            FirebaseUser user = fAuth.getCurrentUser();

            //fetch user data from DB
            databaseReference = FirebaseDatabase.getInstance().getReference().child("user").child(userID);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //assign values to text fields
                    user_name.setText(snapshot.child("Name").getValue().toString());
                    user_phone.setText(snapshot.child("Phone").getValue().toString());
                    user_gender.setText(snapshot.child("Gender").getValue().toString());
                    user_district.setText(snapshot.child("District").getValue().toString());
                    user_email.setText(snapshot.child("Email").getValue().toString());

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            //check whether user is verified or not
            Map<String,Object> USER = new HashMap<>();
            if (user.isEmailVerified()){
                USER.put("verified","yes");
            }
            else {
                USER.put("verified","no");
            }
            //update verified in DB
            DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("user").child(userID);
            df.updateChildren(USER);


            //display verification badge and verify now button
            if (user.isEmailVerified()){
                verification_badge.setVisibility(View.VISIBLE);
                verify_now_btn.setVisibility(View.INVISIBLE);
            }else {
                verify_now_btn.setVisibility(View.VISIBLE);

                verify_now_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(), "Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }
                });
            }

            edit_user_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EditProfile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("GENDER",user_gender.getText().toString());
                    intent.putExtra("DISTRICT",user_district.getText().toString());
                    startActivity(intent);
                }
            });

        }

        user_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CreateJob.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Profile.this, Userlogin.class));
            }
        });

    }
}