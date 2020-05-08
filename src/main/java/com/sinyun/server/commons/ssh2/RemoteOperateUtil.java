package com.sinyun.server.commons.ssh2;

import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import com.sinyun.server.commons.ssh2.entity.RemoteSession;
import com.sinyun.server.commons.ssh2.exception.Ssh2Exception;

import java.io.*;

/**
 * @author gongwenjun
 */
public class RemoteOperateUtil {

    private static final String[] commonds = {"tail ", "cat "};

    /**
     * 执行命名
     * @param sessionId
     * @param cmd
     * @return 结果
     */
    public static String execute(String sessionId, String cmd) {
        if (!RemoteConnectUtil.isConnect(sessionId)){
            return Constant.COMMAND_RUN_FAILE;
        }
        RemoteSession rs = takeRemoteSession(sessionId);
        if (null != rs) {
            Session session = null;
            try {
                cmd = doConnand(cmd, sessionId);
                session = rs.getConnection().openSession();
                System.out.println("打开会话，执行命令：" + cmd);
                session.execCommand(cmd);
                rs.setResultIO(session.getStdout());
                String str = rs.getResult();
                if (null == str || str.length() < 8){
                    rs.setResultIO(session.getStderr());
                    return rs.getResult();
                }else {
                    return str;
                }
            } catch (IOException e) {
                rs.setMsg(Constant.COMMAND_RUN_FAILE + e.getMessage());
                throw new Ssh2Exception(Constant.COMMAND_RUN_FAILE, e);
            }finally {
                if (null != session) {
                    session.close();
                }
            }
        }
        return rs.getMsg();
    }

    /**
     * 远程上传
     * @param sessionId
     * @param localPath
     * @param remotePath
     */
    public static boolean upload(String sessionId, String localPath, String remotePath){
        if (!RemoteConnectUtil.isConnect(sessionId)){
            return false;
        }
        RemoteSession rs = takeRemoteSession(sessionId);
        boolean flag = false;
        if (null != rs) {
            if (osType("windows")) {

            }
            if (osType("linux")) {
                SCPClient scpClient = rs.getScpClient();
                if (null != scpClient) {
                    try {
                        scpClient.put(localPath, remotePath);
                        return true;
                    } catch (IOException e) {
                        throw new Ssh2Exception("执行命令失败！", e);
                    }
                }
            }
        }
        return false;
    }

    /**
     * 远程下载
     * @param sessionId
     * @param localPath
     * @param remotePath
     */
    public static boolean download(String sessionId, String localPath, String remotePath){
        if (!RemoteConnectUtil.isConnect(sessionId)){
            return false;
        }
        RemoteSession rs = takeRemoteSession(sessionId);
        boolean flag = false;
        if (null != rs) {
            SCPClient scpClient = rs.getScpClient();
            if (null != scpClient) {
                try {
                    scpClient.get(remotePath, localPath);
                    return true;
                } catch (IOException e) {
                    throw new Ssh2Exception("执行命令失败！", e);
                }
            }
        }
        return false;
    }

    public static RemoteSession takeRemoteSession(String sessionId) {
        RemoteSession rs = CacheManager.get(Constant.KEY_SESSION + sessionId, RemoteSession.class);
        if (null == rs) {
            throw new Ssh2Exception("未获RemoteSession取到信息！");
        }
        return rs;
    }

    private static boolean osType(String type){
        String str = System.getProperty("os.name");
        if (str.toLowerCase().contains(type)){
            return true;
        }
        return false;
    }

    private static String doConnand(String cmd, String sessionId){
        boolean isOpen = true;
        for (String str : commonds) {
            if (cmd.contains(str)) {
                isOpen = false;
                break;
            }
        }
        if (isOpen) {
            cmd += ">/opt/tem/" + sessionId + ".log && tail -n 100 /opt/tem/" + sessionId + ".log";
        }
        return cmd;
    }
}
