package org.glimmer.controller;


import org.glimmer.domain.ResponseResult;
import org.glimmer.domain.UserLikeFile;
import org.glimmer.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeAndDislikeController {


    @Autowired
    LikeService likeService;

    @PostMapping("/like/pdf")
    @PreAuthorize("hasAuthority('like:pdf')")
    public ResponseResult LikePDF(@RequestBody UserLikeFile userAndFile){
        Long userId = userAndFile.getLikedUserId();
        Long fileId = userAndFile.getLikedFileId();
        if(likeService.like(userId,fileId)==true){
            return new ResponseResult<>(4020,"点赞成功");
        }else {
            return new ResponseResult<>(4021,"取消点赞");
        }
    }


    @PostMapping("/dislike/pdf")
    @PreAuthorize("hasAuthority('dislike:pdf')")
    public ResponseResult DislikePDF(@RequestBody UserLikeFile userAndFile){
        Long userId = userAndFile.getLikedUserId();
        Long fileId = userAndFile.getLikedFileId();
        if (likeService.dislike(userId,fileId)){
            return new ResponseResult<>(4022,"点踩成功");
        }else {
            return new ResponseResult<>(4023,"取消点踩");
        }
    }
}
