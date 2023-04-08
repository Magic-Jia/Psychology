package com.xf.psychology.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.xf.psychology.bean.AnswerBean;
import com.xf.psychology.bean.QuestionBean;

import java.util.List;

@Dao
public interface AnswerDao {
    @Insert(entity = AnswerBean.class)
    public void insert(AnswerBean answerBean);

    @Query("select * from answerbean where answererId=:answererId")
    List<AnswerBean> queryByRaiserId(int answererId);

    @Query("select * from answerbean where questionId=:questionId")
    List<AnswerBean> queryByQuestionId(int questionId);

}
