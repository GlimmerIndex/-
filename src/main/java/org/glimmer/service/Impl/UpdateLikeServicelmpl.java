package org.glimmer.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.builder.ToStringSummary;
import org.glimmer.domain.UserLikeFile;
import org.glimmer.mapper.PDFFilesMapper;
import org.glimmer.mapper.UserLikeFileMapper;
import org.glimmer.service.UpdateLikeService;
import org.glimmer.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.glimmer.domain.UserLikeFile;

import java.util.List;
import java.util.Set;

@Service
public class UpdateLikeServicelmpl implements UpdateLikeService {

    @Autowired
    UserLikeFileMapper userLikeFileMapper;

    @Autowired
    RedisCache redisCache;

    @Autowired
    PDFFilesMapper pdfFilesMapper;

    @Override
    public void updateLike(Long fileId){
        String fileKey = "user_like:" + Long.toString(fileId);
        Set<Long> users = redisCache.getCacheSet(fileKey);//赞

        String cancelKey = "cancel_like" + Long.toString(fileId);
        Set<Long> cancelusers = redisCache.getCacheSet(cancelKey);//取消赞

        UserLikeFile userLikeFile = new UserLikeFile();
        userLikeFile.setLikedFileId(fileId);
        //更新点赞数据
        for (Long user : users){
            if(userLikeFileMapper.checkUserFileExisted(user,fileId)>0) {
                userLikeFileMapper.updateIsLiked(1, fileId, user);//如果用户和文件的关系已经存在，则更新
            }else{
                //设置user和file的关系
                userLikeFile.setIsLiked(1);
                userLikeFile.setLikedUserId(user);
                try{
                    userLikeFileMapper.insert(userLikeFile);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        //更新取消赞
        for (Long user : cancelusers){
            userLikeFileMapper.updateIsLiked(0,fileId,user);
        }
    }

    @Override
    public void updateDislike(Long fileId){
        String fileKey = "user_like:" + Long.toString(fileId);
        Set<Long> users = redisCache.getCacheSet(fileKey);

        String cancelKey = "cancel_dislike:" + Long.toString(fileId);
        Set<Long> cancelUsers = redisCache.getCacheSet(cancelKey);

        UserLikeFile userLikeFile = new UserLikeFile();
        userLikeFile.setLikedFileId(fileId);
        for (Long user : users){
            if(userLikeFileMapper.checkUserFileExisted(user,fileId)>0) {
                userLikeFileMapper.updateDisLiked(1, fileId, user);//如果用户和文件的关系已经存在，则更新
            }else{
                //设置user和file的关系
                userLikeFile.setIsDisliked(1);
                userLikeFile.setLikedUserId(user);
                try{
                    userLikeFileMapper.insert(userLikeFile);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        //更新取消赞
        for (Long user : cancelUsers){
            userLikeFileMapper.updateDisLiked(0,fileId,user);
        }


    }

    @Override
    public void updateLikeCount(Long fileId){
        Long likeCount = userLikeFileMapper.getUserLikeFileCount(fileId,1);
        pdfFilesMapper.updateFileLikedCount(likeCount,fileId);

    }
    @Override
    public void updateDislikeCount(Long fileId){
        Long dislikeCount = userLikeFileMapper.getUserDislikeFileCount(fileId,1);
        pdfFilesMapper.updateFileDislikeCount(dislikeCount,fileId);
    }

}
