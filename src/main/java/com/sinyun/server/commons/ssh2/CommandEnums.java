package com.sinyun.server.commons.ssh2;

/**
 * @author gongwenjun
 */
public enum CommandEnums {

    /*------------------------------------ web前台 ------------------------------------*/
    /**
     * oaip-biz
     */
    OAIP_BIZ_RUN_1257214166("oaip-biz_run_1257214166", "/opt/tem/deploy.sh oaip-biz start"),

    /**
     * oaip-approval
     */
    OAIP_APPROVAL_RUN_1257214166("oaip-dispatch_run_1257214166", "/opt/tem/deploy.sh oaip-approval start"),

    /**
     * oaip-dispatch
     */
    OAIP_DISPATCH_RUN_1257214166("oaip-dispatch_run_1257214166", "/opt/tem/deploy.sh oaip-dispatch start"),


    NGINX_LOG_1257214166("nginx_log_1257214166", "tail -n 100 /usr/local/nginx/logs/access.log"),


    /*------------------------------------ 后台服务SERVER ------------------------------------*/


    ;

    private String code;

    private String command;

    CommandEnums(String code, String command) {
        this.code = code;
        this.command = command;
    }

    public String getCode() {
        return code;
    }

    public String getCommand() {
        return command;
    }

}
