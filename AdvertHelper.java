package com.example.task_71p;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task_71p.model.Items;

import java.util.ArrayList;

public class AdvertHelper extends RecyclerView.Adapter<AdvertHelper.AdvertsViewHolder> {

    Context context;
    ArrayList<Items> AdvertsList;
    ItemClickListener ClickListener;

    public AdvertHelper(@NonNull Context context, ArrayList<Items> AdvertsList, ItemClickListener ClickListener) {
        this.context = context;
        this.AdvertsList = AdvertsList;
        this.ClickListener = ClickListener;
    }

    @NonNull
    @Override
    public AdvertsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View ItemView = LayoutInflater.from(context).inflate(R.layout.advert, parent, false);
        return new AdvertsViewHolder(ItemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertsViewHolder holder, int position) {
        holder.LabelAdvertListing.setText(AdvertsList.get(position).getLostOrFound()+" "+ AdvertsList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickListener.onItemClick(AdvertsList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return AdvertsList.size();
    }

    public class AdvertsViewHolder extends RecyclerView.ViewHolder {
        TextView LabelAdvertListing;
        public AdvertsViewHolder(@NonNull View itemView) {
            super(itemView);
            LabelAdvertListing =itemView.findViewById(R.id.advertList);
        }
    }
    public interface ItemClickListener{
        public void onItemClick(Items items);
    }
}
