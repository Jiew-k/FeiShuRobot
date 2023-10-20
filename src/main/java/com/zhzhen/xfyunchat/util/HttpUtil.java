package com.zhzhen.xfyunchat.util;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class HttpUtil {
    /**
     * 发送带json参数的post请求
     * @param urlParam 请求url
     * @param jsonParam json参数
     * @return response 请求相应
     */
    public String post(String urlParam,String jsonParam){
        try {
            // 定义目标URL
            URL url = new URL(urlParam);
            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为POST
            connection.setRequestMethod("POST");
            // 设置请求头，指定内容类型为JSON
            connection.setRequestProperty("Content-Type", "application/json");
            // 启用输入输出流
            connection.setDoOutput(true);
            // 将JSON数据写入输出流
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonParam.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            // 获取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    // 输出响应数据到控制台
                    return response.toString();
                }
            } else {
                throw new RuntimeException("POST请求失败，响应代码: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 发送带json参数的post请求
     * @param urlParam 请求url
     * @param jsonParam json参数
     * @param tokenParam token参数
     * @return response 请求相应
     */
    public String post(String urlParam,String jsonParam,String tokenParam){
        try {
            // 定义目标URL
            URL url = new URL(urlParam);
            // 打开连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // 设置请求方法为POST
            connection.setRequestMethod("POST");
            // 设置请求头，指定内容类型为JSON
            connection.setRequestProperty("Content-Type", "application/json");
            // 添加Authorization头
            connection.setRequestProperty("Authorization", "Bearer " + tokenParam);

            // 启用输入输出流
            connection.setDoOutput(true);
            // 将JSON数据写入输出流
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonParam.getBytes("UTF-8");
                os.write(input, 0, input.length);
            }

            // 获取响应
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 读取响应数据
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    // 输出响应数据到控制台
                    return response.toString();
                }
            } else {
                throw new RuntimeException("POST请求失败，响应代码: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
