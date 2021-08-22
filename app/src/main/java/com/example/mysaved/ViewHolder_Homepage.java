
package com.example.mysaved;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ViewHolder_Homepage extends RecyclerView.Adapter<ViewHolder_Homepage.ViewHolder> {

    Context context;
    ArrayList<HomeList> list;
    ImageButton bookmark;
    DatabaseReference favouriteref;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public ViewHolder_Homepage(Context context, ArrayList<HomeList> list) {
        this.context = context;
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
        HomeList homelist = list.get(position);
        holder.jobtitle.setText(homelist.getTitle());
        holder.jobtype.setText(homelist.getJob_type());
        holder.joblocation.setText(homelist.getDistrict());


        String imageUrl = null;
        imageUrl= homelist.getImg();
        Picasso.get().load(imageUrl).into(holder.imageView);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    //Constructor Create
    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView jobtitle,jobtype,joblocation;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.d_img_profile);
            jobtitle = itemView.findViewById(R.id.d_tv_jobname);
            jobtype = itemView.findViewById(R.id.d_tv_jobtype);
            joblocation = itemView.findViewById(R.id.d_tv_joblocation);

        }
    }


}




