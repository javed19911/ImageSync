package com.example.imagesynclib.Database.Remote;

import android.content.Context;
import android.content.Intent;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.imagesynclib.BackgroundImageUploader;
import com.example.imagesynclib.Database.Local.AppDatabase;
import com.example.imagesynclib.Database.Local.DatabaseClient;
import com.example.imagesynclib.Database.Local.mGeneric;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

public class UploadService {

   // private String url ="http://13.233.157.30:4004/api/v1/gradings/classify_image";

    private MultipartUtilityV2 multipartUtilityV2;
    //private MultipartUtility multipartUtility;

    public UploadService(Context context, mGeneric generic) throws IOException  {
        multipartUtilityV2 = new MultipartUtilityV2(generic.getUrl());

        Gson gson = new Gson();
        JsonObject object = gson.fromJson(generic.getJsonStr(),JsonObject.class);
//        Log.d("UploadService", "url : "+ url);
        for (String key : object.keySet()){
//            Log.d("UploadService", "key : "+ key);

            String file = object.get(key).getAsString();

//            Log.d("UploadService", "value : "+ file);

            if (!new File(file).exists()){
                multipartUtilityV2.addFormField(key,object.get(key).getAsString());
            }
        }


        for (String key : object.keySet()){
//            Log.d("UploadService", "key : "+ key);

            String file = object.get(key).getAsString();

//            Log.d("UploadService", "value : "+ file);

            if (new File(file).exists()){
                multipartUtilityV2.addFilePart(key,new File(file));
            }
        }
        String response = multipartUtilityV2.finish();

        if (!response.isEmpty()) {
            AppDatabase database = DatabaseClient.getInstance(context).getAppDatabase();
            try {
                JSONObject object1 = new JSONObject(response);

                if (object1.has("result")){
                    object1 = object1.getJSONObject("result");
                }
                if (object1.has("status_code")){
                    if (object1.getInt("status_code") == 200 ||
                        generic.getNo_retryed() >= generic.getNo_of_retry()){
                        database.genericDao().delete(generic);
                    }else{
                        database.genericDao().update(generic.setRetryed(generic.getNo_retryed()+1));
                    }

                    if (object1.getInt("status_code") == 200){
                        Intent intent = new Intent(BackgroundImageUploader.getAction());
                        intent.putExtra("data", generic.getJsonStr());
                        intent.putExtra("response", response);
                        LocalBroadcastManager.getInstance(context).
                                sendBroadcast(intent);
                    }

                }else{
                    database.genericDao().delete(generic);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                database.genericDao().update(generic.setRetryed(generic.getNo_retryed()+1));
            }






        }

    }

/*

    public UploadService(String filepath) throws IOException  {
        multipartUtilityV2 = new MultipartUtilityV2(url);


        multipartUtilityV2.addFormField("email","internal@intellolabs.com");
        multipartUtilityV2.addFormField("token","1ZW7GCtzcmmCp7zYA67s");
        multipartUtilityV2.addFilePart("file",new File(filepath));
        multipartUtilityV2.finish();

    }


    public UploadService(mSync sync) throws IOException  {
        */
/*multipartUtilityV2 = new MultipartUtilityV2(url);


        multipartUtilityV2.addFormField("email",sync.getEmail());

        multipartUtilityV2.addFilePart("file",new File(sync.getFile()));

        multipartUtilityV2.addFormField("token",sync.getToken());

        multipartUtilityV2.finish();
        *//*


        multipartUtility = new MultipartUtility(url,"UTF-8");


        multipartUtility.addFormField("email",sync.getEmail());

        multipartUtility.addFilePart("file",new File(sync.getFile()));
        multipartUtility.addFormField("token",sync.getToken());



        multipartUtility.finish();

    }
*/






}
