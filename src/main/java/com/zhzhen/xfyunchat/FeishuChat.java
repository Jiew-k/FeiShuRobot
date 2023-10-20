package com.zhzhen.xfyunchat;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

@Service
public class FeishuChat {


    @Resource
    BigModelNew bigModelNew;

    public String getAuthUrlTest(String question) throws Exception {
        while (true) {
            boolean createWebSocket = "".equals(BigModelNew.NewAnswer);
            if (createWebSocket) {
                bigModelNew.createWebSocket(question);
            } else {
                String msg = BigModelNew.NewAnswer;
                BigModelNew.NewAnswer = "";
                return msg;
            }
        }
    }
}
