package com.xf.psychology.bean;


public class TestBean {
    public TestBean(String question,String A,String B,String C,String D,String E) {
        this.question = question;
        this.A = A;
        this.B = B;
        this.C = C;
        this.D = D;
        this.E = E;
    }
    public String question,A,B,C,D,E;
    public int score = -1;
    @Override
    public String toString() {
        return "TestBean{" +
                "question='" + question + '\'' +
                ", score=" + score +
                '}';
    }

}
