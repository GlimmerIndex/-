package org.glimmer.utils;

import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;

import java.util.UUID;

public class IDUtils {
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}