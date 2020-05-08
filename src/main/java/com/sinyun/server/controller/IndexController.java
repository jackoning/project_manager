package com.sinyun.server.controller;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.UnsupportedEncodingException;

@Controller
public class IndexController {

    @Value("${remote.sginKey}")
    private String sginKey;

    @RequestMapping("/index")
    public String index(){
        return "/index";
    }

    @RequestMapping("/sign")
    public String sign(){
        return "/sign";
    }

    @RequestMapping(value = "/verify", method = RequestMethod.POST)
    public String verify(String sign){
        try {
            sign = new String(Base64Utils.decode(sign.getBytes("UTF-8")), "UTF-8");
            System.out.println(sign);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "/index";
    }
}
