package org.glimmer.service;

import org.glimmer.domain.UserLikeFile;

public interface UserCacheService {


    /**
     * 将用户点赞信息缓存至redis
     * @param userId
     * @return
     */
    public void userLikeCache(Long userId);


    public void userDislikeCache(Long userId);

}
