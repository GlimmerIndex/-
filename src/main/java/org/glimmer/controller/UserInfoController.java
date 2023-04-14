package org.glimmer.controller;

import org.glimmer.domain.ResponseResult;
import org.glimmer.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {
    @Autowired
    UserInfoService userInfoService;
    @GetMapping("/user/info/{UserId}")
    @PreAuthorize("hasAuthority('user:info:get')")
    ResponseResult getUserInfo(@PathVariable String UserId) {
        return userInfoService.getInfoByName(UserId);
    }
}
