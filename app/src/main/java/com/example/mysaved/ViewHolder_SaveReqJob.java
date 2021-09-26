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

public class ViewHolder_SaveReqJob extends RecyclerView.Adapter<ViewHolder_SaveReqJob.MyReqAdapter> {

    ArrayList<ReqJobList> list;
    Context context;

    public ViewHolder_SaveReqJob(Context context, ArrayList<ReqJobList>list){

        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyReqAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.save_reqjob_item,parent,false);
        return new MyReqAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyReqAdapter holder, int position) {
        ReqJobList reqJobList = list.get(position);
        holder.reqjobtitle.setText(reqJobList.getTitle());
        holder.reqgender.setText(reqJobList.getGender());
        holder.reqage.setText(reqJobList.getC_age1());

        String imageUrl = null;
        imageUrl = reqJobList.getImg();
        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyReqAdapter extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener , View.OnClickListener {

        ImageView imageView;
        TextView reqjobtitle,reqgender,reqage;
        CheckBox remove;
        LinearLayout reqjob_card;

        public MyReqAdapter(@NonNull View itemView) {
            super(itemView);
            reqjobtitle = itemView.findViewById(R.id.d_tv_reqjobname2);
            reqgender = itemView.findViewById(R.id.d_tv_reqjobgender2);
            reqage = itemView.findViewById(R.id.d_tv_reqjobage2);
            imageView = itemView.findViewById(R.id.d_img_reqprofile2);
            remove = itemView.findViewById(R.id.d_img_delete2);
            reqjob_card = itemView.findViewById(R.id.d_reqjobcard2);

            remove.setOnCheckedChangeListener(this);
            reqjob_card.setOnClickListener(this);


        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

            if (FirebaseAuth.getInstance().getCurrentUser() == null){
                Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();

                context.startActivity(new Intent(context,LoginActivity.class));
                return;
            }

            DatabaseReference dbremove = FirebaseDatabase.getInstance().getReference("user")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("SaveRequestedjobs");

            int position = getAbsoluteAdapterPosition();
            ReqJobList reqJobList = list.get(position);
            String reqjob = reqJobList.reqid + reqJobList.reqjobid;

            if (b){
                dbremove.child(reqjob).setValue(null);
                compoundButton.setChecked(false);

            }
        }

        @Override
        public void onClick(View view) {
            int position = getAbsoluteAdapterPosition();
            ReqJobList reqJobList = list.get(position);
            String userid = reqJobList.reqid;
            String jobid = reqJobList.reqjobid;

            Intent intent = new Intent(view.getContext(), ViewReqJob.class);
            intent.putExtra("user_id", userid);
            intent.putExtra("ReqId", jobid);

            view.getContext().startActivity(intent);
        }
    }
}
