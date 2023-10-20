package com.zhzhen.xfyunchat.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;


@Service
public class FeishuUtils {

    public static final String tokenUrl
            = "https://open.feishu.cn/open-apis/auth/v3/app_access_token/internal/";
    //这个是飞书应用的appid和key，可以在创建的飞书应用中找到
    public static final String appId = "cli_a5a52c41adb9d00c";
    public static final String appSecret = "z5HJr1pZUAYlDwAfqrTnXgyCu6sFhyhe";

    @Resource
    HttpUtil httpUtil;

    public String getTenantAccessToken() {
        // 创建JSON数据
        JSONObject params = new JSONObject();
        params.put("app_id", appId);
        params.put("app_secret", appSecret);
        String jsonInputString = params.toJSONString();
        String response = httpUtil.post(tokenUrl, jsonInputString);
        String tenantAccessToken = JSON.parseObject(String.valueOf(response)).getString("tenant_access_token");
        return tenantAccessToken;
    }

}

