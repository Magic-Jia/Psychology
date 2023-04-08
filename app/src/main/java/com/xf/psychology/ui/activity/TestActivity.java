package com.xf.psychology.ui.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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
                int totalScore = 0;
                for (int i = 0; i < data.size(); i++) {
                    TestBean questionBean = data.get(i);
                    if (questionBean.score == -1) {
                        toast("第" + (i + 1) + "题未做，请完成");
                    } else {
                        totalScore += questionBean.score;
                    }
                }
                showMsgDialog(totalScore);

            }
        });
    }

    private void showMsgDialog(int totalScore) {
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
        if (totalScore <= 8) {
            msg = "你的心理非常健康，请你放心。";
            cancelView.setText("关闭");
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    toast("测试记录已入库");
                }
            });
        } else if (totalScore <= 16) {
            msg = "你的心理大致还属于健康的范围，但应有所注意，也可以找老师或同学聊聊。";
            cancelView.setText("关闭");
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    toast("测试记录已入库");
                    finish();
                }
            });
        } else if (totalScore <= 30) {
            msg = "你在心理方面有了一些障碍，应采取适当的方法进行调适，或找心理辅导老师帮助你。";
            cancelView.setText("去找老师咨询");
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    finish();
                }
            });
        } else if (totalScore <= 40) {

            msg = "你有可能患了某些心理疾病，应找专门的心理医生进行检查治疗。";
            cancelView.setText("去找老师咨询");
            cancelView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                    finish();
                }
            });
        } else {
            msg = "你有较严重的心理障碍，应及时找专门的心理医生治疗";
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
        data.add(new TestBean("1. 平时不知为什么总觉得心慌意乱，坐立不安。"));
        data.add(new TestBean("2. 上床后，怎么也睡不着，即使睡着也容易惊醒。"));
        data.add(new TestBean("3. 经常做恶梦，惊恐不安，早晨醒来就感到倦怠无力、焦虑烦躁。"));
        data.add(new TestBean("4. 经常早醒1-2小时 ，醒后很难再入睡。"));
        data.add(new TestBean("5. 学习的压力常使自己感到非常烦躁，讨厌学习。"));
        data.add(new TestBean("6. 读书看报甚至在课堂上也不能专心一致，往往自己也搞不清在想什么。"));
        data.add(new TestBean("7. 遇到不称心的事情便较长时间地沉默少言。"));
        data.add(new TestBean("8. 感到很多事情不称心，无端发火。"));
        data.add(new TestBean("9. 哪怕是一件小事情，也总是很放不开，整日思索。"));
        data.add(new TestBean("10. 感到现实生活中没有什么事情能引起自己的乐趣，郁郁寡欢。"));
        data.add(new TestBean("11. 老师讲概念，常常听不懂，有时懂得快忘得也快。"));
        data.add(new TestBean("12. 遇到问题常常举棋不定，迟疑再三。"));
        data.add(new TestBean("13. 经常与人争吵发火，过后又后悔不已。"));
        data.add(new TestBean("14. 经常追悔自己做过的事，有负疚感。"));
        data.add(new TestBean("15. 一遇到考试，即使有准备也紧张焦虑。"));
        data.add(new TestBean("16. 一遇挫折，便心灰意冷，丧失信心。"));
        data.add(new TestBean("17. 非常害怕失败，行动前总是提心吊胆，畏首畏尾。"));
        data.add(new TestBean("18. 感情脆弱，稍不顺心，就暗自流泪。"));
        data.add(new TestBean("19. 自己瞧不起自己，觉得别人总在嘲笑自己。"));
        data.add(new TestBean("20. 喜欢跟自己年幼或能力不如自己的人一起玩或比赛。"));
        data.add(new TestBean("21. 感到没有人理解自己，烦闷时别人很难使自己高兴。"));
        data.add(new TestBean("22. 发现别人在窃窃私语，便怀疑是在背后议论自己。"));
        data.add(new TestBean("23. 对别人取得的成绩和荣誉常常表示怀疑，甚至嫉妒。"));
        data.add(new TestBean("24. 缺乏安全感，总觉得别人要加害自己。"));
        data.add(new TestBean("25. 参加春游等集体活动时，总有孤独感。"));
        data.add(new TestBean("26. 害怕见陌生人，人多时说话就脸红。"));
        data.add(new TestBean("27. 在黑夜行走或独自在家有恐惧感。"));
        data.add(new TestBean("28. 一旦离开父母，心里就不踏实。"));
        data.add(new TestBean("29. 经常怀疑自己接触的东西不干净，反复洗手或换衣服，对清洁极端注意。"));
        data.add(new TestBean("30. 担心是否锁门和可能着火，反复检查，经常躺在床上又起来确认，或刚一出门又返回检查。"));
        data.add(new TestBean("31. 站在经常有人自杀的场所、悬崖边、大厦顶、阳台上，有摇摇晃晃要跳下去的感觉。"));
        data.add(new TestBean("32. 对他人的疾病非常敏感，经常打听，深怕自己也身患同病。"));
        data.add(new TestBean("33. 对特定的事物、交通工具（电车、公共汽车等）、尖状物及白色墙壁等稍微奇怪的东西有恐怖倾向。"));
        data.add(new TestBean("34. 经常怀疑自己发育不良。"));
        data.add(new TestBean("35. 一旦与异XXXXX往就脸红心慌或想入非非。"));
        data.add(new TestBean("36. 对某个异性伙伴的每一个细微行为都很注意。"));
        data.add(new TestBean("37. 怀疑自己患了癌症等严重不治之症，反复看医书或去医院检查。"));
        data.add(new TestBean("38. 经常无端头痛，并依赖止痛或镇静药。"));
        data.add(new TestBean("39. 经常有离家出走或脱离集体的想法。"));
        data.add(new TestBean("40. 感到内心痛苦无法解脱，只能自伤或自杀。"));
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
