package com.sinyun.server.controller;


import com.sinyun.server.entity.DataModel;
import com.sinyun.server.service.RemoteOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("remoteOperate")
public class RemoteOperateController {

    /**
     * 连接服务器接口
     * 1、执行命令
     * 2、上传（上传本地，部署跳板机，解决window上传linux问题）文件
     * 3、下载文件
     *
     * 上传下需要多线程完成 待完成
     *
     */

    @Autowired
    private RemoteOperateService remoteOperateService;


    @PostMapping("/connect")
    public DataModel connect(){
        return remoteOperateService.connect();
    }

    @PostMapping("/execute")
    DataModel execute(String sessionId, String cmd) {
        return remoteOperateService.execute(sessionId, cmd);
    }

    @PostMapping("/download")
    DataModel download (String sessionId, String localPath, String remotePath) {
        return remoteOperateService.download (sessionId, localPath, remotePath);
    }

    @PostMapping("/uploadRemote")
    DataModel uploadRemote (String sessionId, String localPath, String remotePath){
        return remoteOperateService.upload(sessionId, localPath, remotePath);
    }

    @PostMapping("/uploadLocal")
    DataModel uploadLocal (String sessionId, String localPath, String remotePath, MultipartFile multipartFile){
        DataModel dm = DataModel.success();
        String name = multipartFile.getOriginalFilename();
        String path = localPath + File.separator + name;
        File file = new File(path);
        try {
            multipartFile.transferTo(file);
            dm.setData(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return DataModel.error();
    }


}
