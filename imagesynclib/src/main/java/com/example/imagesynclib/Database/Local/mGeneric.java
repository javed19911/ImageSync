package com.example.imagesynclib.Database.Local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "GENERIC")
public class mGeneric {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "no_of_retry")
    private int no_of_retry=0;

    @ColumnInfo(name = "no_retryed")
    private int no_retryed=0;

    @ColumnInfo(name = "url")
    private String url="";

    @ColumnInfo(name = "jsonStr")
    private String jsonStr="";

    @ColumnInfo(name = "updated")
    private Boolean updated = false;

    @ColumnInfo(name = "update_time")
    private long update_time = 0l;

    public mGeneric(String url,String jsonStr) {
        this.url = url;
        this.jsonStr = jsonStr;
    }


    ///getter

    public int getId() {
        return id;
    }

    public String getJsonStr() {
        return jsonStr;
    }

    public String getUrl() {
        return url;
    }

    public Boolean getUpdated() {
        return updated;
    }

    public long getUpdate_time() {
        return update_time;
    }


    public int getNo_of_retry() {
        return no_of_retry;
    }

    public int getNo_retryed() {
        return no_retryed;
    }


    ///setter


    public void setId(int id) {
        this.id = id;
    }

    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public mGeneric setNoOfRetry(int noOfRetry) {
        no_of_retry = noOfRetry;
        return this;
    }

    public mGeneric setRetryed(int retryed) {
        no_retryed = retryed;
        return this;
    }

    public void setNo_of_retry(int no_of_retry) {
        this.no_of_retry = no_of_retry;
    }

    public void setNo_retryed(int no_retryed) {
        this.no_retryed = no_retryed;
    }
}
