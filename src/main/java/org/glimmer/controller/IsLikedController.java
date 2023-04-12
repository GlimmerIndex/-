package org.glimmer.controller;


import io.jsonwebtoken.Claims;
import org.glimmer.domain.ResponseResult;
import org.glimmer.domain.UserLikeFile;
import org.glimmer.service.IslikedService;
import org.glimmer.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class IsLikedController {
    @Autowired
    IslikedService islikedService;

    @GetMapping("/is/liked")
    @PreAuthorize("hasAuthority('is:liked')")
    public ResponseResult IsLiked(@RequestHeader("token") String token, @RequestBody UserLikeFile userLikeFile){
        Long fileId = userLikeFile.getLikedFileId();
        try{
            Claims claims = JwtUtil.parseJWT(token);
            Long userId = Long.valueOf(claims.getSubject());
            if(islikedService.isLiked(fileId,userId)==true){
                return new ResponseResult(4026,"已点赞");
            }else {
                return new ResponseResult<>(4027,"未点赞");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(4025,"token 非法");
        }
    }

    @GetMapping("/is/disliked")
    @PreAuthorize("hasAuthority('is:disliked')")
    public ResponseResult IsDisliked(@RequestHeader("token") String token,@RequestBody UserLikeFile userLikeFile){
        Long fileId = userLikeFile.getLikedFileId();
        try{
            Claims claims = JwtUtil.parseJWT(token);
            Long userId = Long.valueOf(claims.getSubject());
            if(islikedService.isDisliked(fileId,userId)==true){
                return new ResponseResult(4026,"已点踩");
            }else {
                return new ResponseResult<>(4027,"未点踩");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseResult<>(4025,"token 非法");
        }
    }
}
