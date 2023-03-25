package org.glimmer.service;

import org.glimmer.domain.Docs;
import org.glimmer.domain.ResponseResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LuceneService {
    /**
     *
     * 重新索引所有文件;
     * @return document数
     */
    public ResponseResult<Integer> FlushIndex();

    /**
     * 删除索引文件
     * @return
     */
    public ResponseResult DeleteIndex();

    /**
     * 添加某一个pdfID的索引文件
     * @param pdfId
     * @return
     */
    public ResponseResult AddPdfIndex(String pdfId);

    /**
     * 删除某一个pdfID的索引文件
     * @param pdfId
     * @return
     */
    public ResponseResult DeletePdfIndex(String pdfId);

    /**
     *
     * @param keyword 搜索用的关键词
     * @param pageOffset 偏移值，用于分页，比如一页显示n篇文档，那么第m页的偏移值是(m-1)*n,传入变量为n(显示第几页)
     * @return
     */
    public ResponseResult<List<List<Docs>>> SearchGroupByKeyword(String keyword,int pageOffset);
}
