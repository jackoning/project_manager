package com.sinyun.server.commons.ssh2;

import ch.ethz.ssh2.Connection;
import com.sinyun.server.commons.ssh2.entity.ConnectInfo;
import com.sinyun.server.commons.ssh2.entity.RemoteSession;
import com.sinyun.server.commons.ssh2.exception.Ssh2Exception;

import java.io.IOException;

/**
 * @author gongwenjun
 */
public class RemoteConnectUtil {

    public static String connect(ConnectInfo info) {
        Connection conn = new Connection(info.getHost());
        RemoteSession rs = new RemoteSession();
        try {
            conn.connect();
            boolean authResult = conn.authenticateWithPassword(info.getUserName(), info.getPassword());
            if (authResult) {
                rs.setConnection(conn);
                rs.setMsg("登录成功！");
            }else {
                rs.setMsg("登录失败！");
            }
        } catch (IOException e) {
            rs.setMsg("地址：" + info.getHost() +"，连接失败！");
            throw new Ssh2Exception("地址：" + info.getHost() +"，连接失败！", e);
        }
        CacheManager.put(rs.getSessionId(), rs);
        return rs.getSessionId();
    }
}
