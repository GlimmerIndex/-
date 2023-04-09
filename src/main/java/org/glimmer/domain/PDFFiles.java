package org.glimmer.domain;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_files")
public class PDFFiles {

    /**
     * 主键
     */
    private Long id;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件内容
     */
    @TableField(value = "file_data")
    private String filePath;
    /**
     * 文件大小
     */
    private Long fileSize;
    /***
     * 文件类型
     */
    private String fileType;
    /**
     * 文件上传时间
     */
    private Date uploadTime;
    /**
     * 上传人
     */
    private Long uploadBy;
    /**
     * 更新时间
     */
    private Data updateTime;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 删除标志（0代表未删除，1代表删除）
     */
    private Integer delFlag;
    /**
     * 文件哈希值
     */
    private String hashFileName;
    /***
     * UUId文件名
     */
    private String uuidFileName;
    /**
     * 点赞数
     */
    private Long likeNumber;
    /**
     * 点踩数
     */
    private Long dislikeNumber;
}
