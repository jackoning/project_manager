package com.sinyun.server.commons;

import com.sinyun.server.commons.ssh2.RemoteConnectUtil;
import com.sinyun.server.commons.ssh2.RemoteOperateUtil;
import com.sinyun.server.commons.ssh2.entity.ConnectInfo;

import java.io.File;

public class Test {

    public static void main(String[] args) {
        ConnectInfo info = new ConnectInfo();
        String id = RemoteConnectUtil.connect(info);
        String result = RemoteOperateUtil.execute(id, "cat /opt/logs.log");
        System.out.println(result);

        RemoteOperateUtil.download(id, "D:"+ File.separator+"logs1.log", "/opt/");
        System.out.println(RemoteOperateUtil.takeRemoteSession(id).getResult());
    }
}
