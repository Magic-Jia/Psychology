package com.xf.psychology.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.xf.psychology.bean.TestRecordXFBean;

import java.util.List;


@Dao
public interface TestRecordDao {
    @Query("select * from testrecordxfbean where phone=:phone")
    List<TestRecordXFBean> queryByPhone(String phone);

    @Insert(entity = TestRecordXFBean.class)
    void insert(TestRecordXFBean bean);
}
