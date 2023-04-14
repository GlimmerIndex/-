package org.glimmer.lucene;

import lombok.val;
import org.apache.el.parser.Token;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.IOUtils;
import org.glimmer.utils.LuceneUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;

@SpringBootTest
public class TestLucene {

    @Autowired
    Analyzer analyzer;

    @Test
    void tokenTest() throws IOException {
        String text = "数字查询和字符串查询不太一样，在内部实现结构上它并不是像字符串那样使用 FST 来组织关键词。如果每一个数字都是关键词，那么使用数字范围定位出的关键词会非常多，需要 merge 的文档列表也会非常多，这样的查询效率就会很差。如果是浮点型数字，那这个问题就更加突出了，可能每个浮点数字只会关联一个文档，而浮点数关键词将会太多太多。\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"Lucene 将数字进行特殊处理，内部使用 BKD-Tree 来租户数字键。BKD 树也是一个较为复杂的数据结构，简单理解就是将数值比较接近的一些数字联合起来作为一个叶节点共享一个 PostingList，同时在 PostingList 的元素需要存储数字键的值，在查询时会额外多出一个过滤步骤。粗略来看，BKD 树会将一个大的数值范围进行二分，然后再继续二分，一直分到几层之后发现关联的文档数量小于设定的阈值，就不再继续拆分了。BKD 树还可以索引多维数值，这样它就可以应用于地理位置查询。\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"下面我们给前面的文章索引增加一个字段，文章的 ID，我们需要将目录里的所有文件全部删除，然后运行 Indexer.java 重新构建索引";
        StringReader reader = null;
        TokenStream tokenStream = null;
        try {
            reader = new StringReader(text);
            tokenStream = analyzer.tokenStream("", reader);
            CharTermAttribute charTermAttribute  = tokenStream.getAttribute(CharTermAttribute.class);
            OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
            tokenStream.reset();
            while (tokenStream.incrementToken()) {
                int startOffset = offsetAttribute.startOffset();
                int endOffset   = offsetAttribute.endOffset();
                if((endOffset - startOffset) > 1){
                    String term = charTermAttribute.toString();
                    System.out.println(term);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            IOUtils.close(tokenStream, reader);
        }
        return;
    }

    @Test
    void clearIndex() throws IOException {
        val indexWriterConfig = new IndexWriterConfig(analyzer);
        Directory directory =  FSDirectory.open(Path.of("index"));
        val indexWriter = new IndexWriter(directory, indexWriterConfig);
        indexWriter.deleteAll();
        indexWriter.close();
        directory.close();
    }
}