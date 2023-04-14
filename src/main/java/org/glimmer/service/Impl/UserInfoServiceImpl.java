package org.glimmer.service.Impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.val;
import org.glimmer.domain.ResponseResult;
import org.glimmer.domain.User;
import org.glimmer.mapper.UserMapper;
import org.glimmer.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    UserMapper userMapper;
    /**
     * @return
     */
    @Override
    public ResponseResult getInfoByName(String userId) {
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<User>();
        userLambdaQueryWrapper.eq(User::getUserName,userId);
        val user = userMapper.selectOne(userLambdaQueryWrapper);
        if(Objects.isNull(user)) {
            return new ResponseResult(4016,"未找到用户");
        }
        user.setPassword(null);
        return new ResponseResult(200,"查询到对应用户", user);
    }
}
