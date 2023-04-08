package com.xf.psychology.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.xf.psychology.bean.ShareLikes;

import java.util.List;


@Dao
public interface ShareLikesDao {

    @Query("select * from sharelikes where shareId=:id")
    List<ShareLikes> queryByShareId(int id);

    @Query("select * from sharelikes where shareId=:id and user=:user")
    ShareLikes queryByShareId(int id, String user);

    @Delete(entity = ShareLikes.class)
    void del(ShareLikes shareLikes);
    @Insert(entity = ShareLikes.class)
    void insert(ShareLikes shareLikes);
    @Insert(entity = ShareLikes.class)
    void insert(List<ShareLikes> shareLikes);
}
