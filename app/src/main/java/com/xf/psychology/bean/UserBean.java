package com.xf.psychology.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.xf.psychology.db.converter.StringTypeConverter;

import java.io.Serializable;
import java.util.List;

@Entity
public class UserBean implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String phone;
    public String pwd;
    public String name;
    public String iconPath;


    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", pwd='" + pwd + '\'' +
                ", name='" + name + '\'' +
                ", isStudent=" +
                '}';
    }
}
