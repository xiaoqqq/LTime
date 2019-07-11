package com.xiaoqqq.l_time.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.xiaoqqq.l_time.LtApp;
import com.xiaoqqq.l_time.bean.DateBean;
import com.xiaoqqq.l_time.db.dao.DateDao;

/**
 * @author xiaoqqq
 * @package com.xiaoqqq.l_time.db
 * @date 2019-07-11
 * @describe todo
 */
@Database(entities = {DateBean.DataContentBean.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DateDao dateDao();


    private static AppDatabase appDataBase;

    public synchronized static AppDatabase getInstance() {
        if (appDataBase == null || !appDataBase.isOpen()) {
            open();
        }
        return appDataBase;
    }

    public static void release() {
        if (null != appDataBase && appDataBase.isOpen()) {
            appDataBase.close();
            appDataBase = null;
        }
    }

    public synchronized static void open() {
        release();
        try {
            appDataBase = Room.databaseBuilder(LtApp.getInstance(), AppDatabase.class, "ltime.db")
                    .allowMainThreadQueries()
                    .build();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
