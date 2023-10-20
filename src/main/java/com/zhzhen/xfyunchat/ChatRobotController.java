package com.zhzhen.xfyunchat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhzhen.xfyunchat.dto.FeishuResponse;
import com.zhzhen.xfyunchat.util.FeishuUtils;
import com.zhzhen.xfyunchat.util.HttpUtil;
import com.zhzhen.xfyunchat.util.RequestIdSet;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

@RestController
@RequestMapping("/xhFeishu")
public class ChatRobotController {

    @Resource
    FeishuChat feishuChat;
    @Resource
    FeishuUtils feishuUtils;
    @Resource
    HttpUtil httpUtil;

    @PostMapping(value = "/message")
    public void message(@RequestBody String body) throws Exception {
//        FeishuEventParams feishuEventParams = JSON.parseObject(body, FeishuEventParams.class);
//        FeishuEventDTO eventDTO = new FeishuEventDTO();
//        eventDTO.setChallenge(feishuEventParams.getChallenge());
//        return eventDTO;
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONObject header = jsonObject.getJSONObject("header");
        String eventType = header.getString("event_type");
        String eventId = header.getString("event_id");
        // 根据内存记录的消息事件id去重
        if (RequestIdSet.requestIdSet.contains(eventId)) {
            //log.warn("重复请求，requestId:{}", requestId);
            return ;
        }
        RequestIdSet.requestIdSet.add(eventId);
        String answe = "";
        FeishuResponse feishuResponse = new FeishuResponse();
        if ("im.message.receive_v1".equals(eventType)) {
            JSONObject event = jsonObject.getJSONObject("event");
            JSONObject message = event.getJSONObject("message");
            String messageType = message.getString("message_type");
            if ("text".equals(messageType)) {
                String messageId = message.getString("message_id");
                String content = message.getString("content");
                JSONObject contentJson = JSON.parseObject(content);
                String text = contentJson.getString("text");
                feishuResponse.setMessageId(messageId);
                feishuResponse.setQuery(text);
                answe = feishuChat.getAuthUrlTest(text);
            }
        }
        String url1 = String.format("https://open.feishu.cn/open-apis/im/v1/messages/%s/reply",
                feishuResponse.getMessageId());
        String tenantAccessToken = feishuUtils.getTenantAccessToken();
        // 创建JSON数据
        JSONObject params = new JSONObject();
        Random random = new Random();
        // 生成随机的十位数
        long randomNumber = (long) (random.nextDouble() * 9_000_000_000L + 1_000_000_000L);
        params.put("uuid", String.valueOf(randomNumber));
        params.put("msg_type", "text");
        JSONObject content = new JSONObject();
        content.put("text", answe);
        params.put("content", content.toJSONString());
        String jsonInputString = params.toJSONString();
        httpUtil.post(url1, jsonInputString, tenantAccessToken);
    }
}
