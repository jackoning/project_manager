package com.sinyun.server.controller;


import com.sinyun.server.entity.DataModel;
import com.sinyun.server.entity.RemoteServer;
import com.sinyun.server.service.RemoteOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PostMapping("/save")
    public DataModel save(@RequestBody RemoteServer remoteServer){
        return remoteOperateService.save(remoteServer);
    }

    @PostMapping("/run")
    public DataModel run(String id){
        return remoteOperateService.run(id);
    }

    //@GetMapping("/connect")
    public DataModel connect(String id){
        return remoteOperateService.connect(id);
    }

    //@PostMapping("/execute")
    DataModel execute(String id, String cmd) {
        return remoteOperateService.execute(id, cmd);
    }

    //@PostMapping("/download")
    DataModel download (String sessionId, String localPath, String remotePath) {
        return remoteOperateService.download (sessionId, localPath, remotePath);
    }

    //@PostMapping("/uploadRemote")
    DataModel uploadRemote (String sessionId, String localPath, String remotePath){
        return remoteOperateService.upload(sessionId, localPath, remotePath);
    }

    @PostMapping("/uploadLocal")
    DataModel uploadLocal (String id, String remotePath, MultipartFile multipartFile){
       return remoteOperateService.uploadLocal(id, remotePath, multipartFile);
    }

    @GetMapping("/delete")
    public DataModel delete(String id){
        return remoteOperateService.delete(id);
    }

    @GetMapping("/viewLog")
    public DataModel viewLog(String id){
        return remoteOperateService.viewLog(id);
    }

    @GetMapping("/queryList")
    public DataModel queryList(){
        return remoteOperateService.queryList();
    }
}
