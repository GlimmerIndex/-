package org.glimmer.lucene;

import lombok.val;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.grouping.GroupDocs;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IOUtils;
import org.glimmer.utils.LuceneUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SearchTest {

    @Autowired
    Analyzer analyzer;

    @Test
    public void GroupSearchTest() throws Exception {
        DirectoryReader directoryReader = DirectoryReader.open(FSDirectory.open(Path.of("index")));

        val indexSearcher = new IndexSearcher(directoryReader);

        val term = new Term("content", "萧炎");
        val termQuery = new TermQuery(term);
        int groupLimit = 2;
        int groupDocsLimit = 2;
        //控制组的偏移
        int groupOffset = 0;
        System.out.println("#### 组的分页大小为：" + groupLimit);
        System.out.println("#### 组内分页大小为：" + groupDocsLimit);
        TopGroups<BytesRef> topGroups = LuceneUtils.group(indexSearcher, termQuery, "pdf_id", 0, groupDocsLimit, groupOffset, groupLimit);
        val totGroupCount = topGroups.totalGroupCount;
        while (groupOffset <  totGroupCount ) {//说明还有不同的分组
            //控制组内偏移，每次开始遍历一个新的分组时候，需要将其归零
            int groupDocsOffset = 0;
            System.out.println("#### 开始组的分页");
            System.out.println();
            groupOffset += topGroups.groups.length;
            System.out.println(Arrays.toString(topGroups.groups));
            for(val group:topGroups.groups) {
                for(val doc:group.scoreDocs) {
                    System.out.println(indexSearcher.doc(doc.doc));
                }
            }
            System.out.println("#### 结束组的分页");
            topGroups = LuceneUtils.group(indexSearcher, termQuery, "pdf_id", groupDocsOffset, groupDocsLimit, groupOffset, groupLimit);
        }

    }

    @Test
    public void SimpleSearch() throws IOException, ParseException {
        Directory directory = FSDirectory.open(Path.of("index"));
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        QueryParser parser = new QueryParser("context",analyzer);
        Query query = parser.parse("数据");
        parser.setDefaultOperator(QueryParser.Operator.AND);

        TopDocs hits = indexSearcher.search(query,10);
        for(ScoreDoc hit:hits.scoreDocs) {
            Document doc = indexSearcher.doc(hit.doc);
            System.out.println(doc.get("context"));
            System.out.println(doc.get("id"));
        }
        return;
    }
    @Test
    public void IdSearch() throws IOException, ParseException {
        Directory directory = FSDirectory.open(Path.of("index"));
        DirectoryReader directoryReader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);

        QueryParser parser = new QueryParser("content",analyzer);
        parser.setDefaultOperator(QueryParser.Operator.AND);
        Query query = parser.parse("回复");
        TopDocs hits = indexSearcher.search(query,10);
        for(ScoreDoc hit:hits.scoreDocs) {
            Document doc = indexSearcher.doc(hit.doc);
            System.out.println(doc.get("id"));
            System.out.println(doc.get("content"));
            System.out.println(hit.score);
        }
        return;
    }
    @Test
    public void SampleTest() throws IOException, ParseException {


        Path indexPath = Files.createTempDirectory("tempIndex");
        Directory directory = FSDirectory.open(indexPath);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter iwriter = new IndexWriter(directory, config);
        Document doc = new Document();
        String text = "This is the text to be indexed.";
        doc.add(new Field("fieldname", text, TextField.TYPE_STORED));
        iwriter.addDocument(doc);
        iwriter.close();

        // Now search the index:
        DirectoryReader ireader = DirectoryReader.open(directory);
        IndexSearcher isearcher = new IndexSearcher(ireader);
        // Parse a simple query that searches for "text":
        QueryParser parser = new QueryParser("fieldname", analyzer);
        Query query = parser.parse("text");
        ScoreDoc[] hits = isearcher.search(query, 10).scoreDocs;
        assertEquals(1, hits.length);
        // Iterate through the results:
        for (int i = 0; i < hits.length; i++) {
            Document hitDoc = isearcher.doc(hits[i].doc);
            assertEquals("This is the text to be indexed.", hitDoc.get("fieldname"));
        }
        ireader.close();
        directory.close();
        IOUtils.rm(indexPath);
    }


}
