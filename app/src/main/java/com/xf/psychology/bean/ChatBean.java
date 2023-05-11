package com.xf.psychology.bean;

import androidx.room.Entity;
public class ChatBean {
    private int id;
    private int messageId;//消息编号
    private boolean isLeft;//消息在左边还是右边
    private String message;//消息信息
    private int sendId;//发送者id
    private int catchId;//接收者id
    private String sendIconPath;//发送者头像路径
    private String catchIconPath;//接收者头像路径
    private String messageTime;//发送时间

    public ChatBean(boolean isLeft, String message, int sendId, int catchId, String sendIconPath, String catchIconPath, String messageTime) {
        this.isLeft = isLeft;
        this.message = message;
        this.sendId = sendId;
        this.catchId = catchId;
        this.sendIconPath = sendIconPath;
        this.catchIconPath = catchIconPath;
        this.messageTime = messageTime;
    }
    public ChatBean(){}


    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public int getCatchId() {
        return catchId;
    }

    public void setCatchId(int catchId) {
        this.catchId = catchId;
    }

    public String getSendIconPath() {
        return sendIconPath;
    }

    public void setSendIconPath(String sendIconPath) {
        this.sendIconPath = sendIconPath;
    }

    public String getCatchIconPath() {
        return catchIconPath;
    }

    public void setCatchIconPath(String catchIconPath) {
        this.catchIconPath = catchIconPath;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}