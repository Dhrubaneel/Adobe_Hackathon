package com.tcs.dhrubaneel.hackathonapp.networkRequest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.tcs.dhrubaneel.hackathonapp.AppController;
import com.tcs.dhrubaneel.hackathonapp.constants.ConstantVariables;
import com.tcs.dhrubaneel.hackathonapp.listners.OnTaskCompleted;
import com.tcs.dhrubaneel.hackathonapp.listners.OnTaskError;

public class ServiceCall {
    private static Context context;
    public ServiceCall(Context context){
        this.context = context;
    }
    RetryPolicy retryPolicy = new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);



    //check Internet connection.
    public static boolean checkInternetConnection(){
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if(isConnected == false){
            Toast.makeText(context,"Please check your internet connection",Toast.LENGTH_LONG).show();
        }

        return isConnected;
    }

    //Function to make service call from network with authentication
    public void loadFromNetworkWithAuth(final String ServiceTag,
                                int RequestType,
                                final String Url,
                                final JSONObject JsonRequest,
                                final OnTaskCompleted callback,
                                final OnTaskError errorCallback){

        JsonObjectRequest jsonObjReq=new JsonObjectRequest(RequestType, Url, JsonRequest,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e(ServiceTag, response.toString());
                        callback.onTaskCompleted(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Toast.makeText(context,"Error: Timeout",Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(context,"Error: AuthFailure",Toast.LENGTH_LONG).show();
                } else if (error instanceof ServerError) {
                    String responseBody = null;
                    try {
                        responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject jsonObject = new JSONObject(responseBody);
                        Log.e("Error-Msg",jsonObject.getString("errorMessage"));
                        Toast.makeText(context, jsonObject.getString("errorMessage"), Toast.LENGTH_LONG).show();
                        errorCallback.onTaskError(jsonObject.getString("errorMessage"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (error instanceof NetworkError) {
                    Toast.makeText(context,"Error: Network issue",Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(context,"Error: Parse Error",Toast.LENGTH_LONG).show();
                }
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", ConstantVariables.auth_token);
                return headers;
            }
        };
        jsonObjReq.setRetryPolicy(retryPolicy);
        if (checkInternetConnection() == true) {
            try{
                AppController.getInstance().addToRequestQueue(jsonObjReq, ServiceTag);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
