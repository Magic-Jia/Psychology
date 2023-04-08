package com.xf.psychology.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.xf.psychology.bean.AnswerBean;
import com.xf.psychology.bean.BookBean;
import com.xf.psychology.bean.ChatBean;
import com.xf.psychology.bean.FMBean;
import com.xf.psychology.bean.FollowBean;
import com.xf.psychology.bean.MessageBeanXF;
import com.xf.psychology.bean.QuestionBean;
import com.xf.psychology.bean.ShareBeanXF;
import com.xf.psychology.bean.ShareCommentBean;
import com.xf.psychology.bean.ShareLikes;
import com.xf.psychology.bean.TestRecordXFBean;
import com.xf.psychology.bean.UserBean;
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


@Database(entities = {
        UserBean.class,
        ShareBeanXF.class,
        TestRecordXFBean.class,
        MessageBeanXF.class,
        ShareCommentBean.class,
        FollowBean.class,
        QuestionBean.class,
        AnswerBean.class,
        FMBean.class,
        BookBean.class,
        ChatBean.class,
        ShareLikes.class
}, version = 1, exportSchema = false)
public abstract class MyDB extends RoomDatabase {
    public abstract UserDao getUserDao();

    public abstract ShareDao getShareDao();

    public abstract TestRecordDao getTestRecordDao();

    public abstract MessageDao getMessageDao();
    public abstract ShareLikesDao getShareLikesDao();
    public abstract ShareCommentDao getShareCommentDao();

    public abstract FollowDao getFollowDao();
    public abstract QuestionDao getQuestionDao();
    public abstract AnswerDao getAnswerDao();

    public abstract FMDao getFMDao();
    public abstract BookDao getBookDao();
    public abstract ChatDao getChatDao();
}
