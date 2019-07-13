package com.xiaoqqq.l_time.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "desktop_show")
public class DestopBean {

    // 0: show home  1: show destop background image
    @PrimaryKey
    @NonNull
    private int show_desktop;

    public int getShow_desktop() {
        return show_desktop;
    }

    public void setShow_desktop(int show_desktop) {
        this.show_desktop = show_desktop;
    }
}
