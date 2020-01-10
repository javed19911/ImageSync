package com.example.imagesync;

import com.example.imagesynclib.BackgroundImageUploader;

import java.util.ArrayList;

public class BackgroundImageUpload extends BackgroundImageUploader<mSync> {
    @Override
    public String getUrl() {
        return "http://13.233.157.30:4004/api/v1/gradings/classify_image";
    }

    @Override
    public int NoOfRetry() {
        return 2;
    }

    @Override
    public ArrayList<String> allowedStatusCodes() {
        ArrayList<String> statusCodes = new ArrayList<>();
        statusCodes.add("200");
        return statusCodes;
    }
}
