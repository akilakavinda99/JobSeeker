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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<Model> mList;
    Context context;

    public MyAdapter(Context context , ArrayList<Model> mList) {

        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listing_card, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Model model = mList.get(position);
        holder.name.setText(model.getName());
        holder.title.setText(model.getTitle());
        holder.job_type.setText(model.getJob_type());
        holder.salary1.setText(model.getSalary1()+" LKR | MO");
        holder.phone1.setText(model.getPhone1()+" | ✆");
        holder.district.setText(model.getDistrict());
        String imgUrl = null;
        imgUrl = model.getImg();
        Picasso.get().load(imgUrl).fit().into(holder.img);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name,title,job_type,salary1,phone1,district;
        ImageView img;
        CardView card_n1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.companyname_box);
            title = itemView.findViewById(R.id.job_title_box);
            job_type = itemView.findViewById(R.id.type2);
            img = itemView.findViewById(R.id.job_img_box);
            salary1 = itemView.findViewById(R.id.salary4);
            phone1 = itemView.findViewById(R.id.number);
            district = itemView.findViewById(R.id.district3);
            card_n1 = itemView.findViewById(R.id.card_new);
            card_n1.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String auth = FirebaseAuth.getInstance().getCurrentUser().getUid();
            int Position = getAbsoluteAdapterPosition();
            Model model = mList.get(Position);
            Intent intent = new Intent(view.getContext(),ViewjobM.class);
            intent.putExtra("user_id", auth);
            intent.putExtra("job_id", model.JobID);
            view.getContext().startActivity(intent);
        }
    }
}
