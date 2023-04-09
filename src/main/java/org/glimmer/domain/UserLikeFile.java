package org.glimmer.domain;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user_like")
public class UserLikeFile {

    /**
     * 主键
     */
    private Long id;
    /**
     * 被点赞的文章
     */
    private Long likedFileId;
    /**
     * 点赞的用户
     */
    private Long likedUserId;
    /**
     * 是否点赞（1点赞，0不点赞或撤销点赞）
     */
    private Integer isLiked;
    /**
     * 是否点踩（1点踩，0不点踩或撤销点踩）
     */
    private Integer isDisliked;
}
