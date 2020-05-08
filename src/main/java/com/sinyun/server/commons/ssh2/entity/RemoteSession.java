package com.sinyun.server.commons.ssh2.entity;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.sinyun.server.commons.ssh2.exception.Ssh2Exception;

import java.io.*;
import java.util.Date;

/**
 * @author gongwenjun
 */
public class RemoteSession {

    private String sessionId;

    private SCPClient scpClient;

    private Connection connection;

    private String result;

    private InputStream resultIO;

    private String charSet;

    private String msg;

    private Date passDue;

    public RemoteSession () {
        this.charSet = "utf-8";
    }

    public Date getPassDue() {
        return passDue;
    }

    public void setPassDue(Date currTime) {
        long time = currTime.getTime() + (30 * 1000 * 1000);
        currTime.setTime(time);
        this.passDue = currTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public SCPClient getScpClient() {
        if (null != connection && null == scpClient) {
            try {
                scpClient = connection.createSCPClient();
            } catch (IOException e) {
                this.msg = "SCPClient创建失败！" + e.getMessage();
                throw new Ssh2Exception("SCPClient创建失败！", e);
            }
        }
        return scpClient;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        this.stdoutResult();
        return result;
    }

    public InputStream getResultIO() {
        return resultIO;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }

    public void setResultIO(InputStream resultIO) {
        this.resultIO = resultIO;
    }

    private void stdoutResult() {
        if (null == getResultIO()) {
            this.result = "";
            return;
        }
        InputStream  stdout = new StreamGobbler(getResultIO());
        StringBuffer buffer = new StringBuffer();
        try {
            buffer.append("stdout>");
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, getCharSet()));
            String line;
            while((line=br.readLine()) != null){
                buffer.append(line+"\n");
            }
            this.result = buffer.toString();
        } catch (UnsupportedEncodingException e) {
            this.msg = "解析脚本出错："+e.getMessage();
        } catch (IOException e) {
            this.msg = "解析脚本出错："+e.getMessage();
        }
    }

}
