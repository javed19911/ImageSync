package com.example.imagesynclib;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.example.imagesynclib.Database.Local.AppDatabase;
import com.example.imagesynclib.Database.Local.DatabaseClient;
import com.example.imagesynclib.Database.Local.mGeneric;
import com.example.imagesynclib.Database.Remote.UploadService;

import java.io.IOException;
import java.util.List;

public class SyncImageJob extends JobService {
    private static final String TAG = "SyncImageJob";
    //private String url ="http://13.233.157.30:4004/api/v1/gradings/classify_image";
    private Boolean IsStoped = false;

    JobParameters params;
    DoItTask doIt;
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG,"Job Started");
        this.params = params;
        doIt = new DoItTask();
        doIt.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG,"Force Stoped");
        IsStoped = true;
        //SyncImages.cancelJob(getApplicationContext());
        //doIt.cancel(true);
        return true;
    }




    private class DoItTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPostExecute(Boolean aVoid) {

            if (aVoid) {
                Log.d(TAG, "Clean up the task here and call jobFinished...");
                jobFinished(params, false);
            }else{
                SyncImages.scheduleJob(getApplicationContext());
                //onStartJob(params);
            }


            super.onPostExecute(aVoid);
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            Log.d(TAG, "Working here...");

            mGeneric generic = getModel();
            if (generic != null){
                try {
                    ///storage/emulated/0/CBO/Intello_attach_1575870876457.jpg

//                    mSync sync = new mSync();
//                    sync.setEmail("internal@intellolabs.com"); // "internal@intellolabs.com"
//                    sync.setToken("1ZW7GCtzcmmCp7zYA67s"); //"1ZW7GCtzcmmCp7zYA67s"
//                    sync.setFile("/storage/emulated/0/CBO/Intello_attach_1575870876457.jpg");

                    //new UploadService(sync);

                    new UploadService(getApplicationContext(),generic);
                } catch (IOException e) {
                    e.printStackTrace();
                    //return false;
                }
            }

           /* for(int i=0;i<100;i++){

                Log.d(TAG,"count : "+ i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }*/
            return  getModel() ==null;
        }
    }

    private mGeneric getModel(){
        AppDatabase database;
        database = DatabaseClient.getInstance(getApplicationContext())
                .getAppDatabase();
        List<mGeneric> generics = database.genericDao().getAll();
        if (generics.size() > 0) {
//            url = generics.get(0).getUrl();
//            Gson gson = new Gson();
//            JsonObject object = gson.fromJson(generics.get(0).getJsonStr(),JsonObject.class);
            return generics.get(0);

        }

       return null;
    }
}
