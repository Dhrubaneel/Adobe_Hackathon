package com.tcs.dhrubaneel.hackathonapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.tcs.dhrubaneel.hackathonapp.R;
import com.tcs.dhrubaneel.hackathonapp.constants.ConstantVariables;
import com.tcs.dhrubaneel.hackathonapp.pojo.serviceOutput.CardDetails;
import com.tcs.dhrubaneel.hackathonapp.utilities.DownLoadImageTask;

public class Details extends AppCompatActivity {
    private static FragmentActivity activity;
    private static Context context;
    private CardDetails objCardDetails;

    private ImageView productImage;
    private TextView productTitle;
    private TextView productType;
    private TextView productPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_details);

        Details.context = getApplicationContext();
        activity=this;

        productImage = (ImageView)findViewById(R.id.product_details_image);
        productTitle = (TextView) findViewById(R.id.product_details_title);
        productType = (TextView) findViewById(R.id.product_details_type);
        productPrice = (TextView) findViewById(R.id.product_details_price);

        Intent intent = getIntent();
        objCardDetails = (CardDetails)intent.getSerializableExtra("CardDetails");

        productTitle.setText(objCardDetails.getTitle());
        productType.setText(objCardDetails.getProductType());
        productPrice.setText("Buy for "+objCardDetails.getContent().getPrice());
        String imgURL  = ConstantVariables.baseURL+objCardDetails.getContent().getImgSrc().getPath();
        new DownLoadImageTask(productImage).execute(imgURL);
    }

    @Override
    public void onBackPressed() {
        activity.finish();
    }
}
