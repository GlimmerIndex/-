package org.glimmer.mapper;

import org.glimmer.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MapperTest {
    @Autowired
    UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Test
    void SelectTest(){
        List<User>users = userMapper.selectList(null);
        System.out.println(users);
    }
    @Test
    public void testselectPermsByUserId(){
        System.out.println(menuMapper.selectPermsByUserId(2L));
    }
    @Test
    public void getNextKey(){
        System.out.println(userMapper.getNextId());
    }
}
