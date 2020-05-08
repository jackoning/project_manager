package com.sinyun.server.entity;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RemoteServerVo {

    private String id;
    /**
     * 项目别名
     */
    private String projectAlias;

    private String projectName;
    /**
     * web或server类型
     */
    private String type;

    private String host;

    private String userName;

}
