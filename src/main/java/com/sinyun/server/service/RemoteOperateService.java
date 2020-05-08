package com.sinyun.server.service;

import com.sinyun.server.entity.DataModel;
import com.sinyun.server.entity.RemoteServer;
import org.springframework.web.multipart.MultipartFile;

public interface RemoteOperateService {

    DataModel save(RemoteServer remoteServer);

    DataModel run(String id);

    DataModel connect(String id);

    DataModel execute(String id, String cmd) ;

    DataModel download (String sessionId, String LocalPath, String remotePath);

    DataModel upload (String sessionId, String LocalPath, String remotePath);

    DataModel uploadLocal (String id, String remotePath, MultipartFile multipartFile);

    DataModel delete(String id);

    DataModel viewLog(String id);

    DataModel queryList();
}
