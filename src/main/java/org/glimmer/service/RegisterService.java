package org.glimmer.service;

import org.glimmer.domain.ResponseResult;
import org.glimmer.domain.User;
import org.springframework.web.bind.annotation.ResponseBody;

public interface RegisterService {
    ResponseResult Register(User user);
    public ResponseResult Active(String activeCode);
}
