package com.xf.psychology.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.xf.psychology.bean.UserBean;

import java.util.List;


@Dao
public interface UserDao {
    @Insert(entity = UserBean.class)
    void registerUser(UserBean user);

    @Query("select * from UserBean")
    List<UserBean> loadAll();

    @Query("select * from UserBean where phone = :phone")
    UserBean queryUserByPhone(String phone);

    @Update
    void updateUser(UserBean user);
}
