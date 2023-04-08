package com.xf.psychology.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity
public class MessageBeanXF implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String teacher;
    public String teacherName;
    public String student;
    public String studentName;
    public String question;
    public String answer;
    public String questionTime;
    public String answerTime;
    public int status; //0未回复 1已回复 ，约谈 2同意 3拒绝 4带同意
    public int type;// 0 咨询，1约谈
}
