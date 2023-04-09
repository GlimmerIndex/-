package org.glimmer.controller;


import io.jsonwebtoken.Claims;
import org.glimmer.domain.ResponseResult;
import org.glimmer.domain.UserLikeFile;
import org.glimmer.service.LikeService;
import org.glimmer.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LikeAndDislikeController {


    @Autowired
    LikeService likeService;

    @PostMapping("/like/pdf")
    @PreAuthorize("hasAuthority('like:pdf')")
    public ResponseResult LikePDF(@RequestBody UserLikeFile userAndFile, @RequestHeader("token") String token){
        Long fileId = userAndFile.getLikedFileId();
        try {
            Claims claims = JwtUtil.parseJWT(token);
            Long userId = Long.valueOf(claims.getSubject());
            if (likeService.like(userId, fileId) == true) {
                return new ResponseResult<>(4020, "点赞成功");
            } else {
                return new ResponseResult<>(4021, "取消点赞");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(4025,"token 非法");
        }
    }


    @PostMapping("/dislike/pdf")
    @PreAuthorize("hasAuthority('dislike:pdf')")
    public ResponseResult DislikePDF(@RequestBody UserLikeFile userAndFile, @RequestHeader("token") String token){
        try {
            Claims claims = JwtUtil.parseJWT(token);
            Long userId = Long.valueOf(claims.getSubject());
            Long fileId = userAndFile.getLikedFileId();
            if (likeService.dislike(userId, fileId)) {
                return new ResponseResult<>(4022, "点踩成功");
            } else {
                return new ResponseResult<>(4023, "取消点踩");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(4025,"token 非法");
        }
    }
}
