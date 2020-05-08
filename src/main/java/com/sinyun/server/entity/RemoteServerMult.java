package com.sinyun.server.entity;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Data
public class RemoteServerMult {

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

    private Integer port;

    private String userName;

    private String password;
    /**
     * 指令集
     */
    private Map<String, String> commands;

    private MultipartFile multipartFile;

}
