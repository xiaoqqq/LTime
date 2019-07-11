package com.xiaoqqq.l_time.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * @author create by xiaoqqq 2019.07.11
 */
@Entity(tableName = "local_image")
public class LocalImageBean {
    @PrimaryKey
    @NonNull
    private String imagePath;
    @NonNull
    private String timeStamp;

    @NonNull
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(@NonNull String imagePath) {
        this.imagePath = imagePath;
    }

    @NonNull
    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(@NonNull String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
