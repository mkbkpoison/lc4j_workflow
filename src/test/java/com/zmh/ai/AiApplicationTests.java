package com.zmh.ai;

import cn.hutool.core.lang.UUID;
import com.zmh.ai.app.LoveApp;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AiApplicationTests {

    @Resource
    private LoveApp loveApp;

    @Test
    void testChat() {
        String chatId = UUID.randomUUID().toString();
        // 第一轮
        String message = "你好，我是zmh";
        String answer = loveApp.doChatByChatResponse(message, chatId);
        Assertions.assertNotNull(answer);
        // 第二轮
        message = "我想去云南旅游一周，有哪些地方和景点比较好玩";
        answer = loveApp.doChatByChatResponse(message, chatId);
        Assertions.assertNotNull(answer);
//        // 第三轮
//        message = "我的另一半叫什么来着？刚跟你说过，帮我回忆一下";
//        answer = loveApp.doChatByString(message, chatId);
//        Assertions.assertNotNull(answer);
    }


}
