package org.glimmer.controller;

import org.glimmer.domain.ResponseResult;
import org.glimmer.domain.User;
import org.glimmer.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RegisterController {
    @Autowired
    RegisterService registerService;
    @PostMapping("/user/register")
    public ResponseResult Register(@RequestBody User user) {
        return registerService.Register(user);
    }

    @GetMapping("user/active/{activeCode}")
    RedirectView Active(@PathVariable String activeCode) {
        ResponseResult responseResult = registerService.Active(activeCode);
        switch (responseResult.getCode()) {
            case 200:
                return new RedirectView("https://baidu.com"); // TODO(AntiO2) 记得修改这里
            default:
                return new RedirectView("https://bing.com");
        }
    }
}
