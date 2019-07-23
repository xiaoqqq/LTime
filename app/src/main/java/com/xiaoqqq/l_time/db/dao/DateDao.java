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
 * @date gift-07-11
 * @describe todo
 */
@Dao
public interface DateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveDate(DateBean.DataContentBean... contentBeans);

    @Query("select * from date_content order by date_timestamp desc")
    List<DateBean.DataContentBean> queryAllDate();

    @Query("select * from date_content order by update_timestamp desc limit 1")
    DateBean.DataContentBean queryDesktopWord();

    @Query("delete from date_content where date_name =:dateName")
    void deleteDateByDateName(String dateName);

    @Query("select * from date_content where date_name =:dateName")
    DateBean.DataContentBean queryDateByDateName(String dateName);

    @Query("update date_content set date_name = :newDateName,date_timestamp = :newTimeStamp,update_timestamp = :updateTimeStamp, " +
            "desktop_word = :desktopWords where date_name = :oldDateName")
    void updateDateByDatename(String oldDateName, String newDateName, String newTimeStamp,
                              String updateTimeStamp, String desktopWords);
}
