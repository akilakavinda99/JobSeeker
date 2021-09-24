package com.example.mysaved;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
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


public class ViewHolder_Homepage extends RecyclerView.Adapter<ViewHolder_Homepage.ViewHolder> implements Filterable {


    ArrayList<HomeList> list;
    ArrayList<HomeList> listfull;
    Context context;
    public ViewHolder_Homepage(Context context, ArrayList<HomeList> list){
        this.context= context;
        this.listfull = list;
        this.list = new ArrayList<>(listfull);
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
    @Override
    public Filter getFilter(){
        return jobFilter;
    }

    private final Filter jobFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<HomeList> filteredJobList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){

                filteredJobList.addAll(listfull);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (HomeList homeList : listfull){
                    if (homeList.getTitle().toLowerCase().contains(filterPattern)){
                        filteredJobList.add(homeList);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredJobList;
            filterResults.count = filteredJobList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            list.clear();
            list.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener , View.OnClickListener {

        ImageView imageView, savejob;
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


//            DatabaseReference dbsave = FirebaseDatabase.getInstance().getReference("user")
//                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                    .child("savejobs");
//            int position = getAbsoluteAdapterPosition();
//            HomeList home = list.get(position);
//            String savejob = home.id + home.jobid;
//            dbsave.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
//                        String jid = dataSnapshot.getKey();
//                        if (savejob.equals(jid)){
//                            bookmark.setChecked(true);
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });


        }



//        @Override
//        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//            if (FirebaseAuth.getInstance().getCurrentUser() == null){
//                Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
//                //user not login so make cheack box false
//                compoundButton.setChecked(false);
//                context.startActivity(new Intent(context,LoginActivity.class));
//
//                return;
//            }
//            DatabaseReference dbsave = FirebaseDatabase.getInstance().getReference("user")
//                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
//                    .child("savejobs");
//            int position = getAbsoluteAdapterPosition();
//            HomeList home = list.get(position);
//
//            if(b){
//                compoundButton.setChecked(true);
//                dbsave.child(home.id).child(home.jobid).setValue(home);
//                DatabaseReference rr = FirebaseDatabase.getInstance().getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
//                rr.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                       Lcount = (Long) snapshot.child("SaveJobID").getValue();
////                        assert Lcount != null;
//                        SaveJobID = Lcount.intValue();
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//                SaveJobID++;
//
//                Map<String,Object> user = new HashMap<>();
//                user.put("SaveJobID",  SaveJobID);
//                rr.updateChildren(user);
//
//
//
//            }else{
//                dbsave.child(home.id).child(home.jobid).setValue(null);
//            }
//           dbsave.addValueEventListener(new ValueEventListener() {
//               @Override
//               public void onDataChange(@NonNull DataSnapshot snapshot) {
//                   for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                       String id = dataSnapshot.getKey();
//                       System.out.println(id+" Fucku");
//                       for (DataSnapshot ds : snapshot.getChildren()){
//                           String idjob = ds.getKey();
//                           if(ds.exists()){
//                               compoundButton.setChecked(true);
//                           }
//                       }
//                   }
//               }
//
//               @Override
//               public void onCancelled(@NonNull DatabaseError error) {
//
//               }
//           });
//        }

        @Override
        public void onClick(View view) {
            int position = getAbsoluteAdapterPosition();
            HomeList home = list.get(position);
            String userid = home.id;
            String jobid = home.jobid;

            Intent intent = new Intent(view.getContext(), ViewjobM.class);
            intent.putExtra("user_id", userid);
            intent.putExtra("job_id", jobid);

            view.getContext().startActivity(intent);
        }

        //Help with boolean, check checkbutton is check or not
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (FirebaseAuth.getInstance().getCurrentUser() == null){
                Toast.makeText(context, "Please Login First", Toast.LENGTH_SHORT).show();
                //user not login so make cheack box false
                compoundButton.setChecked(false);
                context.startActivity(new Intent(context,UserloginActivity.class));

                return;
            }
            DatabaseReference dbsave = FirebaseDatabase.getInstance().getReference("user")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("savejobs");
            int position = getAbsoluteAdapterPosition();
            HomeList home = list.get(position);
            String savejob = home.id + home.jobid;

            if (b){
                compoundButton.setChecked(true);
                dbsave.child(savejob).setValue(home);
            }else {
                dbsave.child(savejob).setValue(null);
            }

        }
    }

}
