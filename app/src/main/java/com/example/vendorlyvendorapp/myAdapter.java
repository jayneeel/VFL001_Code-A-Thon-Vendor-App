package com.example.vendorlyvendorapp;

import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class myAdapter  extends FirebaseRecyclerAdapter<model,myAdapter.myViewHolder> {

    public myAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull model model) {
        holder.mainTxt.setText(model.getName());
        holder.priceTxt.setText("Rs. "+model.getPrice());
        Glide.with(holder.imageView.getContext()).load(model.getImgurl()).into(holder.imageView);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView mainTxt,priceTxt;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img1);
            mainTxt = itemView.findViewById(R.id.nameTxt);
            priceTxt = itemView.findViewById(R.id.priceTxt);

        }
    }
}
