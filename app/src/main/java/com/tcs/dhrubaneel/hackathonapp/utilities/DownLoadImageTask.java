package com.tcs.dhrubaneel.hackathonapp.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.tcs.dhrubaneel.hackathonapp.constants.ConstantVariables;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
    ImageView imageView;

    public DownLoadImageTask(ImageView imageView){
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(String...urls){
        String urlOfImage = urls[0];
        Bitmap logo = null;
        try{
            URL urlConnection = new URL(urlOfImage);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setRequestProperty("Authorization", ConstantVariables.auth_token);
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            logo = BitmapFactory.decodeStream(input);
        }catch(Exception e){ // Catch the download exception
            e.printStackTrace();
        }
        return logo;
    }

    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
    }
}
