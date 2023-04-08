package com.xf.psychology.bean;

import java.io.Serializable;

public class QuestionShowBean implements Serializable {
    public int questionId;
    public int raiserId;
    public String raiserNickName;
    public String raiserIcon;
    public String question;
    public String detail;
    public String time;
    public String firstAnswer;
    public boolean isFollowed;

    public boolean contains(String key) {
        return raiserNickName.contains(key) || question.contains(key) || detail.contains(key);
    }
    @Override
    public String toString() {
        return "QuestionShowBean{" +
                "questionId=" + questionId +
                ", raiserId=" + raiserId +
                ", raiserNickName='" + raiserNickName + '\'' +
                ", raiserIcon='" + raiserIcon + '\'' +
                ", question='" + question + '\'' +
                ", detail='" + detail + '\'' +
                ", time='" + time + '\'' +
                ", firstAnswer='" + firstAnswer + '\'' +
                ", isFollowed=" + isFollowed +
                '}';
    }
}
