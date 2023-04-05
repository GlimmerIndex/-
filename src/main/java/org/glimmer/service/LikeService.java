package org.glimmer.service;

public interface LikeService {
    /**
     * 点赞
     * @param userId
     * @param fileId
     * @return 点赞或已点赞返回true
     */
    public boolean like(Long userId,Long fileId);

    /**
     * 取消点赞
     * @param userId
     * @param fileId
     * @return 取消点赞返回true
     */
    public boolean unlike(Long userId,Long fileId);

    /**
     * 点踩
     * @param userId
     * @param fileId
     * @return
     */

    public boolean dislike(Long userId,Long fileId);

    /**
     * 取消点踩
     * @param userId
     * @param fileId
     * @return
     */
    public boolean undislike(Long userId,Long fileId);

    /**
     * 获取点赞数
     * @param fileId
     * @return
     */
    public Long getCacheLikeCount(Long fileId);

    /**
     * 获取点踩数
     * @param fileId
     * @return
     */

    public Long getCacheDislikeCount(Long fileId);
}
