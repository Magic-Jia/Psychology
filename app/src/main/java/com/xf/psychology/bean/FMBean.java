package com.xf.psychology.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class FMBean implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int fmId;
    public String faceFilePath;
    public String fmFilePath;
    public String fmAuthor;
    public String fmTitle;
    public String fmSecTitle;
    public String up;
    public int upId;
    public int type;

    public boolean contains(String key) {
        return fmAuthor.contains(key) || fmTitle.contains(key) || fmSecTitle.contains(key) || up.contains(key);
    }
}
