package com.xf.psychology.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.xf.psychology.bean.FMBean;

import java.util.List;

@Dao
public interface FMDao {
    @Insert(entity = FMBean.class)
    public void insert(FMBean fmBean);

    @Query("select * from fmbean where upId=:upId")
    List<FMBean> queryByUpId(int upId);
    @Query("select * from fmbean where type=:type")
    List<FMBean> queryByType(int type);

    @Query("select * from fmbean")
    List<FMBean> queryAll();

}
