package com.nidhi.as.adapters;

import android.app.Activity;
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
    ArrayList sno;
    ArrayList title;
    ArrayList desc;
    ArrayList expdate;


    public ItemsAdapter(@NonNull Context context, @NonNull ArrayList sno, ArrayList title,
                        ArrayList desc, ArrayList expdate) {

        this.mcontext = context;
        this.sno = sno;
        this.title = title;
        this.desc = desc;
        this.expdate = expdate;

    }

    public ItemsAdapter(Activity activity, TextView sno, TextView tiltle, TextView desc, TextView expdate) {

    }

    public ItemsAdapter(ItemsAdapter itemsAdapter) {
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


        holder.tv_title.setText(title.get(position).toString());
        holder.tv_desc.setText(desc.get(position).toString());
        holder.tv_sno.setText(sno.get(position).toString());
        holder.tv_expdate.setText(expdate.get(position).toString());

        holder.cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext, title.get(position).toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return desc.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        CardView cardview;

        TextView tv_title;
        TextView tv_desc;
        TextView tv_sno;
        TextView tv_expdate;

        public ItemHolder(View itemView) {
            super(itemView);

            cardview = (CardView) itemView.findViewById(R.id.cardview);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_desc = (TextView) itemView.findViewById(R.id.tv_desc);
            tv_sno = (TextView) itemView.findViewById(R.id.tv_sno);
            tv_expdate = (TextView) itemView.findViewById(R.id.tv_expdate);


        }
    }
}

