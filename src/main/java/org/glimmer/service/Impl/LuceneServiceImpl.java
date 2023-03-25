package org.glimmer.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.val;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.grouping.GroupingSearch;
import org.apache.lucene.search.grouping.TopGroups;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.glimmer.domain.Docs;
import org.glimmer.domain.ResponseResult;
import org.glimmer.mapper.DocsMapper;
import org.glimmer.service.LuceneService;
import org.glimmer.utils.LuceneUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LuceneServiceImpl implements LuceneService {

    @Autowired
    DocsMapper docsMapper;

    @Autowired
    @Qualifier("ContentType")
    FieldType contentType;

    @Autowired
    Analyzer analyzer;
    @Value("${lucene.index-path}")
    String indexPath;
    /**
     * 重新索引所有文件;
     *
     * @return document数
     */
    @Override
    public ResponseResult<Integer> FlushIndex() {
        val indexWriter = LuceneUtils.getIndexWriter(indexPath, LuceneUtils.getindexWriterCfg(analyzer));
        long count = 0L;
        try {
            count = indexWriter.deleteAll();
        } catch (IOException e) {
            return new ResponseResult(5003,"索引文件删除错误");
        }
        val docsLambdaQueryWrapper = new LambdaQueryWrapper<Docs>();
        val docsList = docsMapper.selectList(null);
        if(Objects.isNull(docsList)||docsList.isEmpty()) {
            return new ResponseResult<>(5004,"没有pdf文件");
        }
        if (IndexedByDocs(indexWriter, count, docsList)) return new ResponseResult<>(5001, "索引文件创建错误!");

        return new ResponseResult<>(200,"索引文件创建成功",docsList.size());
    }

    private boolean IndexedByDocs(IndexWriter indexWriter, long count, List<Docs> docsList) {
        for(val docs:docsList) {
            val doc = new Document();
            doc.add(new SortedDocValuesField("pdf_id", new BytesRef(docs.getPdfId() ) ) );
            doc.add(new StringField("pdf_id",docs.getPdfId(), Field.Store.YES));
            doc.add(new StringField("page_id",docs.getPageId(), Field.Store.YES));
            doc.add(new StringField("para_id",docs.getParaId(), Field.Store.YES));
            doc.add(new Field("content",docs.getContent(),contentType));
            try {
                indexWriter.addDocument(doc);
            } catch (IOException e) {
                return true;
            }
            count++;
        }
        return false;
    }

    /**
     * 删除索引文件
     *
     * @return
     */
    @Override
    public ResponseResult DeleteIndex() {
        val indexWriter = LuceneUtils.getIndexWriter(indexPath, LuceneUtils.getindexWriterCfg(analyzer));
        long count = 0L;
        try {
            count = indexWriter.deleteAll();
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            return new ResponseResult(5003,"索引文件删除错误");
        }
        return new ResponseResult<Long>(200,"删除成功",count);
    }

    /**
     * 添加某一个pdfID的索引文件
     *
     * @param pdfId
     * @return
     */
    @Override
    public ResponseResult AddPdfIndex(String pdfId) {
        val docsLambdaQueryWrapper = new LambdaQueryWrapper<Docs>();
        docsLambdaQueryWrapper.eq(Docs::getPdfId,pdfId);
        val docsList = docsMapper.selectList(docsLambdaQueryWrapper);
        if(Objects.isNull(docsList)||docsList.isEmpty()) {
            return new ResponseResult<>(5004,"未找到相应PDF,ID为："+pdfId);
        }
        val indexWriter = LuceneUtils.getIndexWriter(indexPath, LuceneUtils.getindexWriterCfg(analyzer));
        int count = 0;
        if (IndexedByDocs(indexWriter, count, docsList)) return new ResponseResult<>(5001, "索引文件创建错误!");
        try {
            indexWriter.commit();
        } catch (IOException e) {
            return new ResponseResult<>(5001,"索引文件创建错误!");
        }
        try {
            indexWriter.close();
        } catch (IOException e) {
            return new ResponseResult<>(5001,"索引文件创建错误!");
        }
        return new ResponseResult<Integer>(200,"索引文件创建成功");
    }
    /**
     * 删除某一个pdfID的索引文件
     *
     * @param pdfId
     * @return
     */
    @Override
    public ResponseResult DeletePdfIndex(String pdfId) {

        val pdf_id = new Term("pdf_id", pdfId);
        val indexWriter = LuceneUtils.getIndexWriter(indexPath, LuceneUtils.getindexWriterCfg(analyzer));
        long count = 0L;
        try {
            count = indexWriter.deleteDocuments(pdf_id);
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            return new ResponseResult(5003,"索引文件删除错误");
        }
        return new ResponseResult<Long>(200,"删除成功",count);
    }
    @Value("${lucene.group_limit}")
    int groupLimit;
    @Value("${lucene.group_docs_limit}")
    int groupDocsLimit;
    /**
     * @param keyword    搜索用的关键词
     * @param pageOffset 偏移值，用于分页，比如一页显示n篇文档，那么第m页的偏移值是(m-1)*n
     * @return
     */
    @Override
    public ResponseResult<List<List<Docs>>> SearchGroupByKeyword(String keyword, int pageOffset) {
        IndexSearcher indexSearcher = null;
        try {
            indexSearcher = new IndexSearcher(DirectoryReader.open(FSDirectory.open(Path.of(indexPath))));
        } catch (IOException e) {
           return new ResponseResult(5002,"无法打开索引文件");
        }
        val term = new Term("content",keyword);
        val termQuery = new TermQuery(term);
        int groupOffset = groupLimit * (pageOffset-1);
        TopGroups<BytesRef> topGroups = null;
        try {
            topGroups = LuceneUtils.group(indexSearcher, termQuery, "pdf_id", 0, groupDocsLimit, groupOffset, groupLimit);
        } catch (Exception e) {
            return new ResponseResult<>(5006,"索引查找失败");
        }
        if(topGroups.groups.length==0) {
            return new ResponseResult<>(200,"未找到相关结果");
        }
        List<List<Docs>> result = new ArrayList<List<Docs>>();
        for(val topGroup:topGroups.groups) {
            List<Docs> groupResult = new ArrayList<Docs>();
            for(val scoreDoc:topGroup.scoreDocs) {
                try {
                    val doc = indexSearcher.doc(scoreDoc.doc);
                    Docs para = new Docs();
                    String pdf_id = doc.getField("pdf_id").stringValue();
                    String page_id = doc.getField("page_id").stringValue();
                    String para_id = doc.getField("para_id").stringValue();
                    para.setPdfId(pdf_id);
                    para.setPageId(page_id);
                    para.setParaId(para_id);

                    val docsLambdaQueryWrapper = new LambdaQueryWrapper<Docs>();
                    docsLambdaQueryWrapper.eq(Docs::getPdfId,pdf_id).eq(Docs::getPageId,page_id).eq(Docs::getParaId,para_id);

                    val paras = docsMapper.selectList(docsLambdaQueryWrapper);
                    if(paras.isEmpty()) {
                        throw new RuntimeException();
                    }
                    StringBuilder content = new StringBuilder();
                    for(val paraHit:paras) {
                        content.append(paraHit.content);
                    }
                    para.setContent(content.toString());
                    groupResult.add(para);
                } catch (IOException e) {
                   return new ResponseResult(5006,"索引查找失败");
                }
                catch (RuntimeException e) {
                    return new ResponseResult(3001,"数据库错误");
                }

            }
            result.add(groupResult);
        }
        return new ResponseResult<>(200,"查找成功",result);
    }


}
