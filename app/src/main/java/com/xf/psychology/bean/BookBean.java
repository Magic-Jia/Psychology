package com.xf.psychology.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class BookBean {
    @PrimaryKey(autoGenerate = true)
    public int bookId;
    public String upName;
    public int upId;
    public String author;
    public String whyWant;
    public String bookName;
    public String facePicPath;

    public boolean contains(String key) {
        return upName.contains(key) || whyWant.contains(key) || bookName.contains(key) || author.contains(key);
    }
}
