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

public class ViewHolder_ReqHomepage extends RecyclerView.Adapter<ViewHolder_ReqHomepage.ReqViewHolder> implements Filterable {

    ArrayList<ReqJobList> Hlist;
    ArrayList<ReqJobList> Hlistfull;
    Context context;

    public ViewHolder_ReqHomepage(Context context, ArrayList<ReqJobList> Hlist){
        this.context = context;
        this.Hlistfull = Hlist;
        this.Hlist = new ArrayList<>(Hlistfull);

    }

    @NonNull
    @Override
    public ReqViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.reqjob_item,parent,false);
        return new ReqViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReqViewHolder holder, int position) {
        ReqJobList reqJobList = Hlist.get(position);
        holder.reqjobtitle.setText(reqJobList.getTitle());
        holder.reqgender.setText(reqJobList.getGender());
        holder.reqage.setText(reqJobList.getC_age1());

        String imageUrl = null;
        imageUrl = reqJobList.getImg();
        Picasso.get().load(imageUrl).fit().centerCrop().into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return Hlist.size();
    }

    @Override
    public Filter getFilter() {
        return reqjobFilter;
    }
    private final Filter reqjobFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            ArrayList<ReqJobList> filteredReqJobList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0){

                filteredReqJobList.addAll(Hlistfull);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (ReqJobList reqJobList : Hlistfull){
                    if (reqJobList.getTitle().toLowerCase().contains(filterPattern)
                            || reqJobList.getC_age1().toLowerCase().contains(filterPattern)
                            || reqJobList.getGender().toLowerCase().contains(filterPattern)){
                        filteredReqJobList.add(reqJobList);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredReqJobList;
            filterResults.count = filteredReqJobList.size();
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            Hlist.clear();
            Hlist.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();

        }
    };

    class ReqViewHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener , View.OnClickListener {

        ImageView imageView;
        TextView reqjobtitle,reqgender,reqage;
        CheckBox reqbookmark;
        LinearLayout reqjob_card;

        public ReqViewHolder(@NonNull View itemView) {
            super(itemView);
            reqjobtitle = itemView.findViewById(R.id.d_tv_reqjobname);
            reqgender = itemView.findViewById(R.id.d_tv_reqjobgender);
            reqage = itemView.findViewById(R.id.d_tv_reqjobage);
            imageView = itemView.findViewById(R.id.d_img_reqprofile);
            reqbookmark = itemView.findViewById(R.id.d_img_reqbookmark);
            reqjob_card = itemView.findViewById(R.id.d_reqjobcard);

            reqbookmark.setOnCheckedChangeListener(this);
            reqjob_card.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAbsoluteAdapterPosition();
            ReqJobList reqJobList = Hlist.get(position);
            String userid = reqJobList.reqid;
            String jobid = reqJobList.reqjobid;

            Intent intent = new Intent(view.getContext(), ViewReqJob.class);
            intent.putExtra("user_id", userid);
            intent.putExtra("ReqId", jobid);

            view.getContext().startActivity(intent);
        }

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (FirebaseAuth.getInstance().getCurrentUser() == null){
                Toast.makeText(context, "Please login First", Toast.LENGTH_SHORT).show();
                compoundButton.setChecked(false);
                context.startActivity(new Intent(context,UserloginActivity.class));

                return;
            }
            DatabaseReference dbsave = FirebaseDatabase.getInstance().getReference("user")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .child("SaveRequestedjobs");

            int position = getAbsoluteAdapterPosition();
            ReqJobList reqJobList = Hlist.get(position);
            String reqjob = reqJobList.reqid + reqJobList.reqjobid;

            if (b){
                dbsave.child(reqjob).setValue(reqJobList);
            }else {
                dbsave.child(reqjob).setValue(null);
            }

        }
    }

}
