package com.zmh.ai.demo.invoke;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.boot.CommandLineRunner;

/**
 * @Description
 * @ClassName SpringAi
 * @Author zmh
 * @Date 2026/4/9 10:52
 */
//@Component
// 取消注释即可在 SpringBoot 项目启动时执行
public class SpringAi implements CommandLineRunner {

    @Resource
    private ChatModel dashscopeChatModel;

    @Override
    public void run(String... args) throws Exception {
        AssistantMessage output = dashscopeChatModel.call(new Prompt("你好，我是zmh"))
                .getResult()
                .getOutput();
        System.out.println(output.getText());
    }

    public void demo(){

        MessageWindowChatMemory chatMemory = MessageWindowChatMemory
                .builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(20)
                .build();


        ChatClient chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem("你是java编程大师，用最精简的话回答我的问题")
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build()// 对话记忆 adviso

                )
                .build();

        String response = chatClient.prompt()
                // 对话时动态设定拦截器参数，比如指定对话记忆的 id 和长度
                .advisors(advisor -> advisor.param("chat_memory_conversation_id", "678")
                        .param("chat_memory_response_size", 100))
                .user("userText")
                .call()
                .content();


    }


}
