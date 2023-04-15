package com.xf.psychology.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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
            db = Room.databaseBuilder(App.getContext(),
                    MyDB.class,
                    "psychology_db_xf")
                    .allowMainThreadQueries()
                    .build();
        }
    }

    public static synchronized MyDB getInstance(Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context.getApplicationContext(),
                            MyDB.class, "psychology_db_xf")
                    .addMigrations(MIGRATION_1_2)
                    .build();
        }
        return db;
    }
    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            // 创建新表
            database.execSQL("CREATE TABLE new_chatbean (messageId INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT, sendId INTEGER, sendIconPath TEXT, messageTime TEXT, isLeft INTEGER, catchId INTEGER, catchIconPath TEXT)");
            // 迁移旧表数据到新表
            database.execSQL("INSERT INTO new_chatbean (messageId, message, sendId, sendIconPath, messageTime, isLeft, catchId, catchIconPath) SELECT messageId, message, sendId, sendIconPath, messageTime, (CASE WHEN isLeft THEN 1 ELSE 0 END), catchId, catchIconPath FROM ChatBean");
            // 删除旧表
            database.execSQL("DROP TABLE ChatBean");
            // 重命名新表为旧表名称
            database.execSQL("ALTER TABLE new_chatbean RENAME TO ChatBean");
        }
    };

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
    public static FollowDao getFollowDao() { return db.getFollowDao(); }
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
