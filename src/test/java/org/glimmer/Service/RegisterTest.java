package org.glimmer.Service;

import org.glimmer.domain.User;
import org.glimmer.service.RegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class RegisterTest {
    @Autowired
    JavaMailSender javaMailSender;
    @Test
    void SendTest() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("测试邮件");
        message.setText("邮件成功发送");
        message.setTo("1219935161@qq.com");
        message.setFrom("antio2@qq.com");

        javaMailSender.send(message);
    }
    @Autowired
    RegisterService registerService;

    @Test
    void RegisterTest(){
        User user = new User();
        user.setEmail("1219935161@qq.com");
        user.setUserName("Anti");
        System.out.println(registerService.Register(user));
    }
    @Test
    void RegisterTest2(){
        User user = new User();
        user.setEmail("antio2@foxmail.com");
        user.setPassword("1234");
        user.setUserName("Anti");
        System.out.println(registerService.Register(user));
    }
    @Test
    void RegisterTest3(){
        User user = new User();
        user.setEmail("antio2@foxmail.com");
        user.setPassword("1234");
        user.setUserName("anti_test");
        System.out.println(registerService.Register(user));
    }
    @Test
    void ActiveCode() {
        String code = new String("6fe3d2be2418438b81fd5629002a8d94");
        System.out.println(registerService.Active(code));
    }
}
