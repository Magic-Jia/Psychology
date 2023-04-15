package com.xf.psychology.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatBean {
    @PrimaryKey(autoGenerate = true)
    public int messageId;//消息编号
    public  boolean isLeft;//消息在左边还是右边
    public String message;//消息信息
    public int sendId;//发送者id
    public int catchId;//接收者id
    public String sendIconPath;//发送者头像路径
    public String catchIconPath;//接收者头像路径
    public String messageTime;//发送时间
}
