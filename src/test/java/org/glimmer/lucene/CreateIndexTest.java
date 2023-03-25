package org.glimmer.lucene;

import lombok.val;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.glimmer.domain.Docs;
import org.glimmer.mapper.DocsMapper;
import org.glimmer.utils.LuceneUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Path;

@SpringBootTest
public class CreateIndexTest {
    @Autowired
    Analyzer analyzer;
    @Test
    public void testCreate() throws IOException {
        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        Directory directory = FSDirectory.open(Path.of("index"));
        IndexWriter indexWriter = new IndexWriter(directory,iwc);
        val idType = LuceneUtils.getIDType();
        val contentTestType = LuceneUtils.getContentTestType();

        val id = new Field("id", "Integer.toString(1)", idType);
        val content = new Field("content", "网页资讯视频图片知道文库贴吧地图采购\n" +
                "抗压背锅\n" +
                "进入贴吧全吧搜索\n" +
                "连续0天03月22日漏签0天\n" +
                "\n" +
                "抗压背锅吧 关注：4,816,857贴子：121,886,840\n" +
                "看贴\n" +
                "图片\n" +
                "吧主推荐\n" +
                "玩乐\n" +
                "1 2 3 下一页 尾页\n" +
                "187回复贴，共3页\n" +
                "，跳到 \n" +
                " 页 确定 \n" +
                "<返回抗压背锅吧\n" +
                "贴子管理\n" +
                "看来是低估BLG双C了，是真的猛\n" +
                "只看楼主收藏回复\n" +
                "\n" +
                "指马为鹿\n" +
                "大名鼎鼎\n" +
                "14\n" +
                "\n" +
                "\n" +
                "感觉比EDG的双C强不少，\n" +
                "上野辅那么能送居然能救得回来，\n" +
                "这应该是目前最强的国产双C了，\n" +
                "亚运我觉得这对双C去也挺不错的。\n" +
                "\n" +
                "\n" +
                "送TA礼物\n" +
                "IP属地:福建1楼2023-03-22 19:58回复\n" +
                "\n" +
                "乐~~\n" +
                "富有名气\n" +
                "8\n" +
                "苏宁的这两个余孽真的\n" +
                "\n" +
                "\n" +
                "IP属地:山东来自Android客户端2楼2023-03-22 19:59\n" +
                "回复\n" +
                "\n" +
                "-向晚大摸王-\n" +
                "声名远扬\n" +
                "12\n" +
                "两边ad都拉满了，反观某个队的ad\n" +
                "\n" +
                "\n" +
                "IP属地:山东来自Android客户端3楼2023-03-22 19:59\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "炉心融解\n" +
                "闻名一方\n" +
                "11\n" +
                "牙膏没用 大飞打他跟打儿子一样\n" +
                "\n" +
                "\n" +
                "IP属地:江苏来自iPhone客户端4楼2023-03-22 19:59\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "内个还是那个\n" +
                "闻名一方\n" +
                "11\n" +
                "可是blg被edg暴打了\n" +
                "\n" +
                "\n" +
                "IP属地:河北来自iPhone客户端5楼2023-03-22 19:59\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "月桥花院\n" +
                "闻名一方\n" +
                "11\n" +
                "elk一直可以的\n" +
                "\n" +
                "\n" +
                "IP属地:湖南来自Android客户端6楼2023-03-22 19:59\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "今日方知我是我啊\n" +
                "远近闻名\n" +
                "10\n" +
                "二带三也能赢\n" +
                "\n" +
                "\n" +
                "IP属地:湖北来自Android客户端7楼2023-03-22 19:59\n" +
                "回复\n" +
                "\n" +
                "懒得想emm\n" +
                "闻名一方\n" +
                "11\n" +
                "不错几把，中路对线给大飞尽孝，下路等他送的时候有你看的\n" +
                "\n" +
                "\n" +
                "IP属地:陕西来自Android客户端8楼2023-03-22 20:00\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "lyyyyyylll77\n" +
                "闻名一方\n" +
                "11\n" +
                "JDG换中单纯削弱\n" +
                "\n" +
                "\n" +
                "IP属地:四川来自Android客户端10楼2023-03-22 20:00\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "污咪\n" +
                "闻名一方\n" +
                "11\n" +
                "blg上限本来就高 只是发挥太不稳定了。\n" +
                "\n" +
                "\n" +
                "IP属地:上海来自Android客户端11楼2023-03-22 20:00\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "星火水晶\n" +
                "闻名一方\n" +
                "11\n" +
                "指先生认为现役ad中比JKL强的有多少\n" +
                "\n" +
                "\n" +
                "IP属地:广西来自Android客户端12楼2023-03-22 20:00\n" +
                "回复\n" +
                "\n" +
                "Rng万歳よし\n" +
                "颇具盛名\n" +
                "7\n" +
                "打下lpl的fw差不多了出去都是尽孝的\n" +
                "\n" +
                "\n" +
                "IP属地:湖南来自Android客户端13楼2023-03-22 20:01\n" +
                "回复\n" +
                "\n" +
                "紫夜洪荒\n" +
                "赫赫有名\n" +
                "13\n" +
                "纯纯三打七，看乐了\n" +
                "\n" +
                "\n" +
                "IP属地:辽宁来自Android客户端14楼2023-03-22 20:01\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "owoo\n" +
                "富有美誉\n" +
                "9\n" +
                "膏膏只是猛这一把，elk倒是最近都不错\n" +
                "\n" +
                "\n" +
                "IP属地:河南来自Android客户端15楼2023-03-22 20:01\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "864652846d\n" +
                "闻名一方\n" +
                "11\n" +
                "反正比月男腐乳强\n" +
                "\n" +
                "\n" +
                "IP属地:北京来自Android客户端16楼2023-03-22 20:01\n" +
                "回复\n" +
                "\n" +
                "咸鱼_路过\n" +
                "闻名一方\n" +
                "11\n" +
                "牙膏打厉害的不太行，但是elk确实猛\n" +
                "\n" +
                "\n" +
                "IP属地:贵州来自Android客户端17楼2023-03-22 20:01\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "慵懒的猫970\n" +
                "富有美誉\n" +
                "9\n" +
                "有没有可能是对面中单太菜\n" +
                "\n" +
                "\n" +
                "IP属地:湖北来自Android客户端18楼2023-03-22 20:01\n" +
                "回复\n" +
                "\n" +
                "贴吧用户_a99R62t\n" +
                "远近闻名\n" +
                "10\n" +
                "你在做梦？这冰女是人玩的？\n" +
                "\n" +
                "\n" +
                "IP属地:广西来自Android客户端19楼2023-03-22 20:01\n" +
                "回复\n" +
                "\n" +
                "金牛小小的希望\n" +
                "远近闻名\n" +
                "10\n" +
                "elk确实可以，一阵ad\n" +
                "\n" +
                "\n" +
                "IP属地:河南来自iPhone客户端20楼2023-03-22 20:01\n" +
                "回复\n" +
                "\n" +
                "椛落情依舊\n" +
                "颇具盛名\n" +
                "7\n" +
                "elk确实可以的，亚运会现在机会最大的AD了，中单还是左手靠谱点，牙膏基本打不了大飞的，在京东的时候都不行，别说现在了\n" +
                "\n" +
                "\n" +
                "IP属地:浙江21楼2023-03-22 20:01\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "八域巡天使\n" +
                "声名远扬\n" +
                "12\n" +
                "上路这把被xun坑了，xun前面那波不送上路稳压的，xun送完之后不管上路了，上路被各种军训越塔就离谱。\n" +
                "\n" +
                "\n" +
                "IP属地:广东来自Android客户端22楼2023-03-22 20:02\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "传菜-\n" +
                "大名鼎鼎\n" +
                "14\n" +
                "牙膏也想去？打得赢太守吗\n" +
                "\n" +
                "\n" +
                "IP属地:福建来自Android客户端23楼2023-03-22 20:02\n" +
                "回复\n" +
                "\n" +
                "柏拉图的善恶\n" +
                "小有美名\n" +
                "5\n" +
                "\n" +
                "\n" +
                "\n" +
                "IP属地:上海24楼2023-03-22 20:02\n" +
                "回复\n" +
                "\n" +
                "俯瞰风景\n" +
                "闻名一方\n" +
                "11\n" +
                "打个ig给你闹麻了\n" +
                "\n" +
                "\n" +
                "IP属地:湖北来自iPhone客户端25楼2023-03-22 20:02\n" +
                "回复\n" +
                "\n" +
                "你与时光皆薄凉\n" +
                "富有美誉\n" +
                "9\n" +
                "还嫌去年牙膏被大飞教训的不够是吧，elk泽丽绝活哥罢了碰到打线ad原型毕露\n" +
                "\n" +
                "\n" +
                "IP属地:安徽来自Android客户端26楼2023-03-22 20:02\n" +
                "回复\n" +
                "\n" +
                "一场秋雨\n" +
                "富有美誉\n" +
                "9\n" +
                "elk亚运会我觉得有希望，膏膏还是差点意思，不稳\n" +
                "\n" +
                "\n" +
                "IP属地:江苏来自Android客户端27楼2023-03-22 20:02\n" +
                "回复\n" +
                "\n" +
                "caldz\n" +
                "富有美誉\n" +
                "9\n" +
                "伊拉克这赛季国产最强ad应该可以聊，数据和表现都支持。\n" +
                "膏膏我怕你是没看他困了的时候\n" +
                "\n" +
                "\n" +
                "IP属地:广东来自Android客户端28楼2023-03-22 20:03\n" +
                "回复\n" +
                "\n" +
                "我上也是3比0\n" +
                "富有美誉\n" +
                "9\n" +
                "elk最近状态太恐怖了，把把伤害拉满，牙膏不太稳定\n" +
                "\n" +
                "\n" +
                "IP属地:福建来自Android客户端30楼2023-03-22 20:03\n" +
                "回复\n" +
                "\n" +
                "NIP青训上单\n" +
                "远近闻名\n" +
                "10\n" +
                "双C这么猛 为啥前三没blg\n" +
                "\n" +
                "\n" +
                "IP属地:浙江31楼2023-03-22 20:03\n" +
                "收起回复\n" +
                "\n" +
                "\n" +
                "司马仔极客\n" +
                "富有名气\n" +
                "8\n" +
                "elk拿到泽丽真的猛，至少不激进暴毙\n" +
                "\n" +
                "\n" +
                "IP属地:广东来自Android客户端32楼2023-03-22 20:03\n" +
                "回复\n" +
                "皇冠身份\n" +
                "发贴红色标题\n" +
                "显示红名\n" +
                "签到六倍经验\n" +
                "更多定制特权\n" +
                "兑换本吧会员\n" +
                "吧主申请名人堂，解锁更多会员特权\n" +
                "\n" +
                "本吧专属印记\n" +
                "定制名片背景\n" +
                "名人自动顶贴\n" +
                "定制头像边框\n" +
                "收起特权\n" +
                "兑换本吧会员\n" +
                "赠送补签卡1张，获得[经验书购买权]\n" +
                "\n" +
                "我在贴吧[管理]\n" +
                "\n" +
                "0[获取]\n" +
                "我的本吧信息\n" +
                "\n" +
                "排名：107280 昨日排名没有变化，击败了本吧97.8%的吧友\n" +
                "闻名一方\n" +
                "11\n" +
                "经验：\n" +
                "3135/6000\n" +
                "扫二维码下载贴吧客户端\n" +
                "下载贴吧APP\n" +
                "看高清直播、视频！\n" +
                "贴吧热议榜\n" +
                "1中科大取消造黄谣男生保研录取资格1928610\n" +
                "2官方下场刀撸投票大战1772393\n" +
                "3林俊杰 mirror1455516\n" +
                "4BLG五连胜1408342\n" +
                "5抽象文化入侵高中1285600\n" +
                "6还能去泰国旅游吗980400\n" +
                "7LNG战胜UP785381\n" +
                "8大学生在贴吧南征北战591712\n" +
                "9在米饭上放什么能快速炫完444045\n" +
                "10广东财经大学 发呆区395920\n" +
                "贴吧页面意见反馈\n" +
                "违规贴吧举报反馈通道\n" +
                "贴吧违规信息处理公示\n" +
                "1 2 3 下一页 尾页\n" +
                "187回复贴，共3页\n" +
                "，跳到 \n" +
                " 页 确定 \n" +
                "<返回抗压背锅吧\n" +
                "发表回复\n" +
                "发贴请遵守贴吧协议及“七条底线”贴吧投诉 停止浮动\n" +
                "内  容:\n" +
                "使用签名档 \n" +
                " 查看全部\n" +
                "发 表\n" +
                "保存至快速回贴\n" +
                "\n" +
                "退 出\n" +
                "©2023 Baidu贴吧协议|隐私政策|吧主制度|意见反馈|网络谣言警示\n" +
                "\n。",contentTestType);
        /**
         * 创建document
         */
        Document document = new Document();
        document.add(id);
        document.add(content);
        indexWriter.addDocument(document);
        indexWriter.commit();
        indexWriter.close();
        directory.close();
    }

    @Autowired
    DocsMapper docsMapper;

    @Value("${lucene.index-path}")
    String index_path;
    @Autowired
    @Qualifier("IDType")
    FieldType IdType;

    @Autowired
    @Qualifier("ContentTypeTest")
    FieldType ContentType;
    @Test
    public void DocsCreate() throws IOException {
        Directory directory = FSDirectory.open(Path.of(index_path));

        IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(directory,iwc);

        val docs = docsMapper.selectList(null);
        System.out.println(docs.size());
        for(Docs doc : docs) {
            val document = new Document();
            val pdfId = new SortedDocValuesField("pdf_id", new BytesRef(doc.getPdfId()));
            val pdfId_S = new StringField("pdf_id",doc.getPdfId(), Field.Store.YES);
            val pageId = new Field("page_id", doc.getPageId(), IdType);
            val paraId = new Field("para_id", doc.getParaId(), IdType);
            val content = new Field("content", doc.getContent(), ContentType);
            document.add(pdfId);
            document.add(pdfId_S);
            document.add(pageId);
            document.add(paraId);
            document.add(content);
            indexWriter.addDocument(document);
        }
        indexWriter.close();
        directory.close();
    }
}
