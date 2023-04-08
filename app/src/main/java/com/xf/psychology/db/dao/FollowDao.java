package com.xf.psychology.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;

import com.xf.psychology.bean.FollowBean;

import java.util.List;
@Dao
public interface FollowDao {
    @Query("select * from FollowBean where aId =:id")
    List<FollowBean> queryFollowByAId(int id);

    @Query("select * from FollowBean where bId =:id")
    List<FollowBean> queryFollowByBId(int id);

    @Query("select * from FollowBean where aId =:aid and bId=:bid")
    FollowBean queryFollowByABId(int aid, int bid);

    @Delete(entity = FollowBean.class)
    void del(FollowBean followBean);

    @Insert(entity = FollowBean.class)
    void insert(FollowBean followBean);
}
