package com.xf.psychology;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.xf.psychology.bean.AnswerBean;
import com.xf.psychology.bean.BookBean;
import com.xf.psychology.bean.FMBean;
import com.xf.psychology.bean.QuestionBean;
import com.xf.psychology.bean.ShareBeanXF;
import com.xf.psychology.bean.ShareCommentBean;
import com.xf.psychology.bean.UserBean;
import com.xf.psychology.db.DBCreator;
import com.xf.psychology.util.PreferenceUtil;
import com.xf.psychology.util.SomeUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class App extends Application {
    private static Context context;
    public static UserBean user;

    private String luibeIcon = "https://pic1.zhimg.com/v2-3fa13c445bcb89ce3227836a5e9d1ae9_r.jpg";
    private String alShenIcon = "https://img2.woyaogexing.com/2022/03/05/31b9d47de8db49868a51ad4437623142!400x400.jpeg";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        DBCreator.init();
        String phone = PreferenceUtil.getInstance().get("logger", "");
        if (!phone.isEmpty()) {
            user = DBCreator.getUserDao().queryUserByPhone(phone);
        }
        boolean firstIn = PreferenceUtil.getInstance().get("firstIn", true);
        if (firstIn) {
            PreferenceUtil.getInstance().save("firstIn", false);
            UserBean registerUser = new UserBean();
            registerUser.id = 1;
            registerUser.name = "Luibe";
            registerUser.phone = "17505570812";
            registerUser.pwd = "123456";
            registerUser.iconPath = luibeIcon;
            DBCreator.getUserDao().registerUser(registerUser);
            UserBean alShen = new UserBean();
            alShen.name = "AlShen";
            alShen.phone = "18906223695";
            alShen.pwd = "123456";
            alShen.iconPath = alShenIcon;
            DBCreator.getUserDao().registerUser(alShen);

            /*----------------------------动态----------------------------------------*/
            insertShare1();
            insertShare2();
            insertShare3();
            /*----------------------------问答----------------------------------------*/
            insertQuestion1();
            insertQuestion2();
            /*----------------------------好书----------------------------------------*/
            insertBook1();
            insertBook2();
            insertBook3();
            insertBook4();

            /*----------------------------FM----------------------------------------*/
            insertFm1();

            insertFm2();
            insertFm3();
        }

    }

    private void insertFm2() {

        FMBean fmBean = new FMBean();
        fmBean.up = "LuiBe";
        fmBean.type = 1;
        fmBean.upId = 1;
        fmBean.fmAuthor = "立夏";
        fmBean.fmFilePath = "https://freetyst.nf.migu.cn/public%2Fproduct5th%2Fproduct35%2F2019%2F10%2F2116%2F2019%E5%B9%B410%E6%9C%8808%E6%97%A518%E7%82%B907%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E6%AD%A3%E4%B8%9C22%E9%A6%96342231%2F%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC%2FMp3_64_22_16%2F6005662GRNF.mp3?Key=9af3cfbcd84ba8e2&Tim=1646625544549&channelid=01&msisdn=68245049b2454439922108a34286fec0";
        fmBean.faceFilePath = "https://img2.woyaogexing.com/2022/03/05/f928f04b56c94d1ca927c4bb4bcf293e!400x400.jpeg";
        fmBean.fmTitle = "冬日枕边书 | 在复杂的世界里，做一个简单的人";
        fmBean.fmSecTitle = "“我特别喜欢的一个概念是：用生命影响生命。这本书传递了一个新的视角：万物都在见证着万物，只是有时候我们不知道。 这本书每一帧画面背后都是一个能让你展开想象的故事，夏布特的画面充斥着黑和白的纯净孤独感。这种感觉在一个描绘光阴的故事里，让人格外有感触。”";
        DBCreator.getFMDao().insert(fmBean);
    }

    private void insertFm3() {
        FMBean fmBean = new FMBean();
        fmBean.up = "AlShen";
        fmBean.type = 2;
        fmBean.upId = 2;
        fmBean.fmAuthor = "Z先生";
        fmBean.fmFilePath = "https://freetyst.nf.migu.cn/public%2Fproduct5th%2Fproduct35%2F2019%2F10%2F2116%2F2019%E5%B9%B410%E6%9C%8808%E6%97%A518%E7%82%B907%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E6%AD%A3%E4%B8%9C22%E9%A6%96342231%2F%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC%2FMp3_64_22_16%2F6005662GRNF.mp3?Key=9af3cfbcd84ba8e2&Tim=1646625544549&channelid=01&msisdn=68245049b2454439922108a34286fec0";
        fmBean.faceFilePath = "https://img2.woyaogexing.com/2022/02/26/60cbe32a7e45460db52db562898ca0c0!400x400.jpeg";
        fmBean.fmTitle = "为什么要学习理解他人？";
        fmBean.fmSecTitle = "这个世界上有很多加分题：做了会得到更多，不做也不会有别的损失。我们无需只是因为累不累苦不苦而做不做，也无需因为得到的好处多不多而做不做，我们只是去问：性价比如何，值不值。";
        DBCreator.getFMDao().insert(fmBean);
    }

    private void insertFm1() {

        FMBean fmBean = new FMBean();
        fmBean.up = "LuiBe";
        fmBean.type = 1;
        fmBean.upId = 1;
        fmBean.fmAuthor = "曾晓";
        fmBean.fmFilePath = "https://freetyst.nf.migu.cn/public%2Fproduct5th%2Fproduct35%2F2019%2F10%2F2116%2F2019%E5%B9%B410%E6%9C%8808%E6%97%A518%E7%82%B907%E5%88%86%E7%B4%A7%E6%80%A5%E5%86%85%E5%AE%B9%E5%87%86%E5%85%A5%E6%AD%A3%E4%B8%9C22%E9%A6%96342231%2F%E5%85%A8%E6%9B%B2%E8%AF%95%E5%90%AC%2FMp3_64_22_16%2F6005662GRNF.mp3?Key=9af3cfbcd84ba8e2&Tim=1646625544549&channelid=01&msisdn=68245049b2454439922108a34286fec0";
        fmBean.faceFilePath = "https://img2.woyaogexing.com/2022/03/02/76a54ee2439a4ff4ba70d80955f0f845!400x400.jpeg";
        fmBean.fmTitle = "爱自己,在宁静的深处发生 | 晓声聆听";
        fmBean.fmSecTitle = "你可以离自己很近， 穿过所有的情绪， 那些对自己的评判， 他人对待你的方式， 然后， 抵达你自己。 你可以等待夜幕的降临， 适应黑暗的到来， 在宁静的深处， 聆听内心最细微的声音。 你的眼睛会点亮黑夜， 你的心跳会燃烧火焰， 在宁静的深处， 你回应自己。 即使只是等待， 你握紧自己的手， 知道黎明一定会到来。 在宁静的深处， 你与自己赤诚以对。 在宁静的深处， 爱正在发生。 曾晓 2022.2.14";
        DBCreator.getFMDao().insert(fmBean);
    }

    private void insertBook1() {
        BookBean bookBean = new BookBean();
        bookBean.author = "许皓宜";
        bookBean.bookName = "与父母和解";
        bookBean.whyWant = "父母不只给你基因,也给你命运,和记忆里的父母和解";
        bookBean.facePicPath = "https://www.wanwupai.com/upload/product/20190916-1/b81863ed291f9a0a1b8ca97004b64a9c.jpg";
        bookBean.upId = 1;
        bookBean.upName = "Luibe";
        DBCreator.getBookDao().insert(bookBean);
    }

    private void insertBook2() {
        BookBean bookBean = new BookBean();
        bookBean.author = "林然";
        bookBean.bookName = "别想不开，一切都在慢慢变好";
        bookBean.whyWant = "一本足以改变你人生轨迹,时间会消化掉你的情绪";
        bookBean.facePicPath = "https://www.wanwupai.com/upload/product/20190916-1/a150bf35dfd0acbf27a031d938004334.jpg";
        bookBean.upId = 1;
        bookBean.upName = "Luibe";
        DBCreator.getBookDao().insert(bookBean);

    }

    private void insertBook3() {
        BookBean bookBean = new BookBean();
        bookBean.author = "马志国";
        bookBean.bookName = "片刻安静";
        bookBean.whyWant = "安静下来才会有沉淀,让工作生活变得更加轻松";
        bookBean.facePicPath = "https://www.wanwupai.com/upload/product/20190916-1/6aa835d9ae81d141df8a9e7650d55c6e.jpg";
        bookBean.upId = 1;
        bookBean.upName = "Luibe";
        DBCreator.getBookDao().insert(bookBean);
    }

    private void insertBook4() {
        BookBean bookBean = new BookBean();
        bookBean.author = "李洋";
        bookBean.bookName = "罗杰斯心理健康思想解析";
        bookBean.whyWant = "体现实践应用的前景,中外相关心理咨询经典案例";
        bookBean.facePicPath = "https://www.wanwupai.com/upload/product/20190916-1/15f83cdd295ca0a9fd511ebc9e1f649a.jpg";
        bookBean.upId = 2;
        bookBean.upName = "AlShen";
        DBCreator.getBookDao().insert(bookBean);
    }

    private void insertQuestion1() {
        QuestionBean questionBean = new QuestionBean();
        questionBean.question = "假装活得很好，其实活得并不好，有什么方式改变现在？";
        questionBean.raiserIcon = luibeIcon;
        questionBean.raiserNickName = "Luibe";
        questionBean.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        questionBean.raiserId = 1;
        DBCreator.getQuestionDao().insert(questionBean);
        AnswerBean answerBean = new AnswerBean();
        answerBean.answer = "题主您好，我是AlShen。感谢你的自我察觉，在另一方面也说明你也想让自己变得更好，更适应这个世界，这是一件好事，值得肯定~那我们遇到了问题就来先讨论一下，一步一步来分析吧：1.你说自己没有朋友，不敢敞开心扉对他人，害怕别人了解你的真面目。   其实简而言之我觉得这是一个关于人际交往的问题，   我们首先理清一个逻辑，你对目前自己状态是满意的吗？比如你是否愿意自己一个人，你自己一个人工作、生活的时候是否舒适？如果你的答案是是，那其实就做你自己喜欢的事情，无需在意他人的看法，根据你想要的，你觉得好的方向去努力就可以了。   如果不是，也就是你还是想要融入大家的，那既然你想，我们总得为之去努力，努力就意味着你要付出一些代价的。对于你说害怕敞开心扉，害怕把自己真面目带给别人，是为什么会害怕呢？我的想法是首先你得喜欢与尊重自己，就是要相信自己是独一无二的，再好再差世界上也不会有另一个我存在了，只有你先爱自己，把更多的注意力放在自己身上，多给自己一些关注，而不要把注意力过多放在他人身上，把别人的标准看的这么重要，要允许自己和别人不一样，这是对自己的尊重和爱，再在生活中真诚友好、有原则性待人，对人际交往多一点信任，多世界怀揣一份美好，相信他人会愿意和你交流，然后在交流中凭自己感觉和缘分慢慢发展成朋友等关系。   再者，难道别人的状态（人际交往等）就一定都是完美的吗，就没有任何的问题吗？其实显然不是的，每个人都有自己的特点与不擅长的地方，我们慢慢接受自己目前的状态，然后在一次一次的实践中找到属于自己的交往模式，要相信万千世界，总有喜欢你性格的人，萝卜青菜各有所爱。而你如果是一颗萝卜（比喻），  那你的任务就是去找到属于你圈子里的萝卜或兔子，而不是要被其他因素干扰。 2. 你渴望活出自己的真实模样，但不知什么样的才是真实的自己，或者说不知道应该在什么合适的场合展示什么样的自己，于是不确定化为了不敢，原因是害怕和其他人不一样。   其实这个问题可以和第一个问题关联，基本上是关于人与人的关系，我应该如何适应这个世界，怎样处理好与周围人的关系，但这个题还多了一样，那就是如何活出真正的自己。   其实真实的自我是需要一步一步探索来的，而探索只能在实践中总结得来，找到自己、做真正的自己这是人一生的课题。没关系，我们抱抱自己，说声没关系，来日方长，我们可以慢慢来。而对于你说不敢表露自己情感，是因为不想格格不入。那么我们先来思考两个问题，对于你来说什么样才是格格不入的？什么样才是很好融入他人的？   展示真实的自己就会变得格格不入吗？那我要不要放弃展露真正我想展示想分享的，而选择去迎合他人呢？   其实在成长中，有一个很关键的因素，我认为是敢于表达自己的想法也好、态度也罢。前提是这本身是合法，且又是自己思考后觉得美好的东西。因为这是获得自信心与赢得别人尊重的一种方式。   但人非圣贤，我们生而为人，处于环境中，自然会有害怕他人的心理，有在意他人的想法，但是我们扪心自问下，他们的想法对自己来说是否具有决定性的作用呢？在我们漫长的人生当中，无论我们如何去迎合他人，总有人喜欢自己，有人讨厌自己。所以，我们要学会的就是在接纳自己的基础上勇敢做自己，得之我幸，弃我去者也不留也罢。 3.假装自己活的很好，用笑容伪装开心。   可能是因为处于某种社会环境下，不得不因为种种原因而变得虚伪，在大部分时间做的事情不是自己真实想的，但也许这就是世界运转的规则吧，我们要做就是在理解规则的前提下，找到自己的那一条路，去与自己和解，与他人和谐相处。   另外，人有消极情绪其实是很正常的，只是我们不能让消极情绪过多而已，当然我们也不需要讨好别人。有了情绪也是需要适当的发泄，因为挤压久了，快乐也会慢慢变少，就会在某一刻忍不住全然爆发出来，对自己与他人都是不利的。   在意识到自己不舒服的时候，尝试先跳出来，以第三人称视角看待目前自己的生活状态是否出现了问题，然后做些小改变，让自己的心情发泄出来，让自己心情舒畅一些。如运动可以分泌让大脑快乐的多巴胺、与信任的人倾诉可以缓解不良情绪、帮助他人可以让自己快乐值上升一点……找到适合自己的节奏就好啦~    总之，先从思想上打破，不要设置太多的心理障碍，勇敢一些、主动一点，就能推进关系进一步，学习适当敞开心扉和他人坦诚交流；从行为上接受，做好自己的同时顺其自然，尝试爱与接受爱，在实践中感知并形成自己与人相处的最舒适的行为模式，你会发现世界还是很美好的；从生活中改进，不限于尝试勇敢多与人互动，主动表达自己想法等。   当然，如果有余力，推荐你读一下《被讨厌的勇气》这本书，或者了解一些关于“积极心理学”的课程等来自助，相信你会打开一个不一样的世界。   最后祝你早日找到适合自己的“舒适区”，遇到良师和益友长伴，并遇良人相伴，要相信爱自己就是浪漫的开始！   我是小贝，世界和我都爱着你~加油相信你可以的。❤";
        answerBean.answererIconPath = alShenIcon;
        answerBean.answererId = 2;
        answerBean.answererNickName = "AlShen";
        answerBean.questionId = 1;
        answerBean.time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        DBCreator.getAnswerDao().insert(answerBean);
    }

    private void insertQuestion2() {
        QuestionBean questionBean = new QuestionBean();
        questionBean.question = "24岁，读研，为什么会变得非常排斥与家人联系？";
        questionBean.raiserIcon = alShenIcon;
        questionBean.raiserNickName = "AlShen";
        questionBean.raiserId = 2;
        questionBean.time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        DBCreator.getQuestionDao().insert(questionBean);
        AnswerBean answerBean = new AnswerBean();
        answerBean.answer = "题主你好：     在你的描述当中，我感觉到了内心强烈的冲突矛盾。一方面你对家人的关心很抵触，感觉很累，但一方面你不能接受这种抵触，好像你内心有一个严格的判官在批判自己出现这种感受。不知道，除了对家里人的感受，你对待其他的事情上出现一些负面的感受时，这个判官会不会也在你心里拿着一个严格的戒尺在敲打你？      从你的文字当中感觉家里人对你的关心有一些无微不至，或者是包办安排，好像在一些你不需要的地方，他们也要想方设法的为你做一些事情。就像一个满心关怀的妈妈，要给一个已经吃饱的孩子再使劲的喂进去食物，而孩子是不能拒绝这个食物的，即使他已经感觉有些反胃了，因为拒绝会让妈妈伤心，会让自己有罪恶感。一个孩子取悦父母的方式就是去接受，同时也是因为他确实很幼小，他需要去接受，但是随着我们的成长，我内心真正想要的和父母想想给的开始不一致，我们也开始逐渐希望自己去获得一些东西，而妈妈仍然因为拒绝而难过，不能够理解我们的拒绝，这也许会让我们内心很慌乱、自责。      在无法拒绝的基础上，似乎他们给你的东西你还要去装样子接受，不知道你是否有空间去做自己？同时你还提到现在还没有毕业，而家人就已经开始忙碌的提前帮你安排工作了，我似乎看到了总是害怕自己的小孩“吃不饱”的父母，关怀中又带着一些焦虑。而最压抑的地方是你无法表达，不知道如果你表示了“不需要”，是否会担心对父母家人造成了攻击？当人们的情绪累积到一定程度的时候却无法表达，身体就会帮助我们去表达，你用了躯体化这个词，相信你也是了解一些的。     其实感受是不分对错的，感受不会因为想抑制他，他就完全的消失，也不意味出现感受我们就一定会去做什么冲动的坏情。他们的爱意是真切的，但你的“不需要”也是真切的，需要和给予不相一致，本来就是难以用是和非来评价的。正因为你是一个独立的个体，所以你才会出现跟家人不同的感受，即使你现在无法去拒绝家人的关心，但是请别再鞭打自己的感受。       爱不一定仅仅是一种形式的，无论是他们对你的爱，还是你对他们的爱。";
        answerBean.answererIconPath = luibeIcon;
        answerBean.answererId = 1;
        answerBean.answererNickName = "LuiBe";
        answerBean.questionId = 2;
        answerBean.time = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        DBCreator.getAnswerDao().insert(answerBean);
    }

    private void insertShare1() {
        UserBean userLuibe = DBCreator.getUserDao().queryUserByPhone("17505570812");
        Log.d("TAG", ": " + userLuibe);
        ShareBeanXF shareBeanXF = new ShareBeanXF();
        shareBeanXF.time = System.currentTimeMillis() - 1000 * 60 * 60 * 24;//一天前
        ArrayList<String> picPaths = new ArrayList<>();
        picPaths.add("https://xinli001-appimg.oss-cn-hangzhou.aliyuncs.com/dynamic/20220306/1008456236_20220306203757_0.jpeg?x-oss-process=image/quality,Q_80/format,jpg");
        shareBeanXF.picPaths = picPaths;
        shareBeanXF.id = 1;
        shareBeanXF.authorNickName = userLuibe.name;
        shareBeanXF.authorIcon = userLuibe.iconPath;
        shareBeanXF.authorId = userLuibe.id;
        shareBeanXF.content = "讨好型人格具有什么特征？\n" +
                "\n" +
                "1、害怕说出自己内心的想法\n" +
                "觉得自己弱势，一旦说出自己的想法就会感觉被孤立，担心不被他人接纳，更害怕他人的攻击，所以他们总是把自己真实想法藏在心里，而不是完全大胆勇敢的表露出来。\n" +
                "\n" +
                "2、喜欢主动道歉\n" +
                "害怕产生冲突，希望用道歉的方式结束冲突，这是讨好型人格的人处理事情的主要方式。\n" +
                "\n" +
                "3、会迎合他人\n" +
                "在社交交往中，他们对待别人的观点经常会说，你说得很对，这种迎合来自他们内心的胆怯，总是觉得别人比自己要厉害，所以会把自己降得很低。\n" +
                "她们总是担心别人不高兴，但是却忘记了自我的情绪，自我声音是什么。\n" +
                "\n" +
                "4、不懂得拒绝\n" +
                "害怕拒绝，其实也是因为担心一旦拒绝他人，他人就会十分厌恶她们，而不拒绝，则是她们假装维持良好的关系，这可以减少她们内心的内疚与负罪情绪，但是无形中担负着巨大的心理压力，更让自己活得很累。\n" +
                "\n" +
                "5、没有原则和底线\n" +
                "在人际交往中，讨好型人总是忽略了自己，她们希望和她人保持和谐的关系，所以在交往中，有时会变得没有原则和底线，一味讨好他人，在这种相处模式中，反而无法赢得她人尊重。\n" +
                "\n" +
                "总结：讨好型人格最大的特点就是掩藏自己情绪非常害怕起冲突，所以她们会压抑自我的需求。";

        DBCreator.getShareDao().insert(shareBeanXF);
        //添加一条评论
        ShareCommentBean commentBean1 = new ShareCommentBean();
        commentBean1.comment = "讨好型人格背后的原因更值得去探究，我记得自己那时候很怕被孤立，孤独。当我一步步突破过来，也算是“伤痕累累”，涅磐重生，直到真正找到自己，学会拒绝，长成一个独立的我。是真的找到自己和成长后的自己。";
        commentBean1.shareId = 1;
        commentBean1.initiatorId = 2;
        commentBean1.initiatorNickName = "AlShen";
        commentBean1.initiatorIcon = alShenIcon;
        commentBean1.toId = 1;
        commentBean1.toNickName = "Luibe";
        commentBean1.time = SomeUtil.getTime();
        ShareCommentBean commentBean2 = new ShareCommentBean();
        commentBean2.comment = "加油";
        commentBean2.shareId = 1;
        commentBean2.initiatorId = 1;
        commentBean2.initiatorNickName = "Luibe";
        commentBean2.initiatorIcon = luibeIcon;
        commentBean2.toId = 2;
        commentBean2.toNickName = "AlShen";
        commentBean2.time = SomeUtil.getTime();
        DBCreator.getShareCommentDao().insert(commentBean1);
        DBCreator.getShareCommentDao().insert(commentBean2);
    }

    private void insertShare2() {
        UserBean userLuibe = DBCreator.getUserDao().queryUserByPhone("17505570812");
        ShareBeanXF shareBeanXF = new ShareBeanXF();
        shareBeanXF.time = System.currentTimeMillis() - 1000 * 60 * 60 * 2;
        ArrayList<String> picPaths = new ArrayList<>();
        picPaths.add("https://ossimg.xinli001.com/20220307/4ea464a99e871e4fc09ec76f22f1c1ad.jpg?x-oss-process=image/quality,Q_80/format,jpg");
        shareBeanXF.picPaths = picPaths;
        shareBeanXF.authorNickName = userLuibe.name;
        shareBeanXF.authorIcon = userLuibe.iconPath;
        shareBeanXF.authorId = userLuibe.id;
        shareBeanXF.content = "你现在心情怎么样？来测试一下～\n" +
                "在图中你看到了什么呢？";

        DBCreator.getShareDao().insert(shareBeanXF);
    }

    private void insertShare3() {
        UserBean userLuibe = DBCreator.getUserDao().queryUserByPhone("17505570812");
        ShareBeanXF shareBeanXF = new ShareBeanXF();
        shareBeanXF.time = System.currentTimeMillis() - 1000 * 60 * 35;
        ArrayList<String> picPaths = new ArrayList<>();
        picPaths.add("https://xinli001-appimg.oss-cn-hangzhou.aliyuncs.com/dynamic/20220306/1006942126_20220306225359_0.jpeg?x-oss-process=image/quality,Q_80/format,jpg");
        picPaths.add("https://xinli001-appimg.oss-cn-hangzhou.aliyuncs.com/dynamic/20220306/1006942126_20220306225359_1.jpeg?x-oss-process=image/quality,Q_80/format,jpg");
        shareBeanXF.picPaths = picPaths;
        shareBeanXF.authorNickName = userLuibe.name;
        shareBeanXF.authorIcon = userLuibe.iconPath;
        shareBeanXF.authorId = userLuibe.id;
        shareBeanXF.content = "如何区分抑郁和消极情绪\uD83E\uDDD0\n" +
                "\uD83C\uDF69抑郁带来的症状主要有情绪感受、行为活动和身体反应三个方面。\n" +
                "·在情绪感受上，我们可以体验到情绪低落、绝望、烦躁易怒和自我厌恶感；\n" +
                "·在行为活动上，我们会丧失对生活的兴趣，回避日常活动和社交生活，无法集中精力，感到乏力、疲惫；\n" +
                "·在身体反应上，可能会伴有失眠、饮食障碍和疼痛不适等生理反应。\n" +
                "\uD83C\uDF6B如果仅仅是某些方面的症状（比如只是失眠、心情不好），并在短时间内可以得到缓解，就大概率不归属“抑郁”。此时的消极情绪或症状是人们对于负面事物的正常反应，是健康的，不需要担心。";

        DBCreator.getShareDao().insert(shareBeanXF);
        //添加一条评论
        ShareCommentBean commentBean1 = new ShareCommentBean();
        commentBean1.comment = "我觉得我抑郁了，不知道怎么走出来，难过";
        commentBean1.shareId = 3;
        commentBean1.initiatorId = 2;
        commentBean1.initiatorNickName = "AlShen";
        commentBean1.initiatorIcon = alShenIcon;
        commentBean1.time = SomeUtil.getTime();

        DBCreator.getShareCommentDao().insert(commentBean1);
    }

    public static Context getContext() {
        return context;
    }

    public static boolean isLogin() {
        return user != null;
    }

    public static void logout() {
        PreferenceUtil.getInstance().remove("logger");
        user = null;
    }
}
