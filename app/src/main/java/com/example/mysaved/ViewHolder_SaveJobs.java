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

public class ViewHolder_SaveJobs extends RecyclerView.Adapter<ViewHolder_SaveJobs.MyViewHolder> {

    ArrayList<HomeList> Hlist;
    Context context;

    public ViewHolder_SaveJobs(Context context, ArrayList<HomeList> Hlist){

        this.Hlist = Hlist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.savejobs_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HomeList homeList = Hlist.get(position);
        holder.jobtitle.setText(homeList.getTitle());
        holder.jobtype.setText(homeList.getJob_type());
        holder.joblocation.setText(homeList.getDistrict());

        String imageUrl = null;
        imageUrl = homeList.getImg();
        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return Hlist.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener , View.OnClickListener {

        ImageView imageView;
        TextView jobtitle,jobtype,joblocation,count;
        CheckBox remove;
        LinearLayout save_card;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jobtitle = itemView.findViewById(R.id.d_tv_jobname2);
            jobtype = itemView.findViewById(R.id.d_tv_jobtype2);
            joblocation = itemView.findViewById(R.id.d_tv_joblocation2);
            imageView = itemView.findViewById(R.id.d_img_profile2);
            remove = itemView.findViewById(R.id.d_img_delete);
//             count = itemView.findViewById(R.id.tv_count);
            save_card = itemView.findViewById(R.id.d_save_card);

            remove.setOnCheckedChangeListener(this);
            save_card.setOnClickListener(this);
            System.out.println(getItemCount()+" ssss");

        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            if (FirebaseAuth.getInstance().getCurrentUser() == null){
                Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
                //user not login direct him to login page
                context.startActivity(new Intent(context,LoginActivity.class));

                return;
            }

            DatabaseReference dbremove = FirebaseDatabase.getInstance().getReference("user")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("savejobs");

            int position = getAbsoluteAdapterPosition();
            HomeList home = Hlist.get(position);
            String savejob = home.id + home.jobid;
            System.out.println(savejob+ " save job");

            if(b){
                System.out.println(home.id);
                System.out.println(home.jobid);
                dbremove.child(savejob).setValue(null);
                compoundButton.setChecked(false);
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAbsoluteAdapterPosition();
            HomeList home = Hlist.get(position);
            String userid = home.id;
            String jobid = home.jobid;
//            Intent intent;
//            context.startActivity(new intent(context, New_Saved_Jobs.class));
            Intent intent = new Intent(view.getContext(), ViewjobM.class);
            intent.putExtra("user_id", userid);
            intent.putExtra("job_id", jobid);

            view.getContext().startActivity(intent);
        }
    }
}
