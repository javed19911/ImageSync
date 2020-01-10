package com.example.imagesync;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.imagesync.Utils.MyImageView;
import com.example.imagesynclib.Database.Local.Converters;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    MyImageView attachment;
    Button sync;
    BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        attachment = findViewById(R.id.attachment);
        sync = findViewById(R.id.sync);

        attachment.setListener(new MyImageView.iMyImageView() {
            @Override
            public void OnAddClicked() {
                attachment.addAttachment(MainActivity.this);
            }

            @Override
            public void OnAdded() {
                OnUpdated(attachment.getDataList());
            }

            @Override
            public void OnDeleted(String file) {
                OnUpdated(attachment.getDataList());
            }

            @Override
            public void OnUpdated(ArrayList<String> files) {
//                new Thread(() -> {
//
////                    AppDatabase database =
////                    DatabaseClient.getInstance(MainActivity.this)
////                            .getAppDatabase();
////                    mSyncImage syncImage = new mSyncImage(files.get(0));
////                    if (database.syncDAO().getItemByFile(syncImage.getFile()).size() == 0){
////                        database.syncDAO()
////                                .insert(syncImage);
//////                        String url ="http://13.233.157.30:4004/api/v1/gradings/classify_image";
//////                        Gson g = new Gson();
//////                        database.genericDao()
//////                                .insert(new mGeneric(url,g.toJson(syncImage)));
//                    }else{
////                        database.syncDAO()
////                                .update(syncImage);
//                    }
//
//                }).start();

            }
        });

        Converters.fromArrayLisr(new ArrayList<>(Arrays.asList("200")));

        new getFiles().execute();



        sync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mSync sync = new mSync();
                sync.setEmail("internal@intellolabs.com"); // "internal@intellolabs.com"
                sync.setToken("1ZW7GCtzcmmCp7zYA67s"); //"1ZW7GCtzcmmCp7zYA67s"
                sync.setFile(attachment.getDataList().get(0));

                new BackgroundImageUpload().upload(MainActivity.this,sync);
            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d("Reciever"," onRecieve"); //do something with intent
                Toast.makeText(MainActivity.this,intent.getStringExtra("data"),Toast.LENGTH_LONG).show();
            }
        };
        LocalBroadcastManager.getInstance(MainActivity.this)
                .registerReceiver(mReceiver, BackgroundImageUpload.getIntentFilter());
    }

    @Override
    protected void onPause() {
        if(mReceiver != null) {
            LocalBroadcastManager.getInstance(MainActivity.this)
                    .unregisterReceiver(mReceiver);
            mReceiver = null;
        }
        super.onPause();
    }


    protected void onActivityResult(int reqcode, int rescode, Intent iob) {


        switch (reqcode) {

            case MyImageView.REQUEST_CAMERA:
                if (rescode == RESULT_OK) {
                    attachment.onActivityResult(reqcode, rescode, iob);
                }
                break;

            default:
                super.onActivityResult(reqcode, rescode, iob);

        }
    }


    class getFiles extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
           /* AppDatabase database =
                    DatabaseClient.getInstance(MainActivity.this)
                            .getAppDatabase();
            List<mSyncImage> syncImages = database.syncDAO().getAll();

            List<mGeneric> generics = database.genericDao().getAll();

            StringBuilder files = new StringBuilder();

            for (mSyncImage image: syncImages){
                files.append(image.getFile()).append("|^");
            }*/
            return "/storage/emulated/0/CBO/Intello_attach_1575870876457.jpg";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            attachment.setAttachment(s);
        }
    }
}
