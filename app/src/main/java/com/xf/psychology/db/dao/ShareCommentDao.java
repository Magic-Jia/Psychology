package com.xf.psychology.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xf.psychology.bean.ShareBeanXF;
import com.xf.psychology.bean.ShareCommentBean;

import java.util.List;

@Dao
public interface ShareCommentDao {

    @Query("select * from sharecommentbean where shareId = :id")
    List<ShareCommentBean> queryByShareId(int id);

    @Query("select * from sharecommentbean where initiatorId")
    List<ShareCommentBean> queryByInitiatorId();

    @Update(entity = ShareBeanXF.class)
    void upData(ShareBeanXF shareBeanXF);

    @Insert(entity = ShareCommentBean.class)
    void insert(ShareCommentBean commentBean);

    @Delete(entity = ShareBeanXF.class)
    void delete(ShareBeanXF shareBeanXF);
}
