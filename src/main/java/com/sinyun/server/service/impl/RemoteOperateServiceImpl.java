package com.sinyun.server.service.impl;

import com.sinyun.server.commons.RemoteCache;
import com.sinyun.server.commons.SystemTypeUtil;
import com.sinyun.server.commons.ssh2.*;
import com.sinyun.server.commons.ssh2.entity.ConnectInfo;
import com.sinyun.server.commons.ssh2.entity.RemoteSession;
import com.sinyun.server.entity.DataModel;
import com.sinyun.server.entity.RemoteServer;
import com.sinyun.server.service.RemoteOperateService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class RemoteOperateServiceImpl implements RemoteOperateService {

    @Value("${remote.localLinuxPath}")
    private String localLinuxPath;

    @Value("${remote.localWinPath}")
    private String localWinPath;

    private String path = "";

    @Override
    public DataModel save(RemoteServer remoteServer) {
        String id = UuidUtil.uuid();
        remoteServer.setId(id);
        CacheManager.put(Constant.KEY_PROJECT + id, remoteServer);
        RemoteCache.CacheWriteJson();
        return DataModel.success();
    }

    @Override
    public DataModel run(String id) {
        DataModel dm = DataModel.success();
        RemoteServer remoteServer = CacheManager.get(Constant.KEY_PROJECT + id, RemoteServer.class);
        String code = remoteServer.getProjectName() + Constant.UNDERLINE +remoteServer.getHost().replace(".", "");
        dm.setData(command(remoteServer.getId(), code));
        return dm;
    }

    @Override
    public DataModel connect(String id) {
        DataModel dm;
        RemoteServer remoteServer = CacheManager.get(Constant.KEY_PROJECT + id, RemoteServer.class);
        ConnectInfo connectInfo = new ConnectInfo();
        BeanUtils.copyProperties(remoteServer, connectInfo);
        RemoteConnectUtil.connect(connectInfo);
        RemoteSession rs = CacheManager.get(Constant.KEY_SESSION + id, RemoteSession.class);
        if (null == rs.getConnection()){
            dm = DataModel.error(rs.getMsg());
        }else {
            dm = DataModel.success(rs.getMsg());
            dm.setData(rs.getSessionId());
        }
        return dm;
    }

    @Override
    public DataModel execute(String id, String cmd) {
        DataModel dm = DataModel.success();
        String str = RemoteOperateUtil.execute(id, cmd);
        dm.setData(str);
        return dm;
    }

    @Override
    public DataModel download (String sessionId, String LocalPath, String remotePath) {
        DataModel dm = DataModel.success();
        Boolean flag = RemoteOperateUtil.download(sessionId, LocalPath, remotePath);
        dm.setData(flag);
        return dm;
    }

    @Override
    public DataModel upload (String sessionId, String LocalPath, String remotePath) {
        DataModel dm = DataModel.success();
        Boolean flag = RemoteOperateUtil.upload(sessionId, LocalPath, remotePath);
        dm.setData(flag);
        return dm;
    }

    @Override
    public DataModel uploadLocal(String id, String remotePath, MultipartFile multipartFile) {
        DataModel dm = DataModel.success();
        String name = multipartFile.getOriginalFilename();
        String prefix = name.substring(0, name.lastIndexOf("."));
        String path = getPath() + File.separator + prefix;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        path = path + File.separator + name;
        File f = new File(path);
        try {
            multipartFile.transferTo(f);
            dm.setData(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dm;
    }

    @Override
    public DataModel delete(String id) {
        DataModel dm = DataModel.success();
        RemoteConnectUtil.close(id);
        CacheManager.remove(Constant.KEY_PROJECT + id);
        dm.setMsg("删除成功！");
        return dm;
    }

    @Override
    public DataModel viewLog(String id) {
        DataModel dm = DataModel.success();
        RemoteServer remoteServer = CacheManager.get(Constant.KEY_PROJECT + id, RemoteServer.class);
        String code = "";
        if (remoteServer.getType().equals(Constant.WEB)) {
            code += Constant.NGINX_LOG + Constant.UNDERLINE + remoteServer.getHost().replace(".", "");
        }
        if (remoteServer.getType().equals(Constant.SERVER)) {
            code += remoteServer.getProjectName() + Constant.LOG + Constant.UNDERLINE + remoteServer.getHost().replace(".", "");
        }
        dm.setData(command(remoteServer.getId(), code));
        return dm;

    }

    @Override
    public DataModel queryList(){
        DataModel dm = DataModel.success();
        List<RemoteServer> list = CacheManager.queryList(Constant.KEY_PROJECT, RemoteServer.class);
        dm.setData(list);
        return dm;
    }

    private String command(String id, String code){
        CommandEnums[] commandEnums = CommandEnums.values();
        for (CommandEnums ce : commandEnums) {
            if (ce.getCode().equals(code)) {
                String cmd = ce.getCommand();
                System.out.println(code + " 待执行命令：" + cmd);
                String str = RemoteOperateUtil.execute(id, cmd);
                return str;
            }
        }
        return "未找到命令！";
    }

    private String getPath(){
        if (null == path || path.isEmpty()) {
            if (SystemTypeUtil.osType(SystemTypeUtil.LINUX)){
                return localLinuxPath;
            }
            if (SystemTypeUtil.osType(SystemTypeUtil.WINDOWS)) {
                return localWinPath;
            }
        }
        return path;
    }
}
