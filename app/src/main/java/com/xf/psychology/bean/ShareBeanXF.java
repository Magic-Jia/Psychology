package com.xf.psychology.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.xf.psychology.db.converter.StringTypeConverter;

import java.util.List;

@Entity
public class ShareBeanXF {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int authorId;
    public String authorNickName;
    public String content;
    public String authorIcon;
    @TypeConverters(StringTypeConverter.class)
    public List<String> picPaths;
    public long time;

    @Override
    public String toString() {
        return "ShareBeanXF{" +
                "id=" + id +
                ", author='" + authorId + '\'' +
                ", authorNickName='" + authorNickName + '\'' +
                ", content='" + content + '\'' +
                ", authorIcon='" + authorIcon + '\'' +
                ", time=" + time +
                '}';
    }
}
