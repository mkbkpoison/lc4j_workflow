package com.zmh.ai.advisor;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;

import java.util.List;

/**
 * @Description
 * @ClassName MyChatMemoryAdvisor
 * @Author zmh
 * @Date 2026/4/15 17:12
 */
public class MyChatMemoryAdvisor implements ChatMemory {
    @Override
    public void add(String conversationId, List<Message> messages) {
        //根据conversationId存入数据库或者redis
    }

    @Override
    public List<Message> get(String conversationId) {
        //从数据库或redis读取
        return List.of();
    }

    @Override
    public void clear(String conversationId) {

    }
}
