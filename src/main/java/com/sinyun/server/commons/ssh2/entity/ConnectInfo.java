package com.sinyun.server.commons.ssh2.entity;

import com.sinyun.server.commons.ssh2.exception.Ssh2Exception;

/**
 * @author gongwenjun
 */
public class ConnectInfo {

    private String host="125.72.14.166";

    private Integer port=22;

    private String userName="root";

    private String password="Guanli@2019";

    private String winPath="D:/tem";

    private String linuxPath="/opt/tmp";

    private final String WINDOWS = "windows";

    private final String LINUX = "linux";

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWinPath() {
        return winPath;
    }

    public void setWinPath(String winPath) {
        this.winPath = winPath;
    }

    public String getLinuxPath() {
        return linuxPath;
    }

    public void setLinuxPath(String linuxPath) {
        this.linuxPath = linuxPath;
    }

    public String getPath() {
        if (osType(WINDOWS)){
            return winPath;
        }else if (osType(LINUX)){
            return linuxPath;
        }else {
            throw new Ssh2Exception("未知系统异常！");
        }
    }

    private boolean osType(String type){
        String str = System.getProperty("os.name");
        if (str.toLowerCase().contains(type)){
            return true;
        }
        return false;
    }
}
