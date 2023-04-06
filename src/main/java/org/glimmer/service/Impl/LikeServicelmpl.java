package org.glimmer.service.Impl;

import org.apache.poi.ss.formula.functions.T;
import org.glimmer.mapper.UserLikeFileMapper;
import org.glimmer.service.LikeService;
import org.glimmer.service.UserCacheService;
import org.glimmer.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class LikeServicelmpl implements LikeService {


    @Autowired
    UserCacheService userCacheService;
    @Autowired
    RedisCache redisCache;
    @Override
    public boolean like(Long userId,Long fileId){
        String userKey = "user_like:" + Long.toString(userId);
        String fileKey = "file_like:" + Long.toString(fileId);
        redisCache.expire(fileKey,60, TimeUnit.MINUTES);
        String cancelKey = "cancel_like:" + Long.toString(fileId);
        redisCache.expire(cancelKey,60,TimeUnit.MINUTES);
        Set<Long> files = redisCache.getCacheSet(userKey);
        if (files == null){
            userCacheService.userLikeCache(userId);//如果刚开始redis中没有点赞数据，则缓存
            files = redisCache.getCacheSet(userKey);
        }
        for (Long file : files){
            if(file.equals(fileId)){
                return unlike(userId,fileId);
            }
        }
        redisCache.setCacheSetVal(fileKey,userId);
        redisCache.setCacheSetVal(userKey,fileId);
        redisCache.removeCacheSetVal(cancelKey,userId);//删除取消赞，若不存在则不会进行任何操作
        return true;
    }
    @Override
    public boolean unlike(Long userId,Long fileId){
        String userKey = "user_like:" + Long.toString(userId);
        String fileKey = "file_like:" + Long.toString(fileId);
        String cancelKey = "cancel_like:" + Long.toString(fileId);
        redisCache.setCacheSetVal(cancelKey,userId);
        redisCache.removeCacheSetVal(userKey,fileId);
        redisCache.removeCacheSetVal(fileKey,userId);
        return false;
    }
    @Override
    public boolean dislike(Long userId,Long fileId){
        String userKey = "user_dislike:" + Long.toString(userId);
        String fileKey = "file_dislike:" + Long.toString(fileId);
        redisCache.expire(fileKey,60, TimeUnit.MINUTES);
        String cancelKey = "cancel_dislike:" + Long.toString(fileId);
        redisCache.expire(cancelKey,60,TimeUnit.MINUTES);
        Set<Long> files = redisCache.getCacheSet(userKey);
        if(files == null){
            userCacheService.userDislikeCache(userId);//如果刚开始redis中没有点赞数据，则缓存
            files = redisCache.getCacheSet(userKey);
        }
        for (Long file : files){
            if(file.equals(fileId)){
                return undislike(userId,fileId);
            }
        }
        redisCache.setCacheSetVal(userKey,fileId);
        redisCache.setCacheSetVal(fileKey,userId);
        redisCache.removeCacheSetVal(cancelKey,userId);
        return true;
    }
    @Override
    public boolean undislike(Long userId,Long fileId){
        String userKey = "user_dislike:" + Long.toString(userId);
        String fileKey = "file_dislike:" + Long.toString(fileId);
        String cancelKey = "cancel_dislike:" + Long.toString(fileId);
        redisCache.setCacheSetVal(cancelKey,userId);
        redisCache.removeCacheSetVal(userKey,fileId);
        redisCache.removeCacheSetVal(fileKey,userId);
        return false;
    }
    @Override
    public Long getCacheLikeCount(Long fileId){
        String fileKey = "file_like:" + String.valueOf(fileId);
        return redisCache.getCacheSetNumber(fileKey);
    }
    @Override
    public Long getCacheDislikeCount(Long fileId){
        String fileKey = "file_dislike:" + String.valueOf(fileId);
        return redisCache.getCacheSetNumber(fileKey);
    }
}
