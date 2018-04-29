package com.tcs.dhrubaneel.hackathonapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.tcs.dhrubaneel.hackathonapp.R;
import com.tcs.dhrubaneel.hackathonapp.adapters.ProductAdapter;
import com.tcs.dhrubaneel.hackathonapp.pojo.serviceOutput.CardDetails;

import java.util.ArrayList;
import java.util.Arrays;

public class HomePage extends AppCompatActivity {

    private static FragmentActivity activity;
    private static Context context;
    ArrayList<CardDetails> allCardDetails = new ArrayList<>();
    private static ListView productList;
    private boolean doubleBackToExitPressedOnce = false;
    private Toast mExitToast=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home_page);

        HomePage.context = getApplicationContext();
        activity=this;

        mExitToast=Toast.makeText(getApplicationContext(), "Double back press to close application.", Toast.LENGTH_SHORT);

        productList = (ListView)findViewById(R.id.product_list);

        Intent intent = getIntent();
        allCardDetails = (ArrayList<CardDetails>)intent.getSerializableExtra("allCardDetails");

        Resources res = activity.getResources();
        productList.setVisibility(View.VISIBLE);
        ProductAdapter adapter = new ProductAdapter(activity,allCardDetails,res);
        productList.setAdapter(adapter);
    }
    //Executes once the activity resumes
    @Override
    protected void onResume() {
        super.onResume();
        // handle the reset state when the activity is resumed
        this.doubleBackToExitPressedOnce = false;
    }

    //if back button clicked twice then app should close
    @Override
    public void onBackPressed() {

        int count = activity.getSupportFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            if (doubleBackToExitPressedOnce) {

                mExitToast.cancel();
                logout();
            }
            this.doubleBackToExitPressedOnce = true;
            mExitToast.show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            activity.getSupportFragmentManager().popBackStack();
        }
    }

    //logout user
    public void logout(){
        Intent intent = new Intent(getApplicationContext(), Splash.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("EXIT", true);
        startActivity(intent);
    }
}
