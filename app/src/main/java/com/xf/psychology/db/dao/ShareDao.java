package com.xf.psychology.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xf.psychology.bean.ShareBeanXF;

import java.util.List;



@Dao
public interface ShareDao {
    @Query("select * from ShareBeanXF where authorId = :id")
    List<ShareBeanXF> queryByUser(int id);

    @Query("select * from ShareBeanXF where id = :id")
    ShareBeanXF queryById(int id);

    @Query("select * from ShareBeanXF")
    List<ShareBeanXF> queryAll();

    @Update(entity = ShareBeanXF.class)
    void upData(ShareBeanXF shareBeanXF);

    @Insert(entity = ShareBeanXF.class)
    void insert(ShareBeanXF shareBeanXF);

    @Delete(entity = ShareBeanXF.class)
    void delete(ShareBeanXF shareBeanXF);

    @Query("delete from sharebeanxf where id=:id")
    void delete(int id);
}
