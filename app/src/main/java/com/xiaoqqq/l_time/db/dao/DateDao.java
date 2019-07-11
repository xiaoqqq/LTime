package com.xiaoqqq.l_time.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.xiaoqqq.l_time.bean.DateBean;

import java.util.List;

/**
 * @author xiaoqqq
 * @package com.xiaoqqq.l_time.db.dao
 * @date 2019-07-11
 * @describe todo
 */
@Dao
public interface DateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveDate(DateBean.DataContentBean... contentBeans);

    @Query("select * from date_content order by date_timestamp desc")
    List<DateBean.DataContentBean> queryAllDate();

    @Query("delete from date_content where date_name =:dateName")
    void deleteDateByDateName(String dateName);

    @Query("update date_content set date_name = :newDateName,date_timestamp = :newTimeStamp where date_name = :oldDateName")
    void updateDateByDatename(String oldDateName, String newDateName, String newTimeStamp);
}
