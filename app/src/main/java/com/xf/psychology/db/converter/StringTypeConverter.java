package com.xf.psychology.db.converter;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;


public class StringTypeConverter {
    Gson gson = new Gson();

    @TypeConverter
    public List<String> toList(String data) {
        if (data == null || data.equals("")) {
            return Collections.EMPTY_LIST;
        }
        Type type = new TypeToken<List<String>>() {
        }.getType();
        return gson.fromJson(data, type);
    }

    @TypeConverter
    public String fromList(List<String> data) {
        return gson.toJson(data);
    }
}
