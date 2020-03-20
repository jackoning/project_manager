package com.sinyun.server.entity;

import lombok.Data;

@Data
public class DataModel {

    private Integer code;

    private String msg;

    private Object data;

    public static DataModel success(){
        DataModel dm = new DataModel();
        dm.setCode(200);
        dm.setMsg("操作成功！");
        return dm;
    }

    public static DataModel success(String msg){
        DataModel dm = new DataModel();
        dm.setCode(200);
        dm.setMsg("");
        return dm;
    }

    public static DataModel error(){
        DataModel dm = new DataModel();
        dm.setCode(500);
        dm.setMsg("操作失败！");
        return dm;
    }

    public static DataModel error(String msg){
        DataModel dm = new DataModel();
        dm.setCode(500);
        dm.setMsg(msg);
        return dm;
    }

}
