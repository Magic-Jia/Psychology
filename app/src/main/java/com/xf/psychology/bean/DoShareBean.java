package com.xf.psychology.bean;

import java.io.Serializable;
import java.util.List;


public class DoShareBean implements Serializable {
    public String authorIcon;
    public int authorId;
    public String authorNickName;
    public String content;
    public String time;
    public boolean isFollow;
    public boolean isLike;
    public int likes;
    public int messages;
    public int shareId;
    public List<String> picPaths;
    public boolean contains(String key){
        return authorNickName.contains(key)||content.contains(key);
    }
    @Override
    public String toString() {
        return "DoShareBean{" +
                "authorIcon='" + authorIcon + '\'' +
                ", author='" + authorId + '\'' +
                ", authorNickName='" + authorNickName + '\'' +
                ", content='" + content + '\'' +
                ", time='" + time + '\'' +
                ", isFollow=" + isFollow +
                ", isLike=" + isLike +
                ", likes=" + likes +
                ", messages=" + messages +
                ", shareId=" + shareId +
                '}';
    }
}
