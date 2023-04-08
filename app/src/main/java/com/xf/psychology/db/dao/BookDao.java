package com.xf.psychology.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.xf.psychology.bean.BookBean;

import java.util.List;

@Dao
public interface BookDao {
    @Insert(entity = BookBean.class)
    void insert(BookBean bean);

    @Query("select * from bookbean")
    List<BookBean> queryAll();

    @Query("select * from bookbean where upId=:upId")
    List<BookBean> queryByUpId(int upId);
}
