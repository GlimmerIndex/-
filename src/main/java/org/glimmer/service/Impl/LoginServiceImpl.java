package org.glimmer.service.Impl;

import org.glimmer.domain.LoginUser;
import org.glimmer.domain.ResponseResult;
import org.glimmer.domain.User;
import org.glimmer.service.LoginService;
import org.glimmer.utils.JwtUtil;
import org.glimmer.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;
    @Value("${glimmer.index.user.token-life}")
    private Integer  tokenLife;
    @Override
    public ResponseResult<HashMap<String, String>> login(User user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if(Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误"); // TODO(Antio2) 抛出一个新的类
        }
        LoginUser loginUser = (LoginUser)authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        redisCache.setCacheObject("login:"+userId, loginUser,tokenLife, TimeUnit.MINUTES);
        HashMap<String,String> map = new HashMap<>();
        map.put("token",jwt);
        return new ResponseResult<HashMap<String, String>>(200,"登陆成功",map);
    }


    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        if(Objects.isNull(loginUser)) {
            throw new RuntimeException("用户未登录");
        }
        Long userid = loginUser.getUser().getId();
        redisCache.deleteObject("login:"+userid);
        return new ResponseResult(200,"注销成功");
    }


}
