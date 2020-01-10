package com.example.imagesynclib;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import com.example.imagesynclib.Database.Local.AppDatabase;
import com.example.imagesynclib.Database.Local.DatabaseClient;
import com.example.imagesynclib.Database.Local.mGeneric;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class BackgroundImageUploader<T> {

    private static String CHANNEL_ID = "IMAGE_SYNC_NOTIFICATION";

    public abstract String getUrl();

    public abstract int NoOfRetry();

    public abstract ArrayList<String> allowedStatusCodes();

    public static String getAction(){
        return "BackgroundImageUploader_Intent";
    }

    public static IntentFilter getIntentFilter(){
        return new IntentFilter(getAction());
    }

    //private String url ="http://13.233.157.30:4004/api/v1/gradings/classify_image";

    public void upload(Context context, T model){
        upload(context,model,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE);
    }

    public void upload(Context context, T model,int Group_Id,int Group_Priority,int Item_Priority){

       /* createNotificationChannel(context);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (!notificationManager.areNotificationsEnabled()) {
            openNotificationSettings(context);
            // return;
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
                isChannelBlocked(context,CHANNEL_ID)) {
            openChannelSettings(context,CHANNEL_ID);
            //return;
        }*/

        new Thread(() -> {



            AppDatabase database =
                    DatabaseClient.getInstance(context)
                            .getAppDatabase();

            mGeneric generic = new mGeneric(getUrl(),getJsonStr(model)).setNoOfRetry(NoOfRetry());
            generic.setAllowed_success_code(allowedStatusCodes().size()>0?allowedStatusCodes() : new ArrayList<>( Arrays.asList("200")));
            generic.setGroup_id(Group_Id);
            generic.setGroup_priority(Group_Priority);
            generic.setItem_priority(Item_Priority);
            database.genericDao()
                    .insert(generic);

            SyncImages.scheduleJob(context);


        }).start();
    }


    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Sync Notification";
            String description = "Notifies you whenever the image in queue is uploaded successfully or any other action is to be taken.";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public String getJsonStr(T model){
        Gson g = new Gson();
        return g.toJson(model);
    }

    public  T getObject(String jsonString){
        Gson g = new Gson();
        return  g.fromJson(jsonString, new TypeToken<T>(){}.getType());
    }


    private void openNotificationSettings(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Intent intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
            context.startActivity(intent);
        } else {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            context.startActivity(intent);
        }
    }

    @RequiresApi(26)
    private boolean isChannelBlocked(Context context,String channelId) {
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        NotificationChannel channel = manager.getNotificationChannel(channelId);

        return channel != null &&
                channel.getImportance() == NotificationManager.IMPORTANCE_NONE;
    }

    @RequiresApi(26)
    private void openChannelSettings(Context context,String channelId) {
        Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
        intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
        intent.putExtra(Settings.EXTRA_CHANNEL_ID, channelId);
        context.startActivity(intent);
    }
}
