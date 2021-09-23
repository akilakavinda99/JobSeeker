package com.example.mysaved;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class EditProfile extends AppCompatActivity {

    TextView edit_user_name,edit_user_phone,edit_user_email;
    Spinner edit_user_gender_spinner,edit_user_district_spinner;
    Button update_user_btn;
    ImageView edit_user_back_btn,user_delete_btn;
    String phonePattern = "[0-9]{10}";

    FirebaseAuth fAuth;
    FirebaseUser user;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    String userID,default_district,default_gender;

    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editprofile);

        String Gender= getIntent().getExtras().getString("GENDER");
        String District= getIntent().getExtras().getString("DISTRICT");

        edit_user_name = findViewById(R.id.et_user_name);
        edit_user_phone = findViewById(R.id.et_user_Mno);
        edit_user_gender_spinner = (Spinner) findViewById(R.id.user_gender_spinner);
        edit_user_district_spinner = (Spinner) findViewById(R.id.user_district_spinner);
        edit_user_email = findViewById(R.id.et_user_email);
        update_user_btn = findViewById(R.id.btn_updateE);
        edit_user_back_btn = findViewById(R.id.edit_user_back_btn);
        user_delete_btn = findViewById(R.id.user_delete_btn);

        ArrayAdapter<CharSequence> adapterG = ArrayAdapter.createFromResource(this, R.array.gender_vp, android.R.layout.simple_spinner_item);
        adapterG.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_user_gender_spinner.setAdapter(adapterG);
        if (Gender != null) {
            int spinnerPosition = adapterG.getPosition(Gender);
            edit_user_gender_spinner.setSelection(spinnerPosition);
        }

        ArrayAdapter<CharSequence> adapterD = ArrayAdapter.createFromResource(this, R.array.districts_vp, android.R.layout.simple_spinner_item);
        adapterD.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edit_user_district_spinner.setAdapter(adapterD);
        if (District != null) {
            int spinnerPosition = adapterD.getPosition(District);
            edit_user_district_spinner.setSelection(spinnerPosition);
        }

        //get current instance of firebase authentication
        fAuth = FirebaseAuth.getInstance();
        rootNode = FirebaseDatabase.getInstance();
        user = fAuth.getCurrentUser();

        //is user is not logged in, return to Login page
        if (fAuth.getCurrentUser() == null) {
            startActivity(new Intent(getApplicationContext(), UserloginActivity.class));
            finish();
        }

        userID = fAuth.getCurrentUser().getUid();
        FirebaseUser user = fAuth.getCurrentUser();


        //fetch user data from DB
        reference = FirebaseDatabase.getInstance().getReference().child("user").child(userID);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //assign values to edit text fields
                edit_user_name.setText(snapshot.child("Name").getValue().toString());
                edit_user_phone.setText(snapshot.child("Phone").getValue().toString());
                default_gender = snapshot.child("Gender").getValue().toString();
                default_district = snapshot.child("District").getValue().toString();
                edit_user_email.setText(snapshot.child("Email").getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        update_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //assign edit text values to variables
                String eName = edit_user_name.getText().toString().trim();
                String ePhone = edit_user_phone.getText().toString().trim();
                String eGender = edit_user_gender_spinner.getSelectedItem().toString();
                String eDistrict = edit_user_district_spinner.getSelectedItem().toString();
                String eEmail = edit_user_email.getText().toString().trim();


                //validations
                if (TextUtils.isEmpty(eName)) {
                    edit_user_name.setError("Name is required");
                    return;
                }

                if(TextUtils.isEmpty(ePhone)){
                    edit_user_phone.setError("Phone is Required");
                }else {
                    if (!edit_user_phone.getText().toString().trim().matches(phonePattern)) {
                        edit_user_phone.setError("Invalid Phone Number");
                        return;
                    }
                }

                if (eGender.equals("Gender")) {
                    Toast.makeText(EditProfile.this, "Select a Gender", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (eDistrict.equals("District")) {
                    Toast.makeText(EditProfile.this, "Select a District", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(eEmail)) {
                    edit_user_email.setError("Email is required");
                    return;
                }


                //update email first
                user.updateEmail(eEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Map<String, Object> user = new HashMap<>();
                        user.put("Name", eName);
                        user.put("Phone", ePhone);
                        user.put("Gender", eGender);
                        user.put("District", eDistrict);
                        user.put("Email", eEmail);

                        //update other properties
                        reference.updateChildren(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Update Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), Profile.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EditProfile.this, "Update Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this, "Invalid Email Address", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        edit_user_back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Profile.class));
            }
        });

        user_delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = new AlertDialog.Builder(v.getContext());
                builder.setMessage("Permanently Delete Account")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //delete user
                                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("user").child(userID);
                                dbRef.removeValue();
                                fAuth.getCurrentUser().delete();
                                startActivity(new Intent(getApplicationContext(), Homepage_new.class));
                                Toast.makeText(EditProfile.this, "User Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete Listing");
                alert.show();

            }
        });
    }

}

