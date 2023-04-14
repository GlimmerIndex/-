package org.glimmer.service.Impl;

import org.glimmer.service.IslikedService;
import org.glimmer.service.UserCacheService;
import org.glimmer.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class IslikedServicelmpl implements IslikedService {


    @Autowired
    UserCacheService userCacheService;
    @Autowired
    RedisCache redisCache;
    @Override
    public boolean isLiked(Long fileId,Long userId){
        String userKey = "user_like:" + Long.toString(userId);
        Set<Long> files = redisCache.getCacheSet(userKey);
        if (files == null){
            userCacheService.userLikeCache(userId);//如果刚开始redis中没有点赞数据，则缓存
            files = redisCache.getCacheSet(userKey);
        }
        for (Long file : files){
            if(file.equals(fileId)){
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean isDisliked(Long fileId,Long userId){
        String userKey = "user_dislike:" + Long.toString(userId);
        Set<Long> files = redisCache.getCacheSet(userKey);
        if(files == null){
            userCacheService.userDislikeCache(userId);//如果刚开始redis中没有点赞数据，则缓存
            files = redisCache.getCacheSet(userKey);
        }
        for (Long file : files){
            if(file.equals(fileId)){
                return true;
            }
        }
        return false;
    }
}
