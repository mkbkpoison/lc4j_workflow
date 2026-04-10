package com.zmh.ai.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @ClassName AiCodeHelper
 * @Author zmh
 * @Date 2026/4/2 10:34
 */

@Service
@Slf4j
public class AiCodeHelper {

//    @Resource
//    private ChatModel qwenChatModel;
//
//    public String chat(String message) {
//
//        UserMessage userMessage1 = UserMessage.builder().build();
//        return getResponse(userMessage);
//    }
//
//    public String sendWithPic(){
//        UserMessage userMessage = UserMessage.from(
//                TextContent.from("描述图片"),
//                ImageContent.from("https://www.codefather.cn/logo.png")
//
//        );
//        return getResponse(userMessage);
//    }
//
//    public String getResponse(UserMessage userMessage) {
//        ChatResponse chatResponse = qwenChatModel.chat(userMessage);
//        AiMessage aiMessage = chatResponse.aiMessage();
//        log.info("AI 输出：" + aiMessage.toString());
//        return aiMessage.text();
//    }
}
