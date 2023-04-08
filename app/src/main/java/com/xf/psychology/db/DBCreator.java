package com.xf.psychology.db;

import androidx.room.Room;

import com.xf.psychology.App;
import com.xf.psychology.bean.ChatBean;
import com.xf.psychology.db.dao.AnswerDao;
import com.xf.psychology.db.dao.BookDao;
import com.xf.psychology.db.dao.ChatDao;
import com.xf.psychology.db.dao.FMDao;
import com.xf.psychology.db.dao.FollowDao;
import com.xf.psychology.db.dao.MessageDao;
import com.xf.psychology.db.dao.QuestionDao;
import com.xf.psychology.db.dao.ShareCommentDao;
import com.xf.psychology.db.dao.ShareDao;
import com.xf.psychology.db.dao.ShareLikesDao;
import com.xf.psychology.db.dao.TestRecordDao;
import com.xf.psychology.db.dao.UserDao;


public class DBCreator {
    private static MyDB db;
    public static void init() {
        if (db == null) {
            db = Room.databaseBuilder(App.getContext(), MyDB.class, "psychology_db_xf").allowMainThreadQueries().build();
        }
    }

    public static UserDao getUserDao() {
        return db.getUserDao();
    }

    public static ShareDao getShareDao() {
        return db.getShareDao();
    }
    public static TestRecordDao getTestRecordDao() {
        return db.getTestRecordDao();
    }
    public static MessageDao getMessageDao() {
        return db.getMessageDao();
    }
    public static ShareLikesDao getShareLikesDao() {
        return db.getShareLikesDao();
    }
    public static ShareCommentDao getShareCommentDao() {
        return db.getShareCommentDao();
    }
    public static FollowDao getFollowDao() {
        return db.getFollowDao();
    }
    public static QuestionDao getQuestionDao() {
        return db.getQuestionDao();
    }
    public static AnswerDao getAnswerDao() {
        return db.getAnswerDao();
    }
    public static FMDao getFMDao() {
        return db.getFMDao();
    }
    public static BookDao getBookDao() {
        return db.getBookDao();
    }
    public static ChatDao getChatDao() {
        return db.getChatDao();
    }
}
