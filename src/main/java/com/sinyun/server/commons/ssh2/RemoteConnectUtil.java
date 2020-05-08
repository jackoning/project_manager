package com.sinyun.server.commons.ssh2;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import com.sinyun.server.commons.ssh2.entity.ConnectInfo;
import com.sinyun.server.commons.ssh2.entity.RemoteSession;
import com.sinyun.server.commons.ssh2.exception.Ssh2Exception;
import com.sinyun.server.entity.RemoteServer;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.Date;

/**
 * @author gongwenjun
 */
public class RemoteConnectUtil {


    public static boolean connect(ConnectInfo info) {
        Connection conn = new Connection(info.getHost());
        RemoteSession rs = new RemoteSession();
        rs.setSessionId(info.getId());
        rs.setPassDue(new Date());
        try {
            conn.connect();
            boolean authResult = conn.authenticateWithPassword(info.getUserName(), info.getPassword());
            if (authResult) {
                rs.setConnection(conn);
                rs.setMsg(info.getHost() + "登录成功！");
                CacheManager.put(Constant.KEY_SESSION + rs.getSessionId(), rs);
                System.out.println(rs.getMsg());
                return true;
            }else {
                rs.setMsg(info.getHost() + "登录失败！");
            }
        } catch (IOException e) {
            rs.setMsg("地址：" + info.getHost() +"，连接失败！");
            throw new Ssh2Exception("地址：" + info.getHost() +"，连接失败！", e);
        }
        System.out.println(rs.getMsg());
        return false;
    }

    public static boolean close (String sessionId) {
        RemoteSession remoteSession = CacheManager.get(Constant.KEY_SESSION + sessionId, RemoteSession.class);
        if (null == remoteSession) {
            return true;
        }
        Connection connection = remoteSession.getConnection();
        if (null != connection) {
            connection.close();
        }
        CacheManager.remove(Constant.KEY_SESSION + sessionId);
        return true;
    }

    public static boolean isConnect(String sessionId){
        RemoteSession rs = CacheManager.get(Constant.KEY_SESSION + sessionId, RemoteSession.class);
        if (null != rs) {
            return true;
        } else {
            RemoteServer remoteServer = CacheManager.get(Constant.KEY_PROJECT + sessionId, RemoteServer.class);
            ConnectInfo info = new ConnectInfo();
            BeanUtils.copyProperties(remoteServer, info);
            if (connect(info)){
                return true;
            }
            return false;
        }
    }

}
