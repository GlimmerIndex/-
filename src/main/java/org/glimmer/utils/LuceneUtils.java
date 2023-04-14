package org.glimmer.utils;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.DocValuesType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.IOContext;
import org.apache.lucene.store.IndexInput;
import org.apache.lucene.util.BytesRef;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Lucene工具类
 */
@Component
public class LuceneUtils {

    /**
     * 分词器
     * @return
     */
    @Bean
    Analyzer getAnalyzer() {
        return new IKAnalyzer();
    }

    /**
     * IndexWriterConfig不能共享，故这里不作为bean<br>
     * <span color="green">返回一个IndexWriterConfig</span>
     * @param analyzer
     * @return
     */
    public static IndexWriterConfig getindexWriterCfg(Analyzer analyzer) {
        return new IndexWriterConfig(analyzer);
    }


    /**
     * 获取索引写类
     *
     * 记得在外面调用IndexWriter.getDirectory().close()来关闭流
     * @param path,iwc
     * @return
     * @throws IOException
     */
    public static IndexWriter getIndexWriter(String path,IndexWriterConfig iwc) {
        Path indexPath = Paths.get(path);
        File file = new File(path);
        if(!file.exists()) {
            file.mkdirs();
        }
        if(!file.canRead()) {
            throw new RuntimeException("Can't open filepath " + path);
        }
        Directory directory = null;
        try {
            directory = FSDirectory.open(indexPath);
            return new IndexWriter(directory,iwc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 这个FieldType用于存储段落对应的页码和段落编号信息，需要存储，不需要分词、索引。
     *
     * @return
     */
    @Bean("IDType")
    public static FieldType getIDType() {
        FieldType fieldType = new FieldType();
        /**
         * 存储
         *
         */
        fieldType.setStored(true);
        /**
         * 不索引
         *
         */
        fieldType.setIndexOptions(IndexOptions.NONE);
        /**
         * 设置类型
         *
         */
        // fieldType.setDocValuesType(DocValuesType.BINARY);

        /**
         * 分词
         *
         */
        fieldType.setTokenized(false);

        return fieldType;
    }
    @Bean("ContentTypeTest")
    public static FieldType getContentTestType() {
        FieldType fieldType = new FieldType();
        /**
         * 存储
         *
         */
        fieldType.setStored(true);
        /**
         * 不索引
         *
         */
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        /**
         * 设置类型
         *
         */
        // fieldType.setDocValuesType(DocValuesType.BINARY);

        /**
         * 分词
         *
         */
        fieldType.setTokenized(true);

        return fieldType;
    }

    /**
     * 实际开发使用的ContentType
     * @return
     */
    @Bean("ContentType")
    public static FieldType getContentType() {
        FieldType fieldType = new FieldType();
        /**
         * 存储
         *
         */
        fieldType.setStored(true);
        /**
         * 不索引
         *
         */
        fieldType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        /**
         * 设置类型
         *
         */
        // fieldType.setDocValuesType(DocValuesType.BINARY);

        /**
         * 分词
         *
         */
        fieldType.setTokenized(true);

        return fieldType;
    }

    /**
     * 用于分组查询.
     * @param indexSearcher
     * @param query
     * @param groupSort 组间排序
     * @param withinGroupSort 组内排序
     * @param groupField 分组Field名
     * @param groupDocsOffset 组内偏移
     * @param groupDocsLimit 每组最大doc值
     * @param groupOffset 组偏移
     * @param groupLimit 最多组
     * @return
     * @throws Exception
     */
    public static TopGroups<BytesRef> group(IndexSearcher indexSearcher, Query query, Sort groupSort, Sort withinGroupSort, String groupField,
                                     int groupDocsOffset, int groupDocsLimit, int groupOffset, int groupLimit) throws Exception {
        //实例化GroupingSearch实例，传入分组域
        GroupingSearch groupingSearch = new GroupingSearch(groupField);
        //设置组间排序方式
        groupingSearch.setGroupSort(groupSort);
        //设置组内排序方式
        groupingSearch.setSortWithinGroup(withinGroupSort);
        //设置用来缓存第二阶段搜索的最大内存，单位MB，第二个参数表示是否缓存评分
        groupingSearch.setCachingInMB(64.0, true);
        //是否计算符合查询条件的所有组
        groupingSearch.setAllGroups(true);
        groupingSearch.setAllGroupHeads(true);
        //设置一个分组内的上限
        groupingSearch.setGroupDocsLimit(groupDocsLimit);
        //设置一个分组内的偏移
        groupingSearch.setGroupDocsOffset(groupDocsOffset);
        return groupingSearch.search(indexSearcher, query, groupOffset, groupLimit);
    }
    public static TopGroups<BytesRef> group(IndexSearcher indexSearcher, Query query, String groupField,
                                     int groupDocsOffset, int groupDocsLimit, int groupOffset, int groupLimit) throws Exception {
        return group(indexSearcher, query, Sort.RELEVANCE, Sort.RELEVANCE, groupField, groupDocsOffset, groupDocsLimit, groupOffset, groupLimit);
    }



    @Bean
    public static SimpleHTMLFormatter getSimpleHTMLFormatter() {
        return new SimpleHTMLFormatter("<span class=\"highlight_glimmer\">","</span>");
    }
}
