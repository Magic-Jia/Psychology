package com.xf.psychology.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AnswerBean {
    @PrimaryKey(autoGenerate = true)
    public int answerId;
    public int questionId;
    public String answer;
    public int answererId;
    public String answererNickName;
    public String answererIconPath;
    public String time;
}
