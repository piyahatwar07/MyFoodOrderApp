package com.example.myfoodorderapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DesertMenuListAdapter extends RecyclerView.Adapter<DesertMenuListAdapter.MyViewHolder> {
    List<MenuList> menuList;
    Context context;

    public DesertMenuListAdapter(List<MenuList> menuList, Context context) {
        this.menuList = menuList;
        this.context = context;
    }

    @NonNull
    @Override
    public DesertMenuListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DesertMenuListAdapter.MyViewHolder holder, int position) {
        final MenuList model=menuList.get(position);
        holder.menuName.setText(model.getName());
        holder.menuPrice.setText(model.getPrice());
        Picasso
                .with(holder.menuImage.getContext())
                .load(model.getImageUrl())
                // .placeholder(R.drawable.vegitarian) // can also be a drawable
                .fit() // will explain later
                .noFade()
                .into(holder.menuImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailActivity.class);
                intent.putExtra("image",model.getImageUrl());
                intent.putExtra("price", model.getPrice());
                intent.putExtra("name", model.getName());
                intent.putExtra("type", 1);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView menuName;
        TextView  menuPrice;
        TextView  addToCartButton;
        ImageView menuImage;
        ImageView imageMinus;
        ImageView imageAddOne;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            menuName=itemView.findViewById(R.id.tvName);
            menuPrice=itemView.findViewById(R.id.tvPrice);
            //  addToCartButton=itemView.findViewById(R.id.btnAdd);
            menuImage=itemView.findViewById(R.id.imageView);
            // imageMinus=itemView.findViewById(R.id.Minus_btn);
            // imageAddOne=itemView.findViewById(R.id.Plus_btn);
        }
    }
}
