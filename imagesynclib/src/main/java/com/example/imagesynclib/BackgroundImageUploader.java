package com.example.imagesynclib;

import android.content.Context;
import android.content.IntentFilter;

import com.example.imagesynclib.Database.Local.AppDatabase;
import com.example.imagesynclib.Database.Local.DatabaseClient;
import com.example.imagesynclib.Database.Local.mGeneric;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public abstract class BackgroundImageUploader<T> {

    public abstract String getUrl();

    public abstract int NoOfRetry();

    public static String getAction(){
        return "BackgroundImageUploader_Intent";
    }

    public static IntentFilter getIntentFilter(){
        return new IntentFilter(getAction());
    }

    //private String url ="http://13.233.157.30:4004/api/v1/gradings/classify_image";

    public void upload(Context context, T model){


        new Thread(() -> {

            AppDatabase database =
                    DatabaseClient.getInstance(context)
                            .getAppDatabase();


                database.genericDao()
                        .insert(new mGeneric(getUrl(),getJsonStr(model)).setNoOfRetry(NoOfRetry()));

            SyncImages.scheduleJob(context);


        }).start();
    }

    public String getJsonStr(T model){
        Gson g = new Gson();
        return g.toJson(model);
    }

    public  T getObject(String jsonString){
        Gson g = new Gson();
        return  g.fromJson(jsonString, new TypeToken<T>(){}.getType());
    }
}
