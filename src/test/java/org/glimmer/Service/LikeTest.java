package org.glimmer.Service;


import org.glimmer.service.LikeService;
import org.glimmer.service.UpdateLikeService;
import org.glimmer.utils.RedisCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class LikeTest {

    @Autowired
    LikeService likeService;

    @Autowired
    UpdateLikeService updateLikeService;

    @Autowired
    RedisCache redisCache;

    @Test
    public void likeRedisTest(){
        Long userId = 1637028625444241413L;
        Long fileId = 1642830312792764417L;
        Long userId1 = 1637028625444241413L;
        Long fileId1 = 1642845455069003778L;
        likeService.like(userId,fileId);
        likeService.like(userId1,fileId1);
        String userKey = "user_like:" + Long.toString(userId);
        String fileKey = "file_like:" +Long.toString(fileId);
        String cancelKey = "cancel_like:" + Long.toString(fileId);
        String cancelKey1 = "cancel_like:" + Long.toString(fileId1);
        Set<Long> files = redisCache.getCacheSet(userKey);
        Set<Long> users = redisCache.getCacheSet(fileKey);
        Set<Long> cancelusers = redisCache.getCacheSet(cancelKey);
        Set<Long> cancelusers1 = redisCache.getCacheSet(cancelKey1);
        System.out.println("liked_files:"+files);
        System.out.println("liked_user:"+users);
        System.out.println("cancel_user:"+cancelusers);
        System.out.println("cancel_user1:"+cancelusers1);
//        likeService.like(userId,fileId);
//        likeService.like(userId1,fileId1);
//        files = redisCache.getCacheSet(userKey);
//        users = redisCache.getCacheSet(fileKey);
//        cancelusers = redisCache.getCacheSet(cancelKey);
//        cancelusers1 = redisCache.getCacheSet(cancelKey1);
//        System.out.println("liked_files:"+files);
//        System.out.println("liked_user:"+users);
//        System.out.println("cancel_user:"+cancelusers);
//        System.out.println("cancel_user1:"+cancelusers1);
    }
    @Test
    public void dislikeRedisTest(){
        Long userId = 1637028625444241413L;
        Long fileId = 1642830312792764417L;
        Long fileId1 = 1642845455069003778L;
        likeService.dislike(userId,fileId);
        likeService.dislike(userId,fileId1);
        String userKey = "user_dislike:" + Long.toString(userId);
        String fileKey = "file_dislike:" +Long.toString(fileId);
        String cancelKey = "cancel_dislike:" + Long.toString(fileId);
        String cancelKey1 = "cancel_dislike:" + Long.toString(fileId1);
        Set<Long> files = redisCache.getCacheSet(userKey);
        Set<Long> users = redisCache.getCacheSet(fileKey);
        Set<Long> cancelusers = redisCache.getCacheSet(cancelKey);
        Set<Long> cancelusers1 = redisCache.getCacheSet(cancelKey1);
        System.out.println("disliked_files:"+files);
        System.out.println("disliked_user:"+users);
        System.out.println("canceldis_user:"+cancelusers);
        System.out.println("canceldis_user1:"+cancelusers1);
//        likeService.dislike(userId,fileId);
//        likeService.dislike(userId1,fileId1);
//        files = redisCache.getCacheSet(userKey);
//        users = redisCache.getCacheSet(fileKey);
//        cancelusers = redisCache.getCacheSet(cancelKey);
//        cancelusers1 = redisCache.getCacheSet(cancelKey1);
//        System.out.println("disliked_files:"+files);
//        System.out.println("disliked_user:"+users);
//        System.out.println("canceldis_user:"+cancelusers);
//        System.out.println("canceldis_user1:"+cancelusers1);
    }


    @Test
    public void updateLikeTest(){
        Long fileId = 1642830312792764417L;
        Long fileId1 = 1642845455069003778L;
        String fileKey = "file_like:" +Long.toString(fileId);
        String cancelKey = "cancel_like:" + Long.toString(fileId);
        String cancelKey1 = "cancel_like:" + Long.toString(fileId1);
        Set<Long> users = redisCache.getCacheSet(fileKey);
        Set<Long> cancelusers = redisCache.getCacheSet(cancelKey);
        Set<Long> cancelusers1 = redisCache.getCacheSet(cancelKey1);
        System.out.println("liked_user:"+users);
        System.out.println("cancel_user:"+cancelusers);
        System.out.println("cancel_user1:"+cancelusers1);
        updateLikeService.updateLike(fileId);
        updateLikeService.updateLike(fileId1);
        updateLikeService.updateDislike(fileId);
        updateLikeService.updateDislike(fileId1);
        updateLikeService.updateDislikeCount(fileId);
        updateLikeService.updateDislikeCount(fileId1);
        updateLikeService.updateLikeCount(fileId);
        updateLikeService.updateLikeCount(fileId1);
    }

}
