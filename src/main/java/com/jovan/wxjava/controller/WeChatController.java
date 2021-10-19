package com.jovan.wxjava.controller;

import com.google.common.collect.Maps;
import com.jovan.wxjava.entity.RestResult;
import com.jovan.wxjava.entity.RestResultGenerator;
import com.jovan.wxjava.protocol.LoginProtocol;
import com.jovan.wxjava.service.WeChatService;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import javax.validation.Valid;

/**
 * @author Jovan
 * @create 2019/8/12
 */
@RestController
public class WeChatController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WeChatService weChatService;

    /**
     * 获取微信登陆二维码地址
     */
    @GetMapping("/wechat-login/qrcode-url")
    public RestResult getQRCodeUrl() {
        return RestResultGenerator.createOkResult(weChatService.getQRCodeUrl());
    }

    /**
     * 微信扫码回调处理
     * 注意：该操作需要由前端页面发起，并且携带扫码返回的code及state参数
     */
    @GetMapping("/wechat-login/callback")
    public Map<String,Object> wxCallBack(@RequestParam String code, @RequestParam String state) {
    	Map<String,Object> map = Maps.newHashMap();
    	map.put("success", false);
    	map.put("msg", "cannot get wxUser.");
    	WxMpUser wxUser = weChatService.wxCallBack(code,state);
        if (wxUser==null) {
        	map.put("success", false);
        	map.put("msg", "cannot get wxUser.");
        } else {
        	map.put("success", true);
        	map.put("msg", "got wxUser successfully.");
        	map.put("data", wxUser);
        }
        return map;
    }
}
