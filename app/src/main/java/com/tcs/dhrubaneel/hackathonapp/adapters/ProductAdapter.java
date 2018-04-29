package com.tcs.dhrubaneel.hackathonapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcs.dhrubaneel.hackathonapp.R;
import com.tcs.dhrubaneel.hackathonapp.activities.Details;
import com.tcs.dhrubaneel.hackathonapp.activities.HomePage;
import com.tcs.dhrubaneel.hackathonapp.constants.ConstantVariables;
import com.tcs.dhrubaneel.hackathonapp.pojo.serviceOutput.CardDetails;
import com.tcs.dhrubaneel.hackathonapp.utilities.DownLoadImageTask;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter implements View.OnClickListener{
    /*********** Declare Used Variables *********/
    private FragmentActivity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
    public Resources res;
    public CardDetails objCardDetails = null;

    /*************  CustomAdapter Constructor *****************/
    public ProductAdapter(Activity a, ArrayList d, Resources resLocal) {


        /********** Take passed values **********/
        activity = (FragmentActivity)a;
        data=d;
        res = resLocal;

        /***********  Layout inflator to call external xml layout () ***********/
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    /******** What is the size of Passed Arraylist Size ************/
    public int getCount() {

        if(data.size()<=0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    /********* Create a holder Class to contain inflated xml file elements *********/
    public static class ViewHolder{
        public TextView product_title;
        public TextView product_price;
        public ImageView productImage;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        ViewHolder holder;

        if (convertView == null) {

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.product_adapter, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.product_title=(TextView) vi.findViewById(R.id.product_title);
            holder.product_price=(TextView) vi.findViewById(R.id.product_price);
            holder.productImage=(ImageView) vi.findViewById(R.id.product_image);


            /************  Set holder with LayoutInflater ************/
            vi.setTag(holder);
        }else
            holder = (ViewHolder) vi.getTag();

        if (data.size() <= 0) {
            //hide the list

        } else {
            /***** Get each Model object from ArrayList ********/
            objCardDetails = null;
            objCardDetails = (CardDetails) data.get(position);

            /************  Set Model values in Holder elements ***********/
            holder.product_title.setText(objCardDetails.getTitle());
            holder.product_price.setText(objCardDetails.getContent().getPrice());

            String imgURL  = ConstantVariables.baseURL+objCardDetails.getContent().getImgSrc().getPath();
            new DownLoadImageTask(holder.productImage).execute(imgURL);

        }

        holder.productImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                CardDetails cd = (CardDetails)data.get(position);
                Intent i =new Intent(activity, Details.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("CardDetails", cd);
                activity.startActivity(i);
            }
        });

        return vi;
    }

    @Override
    public void onClick(View v) {
        Log.v("Product Adapter", "=====Row button clicked=====");
    }
}
