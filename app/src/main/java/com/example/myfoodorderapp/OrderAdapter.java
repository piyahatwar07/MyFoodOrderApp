package com.example.myfoodorderapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>{
    ArrayList<OrderModel> list ;
    Context context;

    public OrderAdapter(ArrayList<OrderModel> list,Context context) {
        this.list = list;
        this.context=context;
    }

    @NonNull
    @Override
    public OrderAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout,parent,false);
       return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.MyViewHolder holder, int position) {
        final OrderModel model=list.get(position);
        holder.SoldOrderName.setText(model.getSoldOrderName());
        holder.SoldOrderPrice.setText(model.getPrice());
        holder.SoldOrderNumber.setText(model.getOrderNumber());
        Picasso
                .with(holder.orderImage.getContext())
                .load(model.getOrderImage())
                // .placeholder(R.drawable.vegitarian) // can also be a drawable
                .fit() // will explain later
                .noFade()
                .into(holder.orderImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailActivity.class);
                intent.putExtra("id",Integer.parseInt(model.getOrderNumber()));
                intent.putExtra("type",2);
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete Item")
                        .setMessage("Are you sure to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DBHelper helper=new DBHelper(context);
                                if (helper.deleteOrder(model.getOrderNumber())>0) {
                                    Toast.makeText(context, "Order Deleted", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
      return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView orderImage;
        TextView SoldOrderName;
        TextView SoldOrderPrice;
        TextView SoldOrderNumber;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            orderImage=itemView.findViewById(R.id.OrderImage);
            SoldOrderName=itemView.findViewById(R.id.tvOrderName);
            SoldOrderPrice=itemView.findViewById(R.id.tvOrderPrice);
            SoldOrderNumber=itemView.findViewById(R.id.tvOrderNo);
        }
    }
}
