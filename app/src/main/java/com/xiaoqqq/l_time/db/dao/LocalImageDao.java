package com.xiaoqqq.l_time.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.xiaoqqq.l_time.bean.LocalImageBean;

/**
 * @author xiaoqqq
 * @package com.xiaoqqq.l_time.db.dao
 * @date 2019-07-11
 * @describe todo
 */
@Dao
public interface LocalImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveLocalImage(LocalImageBean... localImageBeans);

    @Query("DELETE from local_image")
    void deleteAll();

    @Query("select * from local_image")
    LocalImageBean queryLocalImage();

}
