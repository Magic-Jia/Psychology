package com.xf.psychology.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.xf.psychology.bean.QuestionBean;

import java.util.List;

@Dao
public interface QuestionDao {
    @Insert(entity = QuestionBean.class)
    public void insert(QuestionBean questionBean);

    @Query("select * from questionbean where raiserId=:raiserId")
    List<QuestionBean> queryByRaiserId(int raiserId);
    @Query("select * from questionbean")
    List<QuestionBean> queryAll();

}
