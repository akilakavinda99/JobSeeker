package com.example.mysaved;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    ArrayList<ModelRequest> rList;
    Context context;

    public RequestAdapter(Context context , ArrayList<ModelRequest> rList) {

        this.rList = rList;
        this.context = context;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listing_card, parent,false);
        return new RequestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {

        ModelRequest modelRequest = rList.get(position);
        holder.name.setText(modelRequest.getName());
        holder.title.setText(modelRequest.getTitle());
        holder.c_age1.setText(modelRequest.getC_age1()+" Yrs");
        holder.gender.setText(modelRequest.getGender());
        holder.phone1.setText(modelRequest.getPhone1());
        holder.email1.setText(modelRequest.getEmail1());
        String imgUrl = null;
        imgUrl = modelRequest.getImg();
        Picasso.get().load(imgUrl).fit().into(holder.img);

    }

    @Override
    public int getItemCount() {
        return rList.size();
    }

    class RequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name,title,c_age1,gender,phone1,email1;
        ImageView img;
        CardView card_n1;

        public RequestViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.job_title_box);
            title = itemView.findViewById(R.id.companyname_box);
            c_age1 = itemView.findViewById(R.id.type2);
            img = itemView.findViewById(R.id.job_img_box);
            gender = itemView.findViewById(R.id.district3);
            phone1 = itemView.findViewById(R.id.salary4);
            email1 = itemView.findViewById(R.id.number);
            card_n1 = itemView.findViewById(R.id.card_new);
            card_n1.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String auth = FirebaseAuth.getInstance().getCurrentUser().getUid();
            int Position = getAbsoluteAdapterPosition();
            ModelRequest modelRequest = rList.get(Position);
            Intent intent = new Intent(view.getContext(),ViewReqJob.class);
            intent.putExtra("user_id", auth);
            intent.putExtra("ReqId", modelRequest.ReqId);
            view.getContext().startActivity(intent);
        }
    }
}
