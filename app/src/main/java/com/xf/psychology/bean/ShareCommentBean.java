package com.xf.psychology.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ShareCommentBean {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int shareId;
    public int initiatorId;
    public String initiatorNickName;
    public String initiatorIcon;
    public String comment;
    public int toId;
    public String toNickName;
    public String time;

}
