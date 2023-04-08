package com.xf.psychology.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class QuestionBean {
    @PrimaryKey(autoGenerate = true)
    public int questionId;
    public int raiserId;
    public String raiserNickName;
    public String raiserIcon;
    public String question;
    public String detail;
    public String time;
}
