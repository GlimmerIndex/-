package org.glimmer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.glimmer.domain.UserLikeFile;
import org.springframework.security.core.parameters.P;

import java.util.List;

@Mapper
public interface UserLikeFileMapper extends BaseMapper<UserLikeFile> {

    /**
     * 根据文件id查找所有给文件点赞或点踩的用户
     * @param fileId
     * @return
     */
    @Select("SELECT liked_user_id FROM sys_user_like WHERE liked_file_id = #{fileId}")
    List<Long> getUserIdByLikedFile(@Param("fileId") Long fileId);

    /**
     * 根据用户ID查找所有用户点赞的文章
     * @param userId
     * @return
     */
    @Select("SELECT liked_file_id FROM sys_user_like WHERE liked_user_id = #{userId}")
    List<Long> getFileIdByLikedUser(@Param("userId") Long userId);

    /**
     * 根据用户ID和文件Id查询点赞状态
     * @param userId
     * @param fileId
     * @return
     */

    @Select("SELECT is_liked FROM sys_user_like WHERE liked_user_id = #{userId} AND liked_file_id = #{fileId}")
    Integer getIsLikedByLikedUserAndLikedFile(@Param("userId") Long userId,@Param("fileId") Long fileId);

    /**
     * 根据用户ID和文件Id查询点踩状态
     * @param userId
     * @param fileId
     * @return
     */

    @Select("SELECT is_disliked FROM sys_user_like WHERE liked_user_id = #{userId} AND liked_file_id = #{fileId}")
    Integer getIsDislikedByLikedUserAndLikedFile(@Param("userId") Long userId,@Param("fileId") Long fileId);



    /**
     * 更新点赞
     * @param isLiked
     * @param isLikedFileId
     * @return
     */
    @Update("UPDATE sys_user_like SET is_liked = #{is_liked} WHERE liked_file_id = #{liked_file_id} AND liked_user_id = #{liked_user_id}")
    Integer updateIsLiked(@Param("is_liked") Integer isLiked,@Param("liked_file_id") Long isLikedFileId, @Param("liked_user_id") Long likedUser);

    /**
     * 更新点踩
     * @param isDisliked
     * @param isLikedFileId
     * @return
     */
    @Update("UPDATE sys_user_like SET is_disliked = #{is_disliked} WHERE liked_file_id = #{liked_file_id} AND liked_user_id = #{liked_user_id}")
    Integer updateDisLiked(@Param("is_disliked") Integer isDisliked,@Param("liked_file_id") Long isLikedFileId,@Param("liked_user_id") Long likedUser);

    /**
     * 删除点赞和点踩为零的数据
     * @param fileId
     * @return
     */
    @Delete("DELETE FROM sys_user_like WHERE liked_file_id = #{liked_file_id}")
    Integer deleteLikedFileAndUser(@Param("liked_file_id") Long fileId);

    /**
     * 查寻user和file关系是否存在
     * @param userId
     * @param fileId
     * @return
     */
    @Select("SELECT COUNT(*) FROM sys_user_like WHERE liked_user_id = #{userId} AND liked_file_id = #{fileId}")
    Integer checkUserFileExisted(@Param("userId") Long userId,@Param("fileId") Long fileId);

    /**
     * 查询文章点赞数量
     * @param fileId
     * @param isLiked
     * @return
     */

    @Select("SELECT COUNT(*) FROM sys_user_like WHERE liked_file_id = #{fileId} AND is_liked = #{isLiked}")
    Long getUserLikeFileCount(@Param("fileId") Long fileId,@Param("isLiked") Integer isLiked);

    /**
     * 查询文章点踩数量
     * @param fileId
     * @param isDisliked
     * @return
     */
    @Select("SELECT COUNT(*) FROM sys_user_like WHERE liked_file_id = #{fileId} AND is_disliked = #{isDisliked}")
    Long getUserDislikeFileCount(@Param("fileId") Long fileId,@Param("isDisliked") Integer isDisliked);



}
