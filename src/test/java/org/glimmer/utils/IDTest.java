package org.glimmer.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IDTest {
    @Test
    public void GetId() {
        System.out.println(IDUtils.getUUID());
        System.out.println(IDUtils.getUUID());
        System.out.println(IDUtils.getUUID());
    }
}
