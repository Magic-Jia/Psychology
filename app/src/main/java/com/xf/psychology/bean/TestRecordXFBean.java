package com.xf.psychology.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TestRecordXFBean {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String warningTips;
    public String phone;
    public int score;
    public String time;
    public String name;
}
