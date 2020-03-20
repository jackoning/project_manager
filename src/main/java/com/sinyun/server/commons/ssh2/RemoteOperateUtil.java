package com.sinyun.server.commons.ssh2;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import com.sinyun.server.commons.ssh2.entity.ConnectInfo;
import com.sinyun.server.commons.ssh2.entity.RemoteSession;
import com.sinyun.server.commons.ssh2.exception.Ssh2Exception;

import java.io.*;

/**
 * @author gongwenjun
 */
public class RemoteOperateUtil {

    /**
     * 执行命名
     * @param sessionId
     * @param cmd
     * @return 结果
     */
    public static String execute(String sessionId, String cmd) {
        RemoteSession rs = takeRemoteSession(sessionId);
        if (null != rs) {
            Session session = rs.getSession();
            if (null != session) {
                try {
                    session.execCommand(cmd);
                    rs.setResultIO(session.getStdout());
                    return rs.getResult();
                } catch (IOException e) {
                    rs.setMsg("执行命令失败！" + e.getMessage());
                    throw new Ssh2Exception("执行命令失败！", e);
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

    public static String  localFile(String path, String name, InputStream input) {
        String filePath = path + File.separator + name;
        FileOutputStream output = null;
        try {
            output = new FileOutputStream(filePath);
            byte[] bytes = new byte[2048];
            int len;
            while ((len = input.read(bytes)) != -1) {
                output.write(bytes, 0, len);
            }
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != input) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != output) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    public static RemoteSession takeRemoteSession(String sessionId) {
        RemoteSession rs = CacheManager.get(sessionId, RemoteSession.class);
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
}
