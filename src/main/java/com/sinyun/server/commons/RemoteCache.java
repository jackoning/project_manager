package com.sinyun.server.commons;

import com.alibaba.fastjson.JSONObject;
import com.sinyun.server.commons.ssh2.CacheManager;
import com.sinyun.server.commons.ssh2.Constant;
import com.sinyun.server.entity.RemoteServer;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.util.Iterator;
import java.util.Map;

/**
 * @author gongwenjun
 */
public class RemoteCache {

    private static String jsonPath = null;

    private static final String jsonName = "remote.json";

    public static void initCache(){
        String path = getJsonPath() + File.separator + jsonName;
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis, "UTF-8");
            br = new BufferedReader(isr);
            StringBuffer buffer = new StringBuffer();
            String str;
            while ((str = br.readLine()) != null) {
                buffer.append(str);
            }
            if (buffer.length() < 1){
                return;
            }
            JSONObject obj = JSONObject.parseObject(buffer.toString());
            Iterator<String> iterator =  obj.keySet().iterator();
            String key;
            while (iterator.hasNext()) {
                key = iterator.next();
                JSONObject object = (JSONObject) obj.get(key);
                CacheManager.put(key, JSONObject.parseObject(object.toJSONString(), RemoteServer.class));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != isr) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fis) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void CacheWriteJson(){
        String path = getJsonPath() + File.separator + jsonName;
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStreamWriter osw = null;
        try {
            Map<String, RemoteServer> map = CacheManager.queryMap(Constant.KEY_PROJECT, RemoteServer.class);
            if (map.size() < 1){
                return;
            }
            osw = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            osw.write(JSONObject.toJSONString(map));
            osw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (null != osw) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static String getJsonPath(){
        if (null == jsonPath || jsonPath.isEmpty()) {
            try {
                String path = new File(ResourceUtils.getURL("classpath:").getPath()).getParentFile().getParentFile().getParent();
                if (SystemTypeUtil.osType(SystemTypeUtil.LINUX)) {
                    path = path.replace("file:", "");
                }
                path += File.separator + "json";
                File file = new File(path);
                if (!file.exists()){
                    file.mkdirs();
                }
                System.out.println("json文件路径：" + path);
                jsonPath = path;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return jsonPath;
    }
}
