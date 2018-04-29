package com.tcs.dhrubaneel.hackathonapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tcs.dhrubaneel.hackathonapp.R;
import com.tcs.dhrubaneel.hackathonapp.constants.ConstantVariables;
import com.tcs.dhrubaneel.hackathonapp.listners.OnTaskCompleted;
import com.tcs.dhrubaneel.hackathonapp.listners.OnTaskError;
import com.tcs.dhrubaneel.hackathonapp.networkRequest.ServiceCall;
import com.tcs.dhrubaneel.hackathonapp.pojo.serviceOutput.CardDetails;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Splash extends AppCompatActivity {
    private static FragmentActivity activity;
    private static Context context;
    ArrayList<CardDetails> allCardDetails = new ArrayList<>();
    String[] productList = {"gold-card.caas.json", "black-card.caas.json", "blue-card.caas.json"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Splash.context = getApplicationContext();
        activity=this;
        setContentView(R.layout.activity_splash);


        allCardDetails.clear();

        if (getIntent().getBooleanExtra("EXIT", false)) {
            activity.finish();
        }else{
            //by default counter starts from zero
            getAllCardDetails(0);
        }

    }
    //Executes once the activity resumes
    @Override
    protected void onResume() {
        super.onResume();
        // handle the reset state when the activity is resumed
        if (getIntent().getBooleanExtra("EXIT", false)) {
            activity.finish();
        }
    }
    private void getAllCardDetails(int counter){
        //Get all details one after another
        String url = ConstantVariables.baseURL+ConstantVariables.jsonURL+productList[counter];
        ServiceCall sc=new ServiceCall(activity);

        try{
            sc.loadFromNetworkWithAuth(productList[counter]+"_req", Request.Method.GET,url,null,
                    new OnTaskCompleted() {
                        @Override
                        public void onTaskCompleted(JSONObject result) {
                            onDataFetchSuccess(result);
                        }
                    },new OnTaskError() {
                        @Override
                        public void onTaskError(String result) {
                            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void onDataFetchSuccess(JSONObject response){
        try{
            JSONObject data=response;
            if(data.length()>0){
                Gson gson = new GsonBuilder().create();
                CardDetails objCardDetails = gson.fromJson(data.toString(), CardDetails.class);
                allCardDetails.add(objCardDetails);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(allCardDetails.size()==productList.length){
            //Open home page
            Intent i =new Intent(context, HomePage.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra("allCardDetails", allCardDetails);
            context.startActivity(i);
        }else{
            getAllCardDetails(allCardDetails.size());
        }
    }
}
