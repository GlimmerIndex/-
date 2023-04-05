package org.glimmer.service.Impl;


import org.glimmer.mapper.UserLikeFileMapper;
import org.glimmer.service.UserCacheService;
import org.glimmer.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class UserCacheServicelmpl implements UserCacheService {

    @Autowired
    UserLikeFileMapper userLikeFileMapper;
    @Autowired
    RedisCache redisCache;
    @Override
    public void userLikeCache(Long userId){
        List<Long> userLikeFiles = userLikeFileMapper.getFileIdByLikedUser(userId);
        //缓存用户like
        String userKey = "user_like:" + String.valueOf(userId);
        for (Long file : userLikeFiles){
            if (userLikeFileMapper.getIsLikedByLikedUserAndLikedFile(userId,file)==1){
                redisCache.setCacheSetVal(userKey,file);
            }
        }
        redisCache.expire(userKey,30,TimeUnit.MINUTES);//设置缓存过期
    }

    @Override
    public void userDislikeCache(Long userId){
        List<Long> userLikeFiles = userLikeFileMapper.getFileIdByLikedUser(userId);
        //缓存用户dislike
        String userKey = "user_dislike:" + String.valueOf(userId);
        for (Long file : userLikeFiles){
            if(userLikeFileMapper.getIsDislikedByLikedUserAndLikedFile(userId,file)==1){
                redisCache.setCacheSetVal(userKey,file);
            }
        }
        redisCache.expire(userKey,30,TimeUnit.MINUTES);
    }

}
