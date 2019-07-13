package com.xiaoqqq.l_time.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.xiaoqqq.l_time.bean.DestopBean;
import com.xiaoqqq.l_time.bean.LocalImageBean;

/**
 * @author xiaoqqq
 * @package com.xiaoqqq.l_time.db.dao
 * @date 2019-07-11
 * @describe todo
 */
@Dao
public interface DesktopShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveDesktop(DestopBean... destopBeans);

    @Query("select * from desktop_show")
    DestopBean queryDesktop();

}
