package com.xf.psychology.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xf.psychology.bean.MessageBeanXF;

import java.util.List;


@Dao
public interface MessageDao {
    @Query("select * from MessageBeanXF where teacher =:to")
    List<MessageBeanXF> queryByTeacher(String to);

    @Query("select * from MessageBeanXF where student =:student")
    List<MessageBeanXF> queryByStudent(String student);

    @Update(entity = MessageBeanXF.class)
    void update(MessageBeanXF messageBeanXF);

    @Insert(entity = MessageBeanXF.class)
    void insert(MessageBeanXF messageBeanXF);


}
