package org.glimmer.service;

public interface UpdateLikeService {
    /**
     * 更新用户文章点赞
     * @param fileId
     */
    public void updateLike(Long fileId);

    /**
     * 更新用户文章点踩
     * @param fileId
     */
    public void updateDislike(Long fileId);

    /**
     * 更新sys_files中的点赞数量
     * @param fileId
     */
    public void updateLikeCount(Long fileId);

    /**
     * 更新sys_files中的点踩数
     * @param fileId
     */
    public void updateDislikeCount(Long fileId);



}
