package com.example.imagesynclib;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

public class SyncActivity extends AppCompatActivity {

    RecyclerView syncListview;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);

        context = this;




        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        syncListview.setLayoutManager(mLayoutManager);
        syncListview.setItemAnimator(new DefaultItemAnimator());
        //syncListview.setAdapter(adapter);
    }
}
