package com.xf.psychology.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.xf.psychology.App;
import com.xf.psychology.R;
import com.xf.psychology.adapter.TestAdapter;
import com.xf.psychology.base.BaseActivity;
import com.xf.psychology.bean.TestBean;
import com.xf.psychology.bean.TestRecordXFBean;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.util.SomeUtil;

import java.util.ArrayList;
import java.util.List;


public class TestActivity extends BaseActivity {
    private RecyclerView questionRecycler;
    private View submit;
    private List<TestBean> data = new ArrayList<>();
    private TestAdapter adapter = new TestAdapter(data);
    private Boolean Complete = true;
    private int Number = 0;
    @Override
    protected void initListener() {
//        评价参考：
//        1) 0-8分。心理非常健康，请你放心。
//        2) 9-16分。大致还属于健康的范围，但应有所注意，也可以找老师
//        或同学聊聊。
//        3) 17-30分。你在心理方面有了一些障碍，应采取适当的方法进行
//        调适，或找心理辅导老师帮助你。
//        4) 31-40分。是黄牌警告，有可能患了某些心理疾病，应找专门的
//        心理医生进行检查治疗。
//        5) 41分以上。有较严重的心理障碍，应及时找专门的心理医生治疗
//        测评方法：
//        ▲得2分，■得1分，★得0分。
//        对以下40道题，如果感到“常常是”，选择▲号；“偶尔”是，选择■号；“完全没有”，选择★号。
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Number = 0;
                int totalScore = 0;
                for (int i = 0; i < data.size(); i++) {
                    TestBean questionBean = data.get(i);
                    if (questionBean.score == -1) {
                        Complete = false;
                        Number++;
                    } else {
                        totalScore += questionBean.score;
                    }
                }
                showMsgDialog(totalScore);
            }
        });
    }

    private void showMsgDialog(int totalScore) {
        if(Number != 0) {
            Toast.makeText(this,"您还有"+ (Number) + "道题没完成，请继续完成",Toast.LENGTH_SHORT).show();
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View inflate = LayoutInflater.from(this).inflate(R.layout.dialog_msg, null, false);
        builder.setView(inflate);
        TextView titleView = inflate.findViewById(R.id.titleView);
        TextView contentView = inflate.findViewById(R.id.contentView);
        TextView cancelView = inflate.findViewById(R.id.cancelView);

        String title = "心理预警提示";
        titleView.setText(title);
        String msg = "";
        AlertDialog alertDialog = builder.create();
        if (totalScore <= 50) {
            msg = "心理状况良好，拥有较好的自我掌控和情绪管理能力，对生活充满信心和积极的态度。";
            cancelView.setText("关闭");
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    toast("测试记录已入库");
                }
            });
        } else if (totalScore <= 80) {
            msg = "心理状况一般，需要进一步关注自身的情绪管理和心理健康，建议寻求专业帮助。";
            cancelView.setText("关闭");
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    toast("测试记录已入库");
                    finish();
                }
            });
        } else if (totalScore <= 120) {
            msg = "心理状况较差，需要及时寻求专业帮助，可能存在情绪失控、焦虑、抑郁等风险。";
            cancelView.setText("去找老师咨询");
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    finish();
                }
            });
        } else if (totalScore <= 160) {
            msg = "心理状况差，需要尽快寻求专业帮助，可能存在较为严重的情绪失控、抑郁、焦虑、创伤后应激障碍等问题";
            cancelView.setText("去找老师咨询");
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    finish();
                }
            });
        } else {
            msg = "心理状况非常差，需要紧急寻求专业帮助，可能存在严重的心理问题，包括但不限于自杀倾向、精神分裂症、人格障碍等。";
            cancelView.setText("去找老师咨询");
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    finish();
                }
            });
        }
        //存入测试记录
        TestRecordXFBean testRecordXFBean = new TestRecordXFBean();
        testRecordXFBean.score = totalScore;
        testRecordXFBean.phone = App.user.phone;
        testRecordXFBean.warningTips = msg;
        testRecordXFBean.time = SomeUtil.getTime();
        testRecordXFBean.name = App.user.name;
        DBCreator.getTestRecordDao().insert(testRecordXFBean);
        contentView.setText(msg);
        alertDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        alertDialog.show();
    }

    @Override
    protected void initData() {
        data.add(new TestBean("1.在过去的一年中，你是否经常感到孤独和无助？","从未感到","偶尔感到","有时感到","经常感到","总是感到"));
        data.add(new TestBean("2. 你觉得自己的性格特点是什么？","开朗","温和","独立","敏感","严谨"));
        data.add(new TestBean("3. 你是否常常感到紧张和焦虑？","从未感到","偶尔感到","有时感到","经常感到","总是感到"));
        data.add(new TestBean("4. 在生活中，你是否喜欢独处？","非常喜欢","比较喜欢","中立","不太喜欢","非常不喜欢"));
        data.add(new TestBean("5. 你是否喜欢结交新朋友？","非常喜欢","比较喜欢","中立","不太喜欢","非常不喜欢"));
        data.add(new TestBean("6. 你认为自己有多乐观？","非常乐观","比较乐观","中立","不太乐观","非常不乐观"));
        data.add(new TestBean("7. 你认为自己有多自信？","非常自信","比较自信","中立","不太自信","非常不自信"));
        data.add(new TestBean("8. 在困难面前，你是否容易放弃？","从不放弃","偶尔放弃","有时放弃","经常放弃","总是放弃"));
        data.add(new TestBean("9. 你是否容易受他人的影响？","从不受影响","偶尔受影响","有时受影响","经常受影响","总是受影响"));
        data.add(new TestBean("10. 你是否喜欢挑战自己？","非常喜欢","比较喜欢","中立","不太喜欢","非常不喜欢"));
        data.add(new TestBean("11. 你是否经常感到疲劳和无力？","从未感到","偶尔感到","有时感到","经常感到","总是感到"));
        data.add(new TestBean("12. 你对未来有什么打算和规划？","有明确的规划","有模糊的规划","没有规划","对未来没有想法","觉得未来充满不确定性"));
        data.add(new TestBean("13. 你是否容易感到沮丧和失落？","从未感到","偶尔感到","有时感到","经常感到","总是感到"));
        data.add(new TestBean("14. 你是否容易和别人产生冲突？","从不产生冲突","偶尔产生冲突","有时产生冲突","经常产生冲突","总是产生冲突"));
        data.add(new TestBean("15. 你是否擅长表达自己的情感？","非常擅长","比较擅长","一般般","不太擅长","非常不擅长"));
        data.add(new TestBean("16. 你认为自己的人际关系良好吗？","非常良好","比较良好","一般般","不太良好","非常不良好"));
        data.add(new TestBean("17. 你是否有强烈的好奇心？","非常强烈","比较强烈","中等程度","不太强烈","非常不强烈"));
        data.add(new TestBean("18. 你是否有独立思考和判断问题的能力？","非常有","比较有","一般般","不太有","非常没有"));
        data.add(new TestBean("19. 在遇到问题时，你会如何处理？","马上解决","思考一会儿","寻求帮助","等待时间解决","放弃解决"));
        data.add(new TestBean("20. 你是否有稳定的情感和情绪？","非常稳定","比较稳定","一般般","不太稳定","非常不稳定"));
        data.add(new TestBean("21. 你是否经常为自己的行为感到后悔？","从未感到","偶尔感到","有时感到","经常感到","总是感到"));
        data.add(new TestBean("22. 你认为自己有多善于沟通？","非常擅长","比较擅长","一般般","不太擅长","非常不擅长"));
        data.add(new TestBean("23. 你是否有自律的习惯？","非常有","比较有","一般般","不太有","非常没有"));
        data.add(new TestBean("24. 你是否容易受到他人的影响？","从不受影响","偶尔受影响","有时受影响","经常受影响","总是受影响"));
        data.add(new TestBean("25. 你是否有兴趣探索新事物？","非常有","比较有","一般般","不太有","非常没有"));
        data.add(new TestBean("26. 你是否乐于接受挑战？","非常乐于接受","比较乐于接受","一般般","不太乐于接受","非常不乐于接受"));
        data.add(new TestBean("27. 你是否经常感到焦虑或紧张？","从未感到","偶尔感到","有时感到","经常感到","总是感到"));
        data.add(new TestBean("28. 你认为自己有多容易接受新观点和思想？","非常容易","比较容易","一般般","不太容易","非常不容易"));
        data.add(new TestBean("29. 你是否有坚定的信念和价值观？","非常有","比较有","一般般","不太有","非常没有"));
        data.add(new TestBean("30. 你是否容易感到孤独？","从未感到","偶尔感到","有时感到","经常感到","总是感到"));
        data.add(new TestBean("31. 你是否经常自我反思和审视自己的行为？","非常经常","比较经常","一般般","不太经常","非常不经常"));
        data.add(new TestBean("32. 你是否喜欢与他人合作？","非常喜欢","比较喜欢","中立","不太喜欢","非常不喜欢"));
        data.add(new TestBean("33. 你是否有足够的自信和自尊心？","非常有","比较有","一般般","不太有","非常没有"));
        data.add(new TestBean("34. 你是否愿意为自己的梦想和目标而努力？","非常愿意","比较愿意","一般般","不太愿意","非常不愿意"));
        data.add(new TestBean("35. 你是否习惯于寻求帮助和支持？","经常寻求","偶尔寻求","有时寻求","不太寻求","从不寻求"));
        data.add(new TestBean("36. 你是否有控制欲或支配欲？","从不有","偶尔有","有时有","经常有","总是有"));
        data.add(new TestBean("37. 你是否对未来充满希望和信心？","非常充满","比较充满","一般般","不太充满","非常没有"));
        data.add(new TestBean("38. 你是否喜欢冒险和探险？","非常喜欢","比较喜欢","中立","不太喜欢","非常不喜欢"));
        data.add(new TestBean("39. 你是否愿意为了自己的利益而与他人竞争？","非常愿意","比较愿意","一般般","不太愿意","非常不愿意"));
        data.add(new TestBean("40. 你是否常常感到无助和无能为力？","从未感到","偶尔感到","有时感到","经常感到","总是感到"));
        questionRecycler.setAdapter(adapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_test;
    }

    @Override
    protected void findViewsById() {
        questionRecycler = findViewById(R.id.questionList);
        submit = findViewById(R.id.submit);
    }
}
