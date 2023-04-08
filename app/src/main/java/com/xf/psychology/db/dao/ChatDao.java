package com.xf.psychology.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.xf.psychology.bean.ChatBean;

import java.util.List;

@Dao
public interface ChatDao {
    @Insert
    public void insert(ChatBean chatBean);

    @Query("select * from chatbean where sendId=:sendId")
    List<ChatBean> queryBySendId(int sendId);
}
