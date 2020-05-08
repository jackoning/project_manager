package com.sinyun.server.system;

import com.sinyun.server.commons.ssh2.CacheManager;
import com.sinyun.server.commons.ssh2.Constant;
import com.sinyun.server.commons.ssh2.RemoteConnectUtil;
import com.sinyun.server.commons.ssh2.entity.RemoteSession;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.DateUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class CacheManageTask {

    @Scheduled(cron = "0 0/1 * * * ?")
    public void clearPassDueSeesion(){
        List<RemoteSession> list = CacheManager.queryList(Constant.KEY_SESSION, RemoteSession.class);
        if (null == list || list.isEmpty()) {
            return;
        }
        Date date = new Date();
        for (RemoteSession rs : list) {
            if (date.after(rs.getPassDue())) {
                RemoteConnectUtil.close(rs.getSessionId());
            }
        }
    }

}
