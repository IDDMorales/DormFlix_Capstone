package com.example.dormflixv2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class adaptDorm extends RecyclerView.Adapter<adaptDorm.MyViewHolder> {
    Context context;
    ArrayList<dorms> Dorms;
    public adaptDorm(Context context, ArrayList<dorms> Dorms){
        this.context = context;
        this.Dorms = Dorms;
    }
    @NonNull
    @Override
    public adaptDorm.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new adaptDorm.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adaptDorm.MyViewHolder holder, int position) {
        holder.doName.setText(Dorms.get(position).getDormName());
        holder.doPlace.setText(Dorms.get(position).getDormPlace());
        holder.doPrice.setText(Dorms.get(position).getDormPrice());
        holder.imgView.setImageResource(Dorms.get(position).getImg());
    }

    @Override
    public int getItemCount() {
        return Dorms.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView;
        TextView doName, doPlace,doPrice;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgView = itemView.findViewById(R.id.imgHold);
            doName = itemView.findViewById(R.id.dName);
            doPlace = itemView.findViewById(R.id.dPlace);
            doPrice = itemView.findViewById(R.id.dPrice);

        }
    }


}
