package com.sinyun.server.commons.ssh2;

import java.util.UUID;


    public class UuidUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

}
