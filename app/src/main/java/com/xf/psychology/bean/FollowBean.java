package com.xf.psychology.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FollowBean {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int aId;//被关注Id
    public String aNickName;
    public String aIconPath;
    public int bId;//关注Id
    public String bNickName;
    public String bIconPath;
}
