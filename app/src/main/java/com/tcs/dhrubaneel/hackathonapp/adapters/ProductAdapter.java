package com.tcs.dhrubaneel.hackathonapp.adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcs.dhrubaneel.hackathonapp.R;
import com.tcs.dhrubaneel.hackathonapp.activities.Details;
import com.tcs.dhrubaneel.hackathonapp.constants.ConstantVariables;
import com.tcs.dhrubaneel.hackathonapp.pojo.serviceOutput.CardDetails;
import com.tcs.dhrubaneel.hackathonapp.utilities.DownLoadImageTask;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private ArrayList mData;
    private LayoutInflater mInflater;
    private FragmentActivity activity;
    public Resources res;

    // data is passed into the constructor
    public ProductAdapter(Activity a, ArrayList d, Resources resLocal) {
        this.mInflater = LayoutInflater.from(a);
        activity = (FragmentActivity)a;
        this.mData = d;
        res = resLocal;
    }

    // inflates the cell layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.product_adapter, parent, false);
        return new ViewHolder(view);
    }

    // binds the data
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CardDetails cd = (CardDetails)mData.get(position);
        holder.product_title.setText(cd.getTitle());
        holder.product_price.setText(cd.getContent().getPrice());

        String imgURL  = ConstantVariables.baseURL+cd.getContent().getImgSrc().getPath();
        new DownLoadImageTask(holder.productImage).execute(imgURL);

        holder.productImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CardDetails objCardDetails = (CardDetails)mData.get(position);
                Intent i =new Intent(activity, Details.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("CardDetails", objCardDetails);
                activity.startActivity(i);
            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView product_title;
        public TextView product_price;
        public ImageView productImage;

        ViewHolder(View itemView) {
            super(itemView);
            product_title=(TextView) itemView.findViewById(R.id.product_title);
            product_price=(TextView) itemView.findViewById(R.id.product_price);
            productImage=(ImageView) itemView.findViewById(R.id.product_image);
        }
    }
}
