package com.nidhi.as.fragments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nidhi.as.R;

import java.util.ArrayList;

/**
 * Created by nidhi on 9/1/2017.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ItemHolder> {

    Context mcontext;

    ArrayList titles;
    ArrayList desc;
    int[] images;

    public ItemsAdapter(@NonNull Context context, @NonNull ArrayList titles,
                        ArrayList desc, int[] images) {

        this.mcontext = context;
        this.titles = titles;
        this.desc = desc;
        this.images= images;
    }

    public ItemsAdapter() {

    }


    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //append xml.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_items, parent, false);
        //append layout to viewholder(ItemHolder) to return.
        ItemHolder itemHolder = new ItemHolder(view);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, final int position) {

        holder.imageView.setImageResource(images[position]);

        holder.tv_title.setText(titles.get(position).toString());

        holder.tv_desc.setText(desc.get(position).toString());

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, titles.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return desc.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        CardView cardview;
        ImageView imageView;
        TextView tv_title, tv_desc;

        public ItemHolder(View itemView) {
            super(itemView);

            cardview = (CardView) itemView.findViewById(R.id.cardview);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);

        }
    }
}

