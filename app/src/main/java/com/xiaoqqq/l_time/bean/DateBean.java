package com.xiaoqqq.l_time.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.List;

/**
 * @author create by xiaoqqq 2019.07.10
 */

public class DateBean {

    private List<DataContentBean> data_content;

    public List<DataContentBean> getData_content() {
        return data_content;
    }

    public void setData_content(List<DataContentBean> data_content) {
        this.data_content = data_content;
    }

    @Entity(tableName = "date_content")
    public static class DataContentBean {
        /**
         * date_name : name
         * date_timestamp : 2102832411231
         * author : xiaoq
         * show_desktop : 0
         */

        @PrimaryKey
        @NonNull
        private String date_name;
        private String date_timestamp;
        private String author;
        private int show_desktop;

        public String getDate_name() {
            return date_name;
        }

        public void setDate_name(String date_name) {
            this.date_name = date_name;
        }

        public String getDate_timestamp() {
            return date_timestamp;
        }

        public void setDate_timestamp(String date_timestamp) {
            this.date_timestamp = date_timestamp;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getShow_desktop() {
            return show_desktop;
        }

        public void setShow_desktop(int show_desktop) {
            this.show_desktop = show_desktop;
        }
    }
}
