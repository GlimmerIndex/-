package org.glimmer.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用于存储文档内容，并记录相应pdf编号，以及文档位置.
 *
 */
@Data
@AllArgsConstructor
@TableName("pdf_doc")
public class Docs {
    /**
     * 主键
     */
    @JSONField(serialize = false)
    public Long id;
    /**
     * 所属pdf编号
     */
    @JSONField(serialize = false)
    public String pdfId;
    /**
     * 所属页号
     */
    public String pageId;
    /**
     * 所属段落号
     */
    @JSONField(serialize = false)
    public String paraId;
    /**
     * 段落内容
     */
    public String content;
}
