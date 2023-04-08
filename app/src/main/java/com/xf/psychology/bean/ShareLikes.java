package com.xf.psychology.bean;

import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class ShareLikes {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int shareId;
    public String user;
}
