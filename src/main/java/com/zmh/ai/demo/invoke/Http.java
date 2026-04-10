package com.zmh.ai.demo.invoke;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import okhttp3.*;

import java.io.IOException;

/**
 * @Description
 * @ClassName http
 * @Author zmh
 * @Date 2026/4/9 10:09
 */
public class Http {

    // 注意：实际使用时请从环境变量或配置文件中获取
    private static final String API_KEY = "sk-e6276010e9f94f7dad21e3684d38b011";
    private static final String API_URL = "https://dashscope.aliyuncs.com/api/v1/services/aigc/text-generation/generation";

    public static String callQwen(String userMessage) throws IOException {
        OkHttpClient client = new OkHttpClient();
        ObjectMapper mapper = new ObjectMapper();

        // 构建请求体 JSON
        ObjectNode requestBody = mapper.createObjectNode();
        requestBody.put("model", "qwen-plus");

        // 构建 messages 数组
        ObjectNode input = mapper.createObjectNode();
        var messages = mapper.createArrayNode();

        // system message
        ObjectNode systemMsg = mapper.createObjectNode();
        systemMsg.put("role", "system");
        systemMsg.put("content", "You are a helpful assistant.");
        messages.add(systemMsg);

        // user message
        ObjectNode userMsg = mapper.createObjectNode();
        userMsg.put("role", "user");
        userMsg.put("content", userMessage);
        messages.add(userMsg);

        input.set("messages", messages);
        requestBody.set("input", input);

        // 构建 parameters
        ObjectNode parameters = mapper.createObjectNode();
        parameters.put("result_format", "message");
        requestBody.set("parameters", parameters);

        // 构建 HTTP 请求
        Request request = new Request.Builder()
                .url(API_URL)
                .header("Authorization", "Bearer " + API_KEY)
                .header("Content-Type", "application/json")
                .post(RequestBody.create(
                        requestBody.toString(),
                        MediaType.parse("application/json")
                ))
                .build();

        // 执行请求
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            String responseBody = response.body().string();

            // 解析响应，提取 assistant 的回复
            return extractAssistantMessage(responseBody);
        }
    }

    private static String extractAssistantMessage(String jsonResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        var root = mapper.readTree(jsonResponse);

        // 提取 assistant 的回复内容
        // 响应结构: output.choices[0].message.content
        return root.path("output")
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();
    }

    public static void main(String[] args) throws IOException {
        String h = callQwen("你是谁");
        System.out.println(h);
    }

}
