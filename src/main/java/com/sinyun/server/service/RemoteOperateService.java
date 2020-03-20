package com.sinyun.server.service;

import com.sinyun.server.entity.DataModel;

public interface RemoteOperateService {

    DataModel connect();

    DataModel execute(String sessionId, String cmd) ;

    DataModel download (String sessionId, String LocalPath, String remotePath);

    DataModel upload (String sessionId, String LocalPath, String remotePath);
}
