package com.sinyun.server.system;

import com.sinyun.server.commons.RemoteCache;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitRemoteCache implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        RemoteCache.initCache();
    }
}
