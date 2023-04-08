package com.xf.psychology.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatBean {
    @PrimaryKey(autoGenerate = true)
    public int messageId;
    public String message;
    public int sendId;
    public String sendIconPath;
    public String messageTime;
}
