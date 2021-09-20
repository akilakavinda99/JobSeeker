package com.example.mysaved;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewHolder_Homepage extends RecyclerView.Adapter<ViewHolder_Homepage.ViewHolder>{


    ArrayList<HomeList> list;
    Context context;
    public ViewHolder_Homepage(Context context, ArrayList<HomeList> list){
        this.context= context;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.homepage_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeList homeList = list.get(position);
        holder.jobtitle.setText(homeList.getTitle());
        holder.jobtype.setText(homeList.getJob_type());
        holder.joblocation.setText(homeList.getDistrict());

        String imageUrl = null;
        imageUrl = homeList.getImg();
        Picasso.get().load(imageUrl).resize(50,50).centerCrop().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener , View.OnClickListener {

        ImageView imageView;
        TextView jobtitle, jobtype, joblocation;
        CheckBox bookmark;
        LinearLayout card;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.d_img_profile);
            jobtitle = itemView.findViewById(R.id.d_tv_jobname);
            jobtype = itemView.findViewById(R.id.d_tv_jobtype);
            joblocation = itemView.findViewById(R.id.d_tv_joblocation);
            bookmark = itemView.findViewById(R.id.d_img_bookmark);
            card = itemView.findViewById(R.id.d_jobcard);

            bookmark.setOnCheckedChangeListener(this);
            card.setOnClickListener(this);
        }

        //Help with boolean check checkbutton is check or not
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            if (FirebaseAuth.getInstance().getCurrentUser() == null){
                Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
                //user not login so make cheack box false
                compoundButton.setChecked(false);
                context.startActivity(new Intent(context,LoginActivity.class));

                return;
            }
            DatabaseReference dbsave = FirebaseDatabase.getInstance().getReference("user")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("savejobs");
            int position = getAbsoluteAdapterPosition();
            HomeList home = list.get(position);

            if(b){
                dbsave.child(home.id).child(home.jobid).setValue(home);

            }else{
                dbsave.child(home.id).child(home.jobid).setValue(null);
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAbsoluteAdapterPosition();
            HomeList home = list.get(position);

            context.startActivity(new Intent(context, New_Saved_Jobs.class));
            System.out.println(home.id);
            System.out.println(home.jobid);
        }
    }

}
