package com.xf.psychology.bean;


public class TestBean {
    public TestBean(String question) {
        this.question = question;
    }
    public String question;
    public int score = -1;
    @Override
    public String toString() {
        return "TestBean{" +
                "question='" + question + '\'' +
                ", score=" + score +
                '}';
    }

}
