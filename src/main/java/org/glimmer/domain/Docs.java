package org.glimmer.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用于存储文档内容，并记录相应pdf编号，以及文档位置.
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("pdf_doc")
public class Docs {
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
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
    public String paraId;
    /**
     * 段落内容
     */
    public String content;


}
