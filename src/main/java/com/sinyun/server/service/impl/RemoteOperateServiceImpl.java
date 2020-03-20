package com.sinyun.server.service.impl;

import com.sinyun.server.commons.ssh2.CacheManager;
import com.sinyun.server.commons.ssh2.RemoteConnectUtil;
import com.sinyun.server.commons.ssh2.RemoteOperateUtil;
import com.sinyun.server.commons.ssh2.entity.ConnectInfo;
import com.sinyun.server.commons.ssh2.entity.RemoteSession;
import com.sinyun.server.entity.DataModel;
import com.sinyun.server.service.RemoteOperateService;
import org.springframework.stereotype.Service;

@Service
public class RemoteOperateServiceImpl implements RemoteOperateService {

    @Override
    public DataModel connect() {
        DataModel dm;
        String id = RemoteConnectUtil.connect(new ConnectInfo());
        RemoteSession rs = CacheManager.get(id, RemoteSession.class);
        if (null == rs.getConnection()){
            dm = DataModel.error(rs.getMsg());
        }else {
            dm = DataModel.success(rs.getMsg());
            dm.setData(rs.getSessionId());
        }
        return dm;
    }

    @Override
    public DataModel execute(String sessionId, String cmd) {
        DataModel dm = DataModel.success();
        String str = RemoteOperateUtil.execute(sessionId, cmd);
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



}
