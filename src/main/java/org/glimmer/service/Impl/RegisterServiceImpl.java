package org.glimmer.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.glimmer.domain.ResponseResult;
import org.glimmer.domain.User;
import org.glimmer.domain.UserToRole;
import org.glimmer.mapper.UserMapper;
import org.glimmer.mapper.UserToRoleMapper;
import org.glimmer.service.EmailService;
import org.glimmer.service.RegisterService;
import org.glimmer.utils.IDUtils;
import org.glimmer.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class RegisterServiceImpl implements RegisterService {
    @Autowired
    RedisCache redisCache;
    @Value("${email.active-code-time}")
    Integer timeout;
    @Value("${email.url}")
    String url;
    @Autowired
    EmailService emailService;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserToRoleMapper userToRoleMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public ResponseResult Register(User user) {
        user.setStatus("1"); // 禁用
        user.setUserType("1");
        String activeCode = IDUtils.getUUID();
        String email = user.getEmail();
        String userName = user.getUserName();
        String rawPwd = user.getPassword();
        if(email==null||email.equals("")) {
            return new  ResponseResult(4002,"邮箱无效");
        }
        LambdaQueryWrapper<User> queryByEmail = new LambdaQueryWrapper<User>();
        queryByEmail.eq(User::getEmail,email);
        User userExisted = userMapper.selectOne(queryByEmail);
        if(!Objects.isNull(userExisted)) {
            return new ResponseResult(4002,"邮箱已被注册");
        }
        if(Objects.isNull(rawPwd)||rawPwd.equals("")) {
            return new ResponseResult(4005,"密码无效");
        }
        LambdaQueryWrapper<User> queryByUserName = new LambdaQueryWrapper<User>();
        queryByUserName.eq(User::getUserName, userName);
        userExisted = userMapper.selectOne(queryByUserName);
        if(!Objects.isNull(userExisted)) {
            return new ResponseResult(4003,"用户名已被注册");
        }

        user.setPassword(passwordEncoder.encode(rawPwd)); // 加密密码
        redisCache.setCacheObject("active-code:"+activeCode,user,timeout, TimeUnit.SECONDS); // 设置300秒过期
        String text =
                "<a href=\""+url+"/"+activeCode+"\">激活请点击:"+activeCode+"</a>" +
                        "<br><p>激活码将在5分钟后失效</p>";
        emailService.quickSendEmail(user.getEmail(),text);
        return new ResponseResult(200,"用户信息提交成功，请前往邮箱激活");
    }

    public ResponseResult Active(String activeCode) {
        User user = (User)redisCache.getCacheObject("active-code:" + activeCode);
        redisCache.deleteObject("active-code:" + activeCode);
        if(Objects.isNull(user)) {
            return new ResponseResult(4004,"激活码无效或已过期，请尝试重新注册");
        }
        user.setStatus("0");
        try {
            Long nextId = userMapper.getNextId() + 1;
            user.setId(nextId);
            userMapper.insert(user);
            UserToRole userToRole = new UserToRole(nextId, Long.valueOf(user.getUserType()));
            userToRoleMapper.insert(userToRole);

        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(3001,"数据库发生错误，请联系管理员");
        }
        return new ResponseResult(200,"用户激活成功");
    }
}
