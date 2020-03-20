package com.sinyun.server.commons;

public class SystemTypeUtil {

    public static final String LINUX = "linux";

    public static final String WINDOWS = "windows";


    public static String osType(){
        String str = System.getProperty("os.name");
        if (str.toLowerCase().contains(LINUX)){
            return LINUX;
        }
        if (str.toLowerCase().contains(WINDOWS)){
            return WINDOWS;
        }
        return "";
    }

    public static boolean osType(String name){
        String str = System.getProperty("os.name");
        if (str.toLowerCase().contains(name)){
            return true;
        }
        return false;
    }
}
