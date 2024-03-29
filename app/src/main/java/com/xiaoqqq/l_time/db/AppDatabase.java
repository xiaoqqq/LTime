package com.xiaoqqq.l_time.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.xiaoqqq.l_time.LtApp;
import com.xiaoqqq.l_time.bean.DateBean;
import com.xiaoqqq.l_time.bean.DestopBean;
import com.xiaoqqq.l_time.bean.LocalImageBean;
import com.xiaoqqq.l_time.db.dao.DateDao;
import com.xiaoqqq.l_time.db.dao.DesktopShowDao;
import com.xiaoqqq.l_time.db.dao.LocalImageDao;

/**
 * @author xiaoqqq
 * @package com.xiaoqqq.l_time.db
 * @date gift-07-11
 * @describe todo
 */
@Database(entities = {DateBean.DataContentBean.class,
        LocalImageBean.class, DestopBean.class}, version = 5, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DateDao dateDao();

    public abstract LocalImageDao localImageDao();

    public abstract DesktopShowDao desktopShowDao();


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
                    .addMigrations(DatabaseMigration.MIGRATION_1_2)
                    .addMigrations(DatabaseMigration.MIGRATION_2_3)
                    .addMigrations(DatabaseMigration.MIGRATION_3_4)
                    .addMigrations(DatabaseMigration.MIGRATION_4_5)
                    .allowMainThreadQueries()
                    .build();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

}
