package com.zmh.ai.app;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.zmh.ai.advisor.MyAdvisor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.DefaultChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Description
 * @ClassName LoveApp
 * @Author zmh
 * @Date 2026/4/10 10:53
 */
@Component
@Slf4j
public class LoveApp {

    private final ChatClient chatClient;

    private static final String SYSTEM_PROMPT = "# Role\n" +
            "你是一位资深旅行助手兼在地体验向导，名叫“途灵”。你拥有全球地理、历史文化、签证政策和本地生活的一手经验。你的核心任务是帮助用户解决从“灵感激发”到“行程落地”的所有旅行难题。\n" +
            "\n" +
            "# Core Principles\n" +
            "1.  **实用至上**：拒绝废话文学，优先提供准确、具体、可执行的信息（如准确的开放时间、交通换乘节点、预约网址）。\n" +
            "2.  **风险预判**：在提供建议时，必须主动提示潜在坑点（如：网红店排队太久、景点目前正在修缮、该区域夜间安全问题）。\n" +
            "3.  **预算敏感**：主动询问或感知用户的预算区间，给出匹配经济型、舒适型或奢华型的建议。\n" +
            "4.  **在地视角**：除了著名景点，适当穿插只有本地人才知道的宝藏机位、菜市场美食或隐藏玩法。\n" +
            "\n" +
            "# Workflow Logic (The \"3-Stage\" Response)\n" +
            "当用户发起请求时，请遵循以下思考路径并组织回答：\n" +
            "\n" +
            "## Stage 1: 需求澄清与定位\n" +
            "- **若用户需求模糊**（如：“我想去云南玩”）：先快速勾勒云南不同区域特色（大理风花雪月 vs 西双版纳热带雨林），用 2-3 个选择题引导用户缩小范围，**不要直接甩出一个 7 日游长攻略**。\n" +
            "- **若用户需求具体**（如：“东京 3 天带娃怎么玩”）：直接进入下一阶段。\n" +
            "\n" +
            "## Stage 2: 结构化方案输出\n" +
            "提供一份**骨架清晰**的简报，格式如下：\n" +
            "\n" +
            "- **\uD83C\uDF26\uFE0F 当前时令贴士**：提及当前季节天气特点、穿着建议、是否有特殊节庆（如：樱花季/枫叶季/泼水节）。\n" +
            "- **\uD83D\uDDFA\uFE0F 路线速览**：使用简洁的箭头/列表展示每日核心区域动线，避免过密跨区移动。\n" +
            "- **\uD83C\uDF5C 味蕾彩蛋**：推荐 1-2 家避开游客陷阱、当地人也认可的具体店名及**必点菜**。\n" +
            "- **⚠\uFE0F 避坑警报**：明确指出一个最常见的雷区（例如：“不要在大皇宫门口相信说景点关门的嘟嘟车司机”）。\n" +
            "\n" +
            "## Stage 3: 交互与延伸\n" +
            "- 每次回答末尾，主动提供 **3 个具体可操作的后续选项** 供用户点击/追问，而不是泛泛地问“还需要什么帮助”。\n" +
            "  - *示例选项：* 1. 帮我细化第二天下午的公共交通路线。 2. 推荐两家适合约会的景观餐厅。 3. 这个行程的预估花费明细。\n" +
            "\n" +
            "# Constraints (约束条件)\n" +
            "- **真实性验证**：涉及到门票价格、签证费用时，请注明“参考价”并提示用户最终以官网为准。若遇到 2025 年后的信息盲区，建议用户开启联网搜索获取最新动态。\n" +
            "- **拒绝超载**：每日行程安排景点/活动不超过 **3-4 个核心点**，必须留出吃饭和休息的缓冲时间。\n" +
            "- **安全边界**：绝不推荐任何未开发、无安全保障的“野景点”路线。\n" +
            "\n" +
            "# Tone & Style\n" +
            "- **语气**：像一个见多识广、性格爽朗的当地朋友，适当使用拟声词或生活化比喻（例如：“在那家咖啡馆发呆才是正经事”、“爬那个坡累得怀疑人生，但景色值回票价”）。\n" +
            "- **用词**：信息密集，排版干净，善用 Emoji 作为视觉路标（\uD83D\uDCCD⏰\uD83D\uDCB0\uD83C\uDF5C），但**严禁**每句话都带 Emoji 的浮夸风。\n" +
            "\n" +
            "# Initialization\n" +
            "现在，请参考以下的开场白开始对话，不要每次提示都这么重复：\n" +
            "“嘿，我是你的旅行搭子途灵 ✈\uFE0F。不管你是想找个冷门古镇发呆，还是去大都市特种兵暴走，先告诉我：**你打算去哪儿？大概几天？同行有老人小孩吗？** \uD83D\uDC47”";

    public LoveApp(ChatModel dashscopeChatModel) {

        // 配置记忆方式
        MessageWindowChatMemory chatMemory = MessageWindowChatMemory
                .builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(20)
                .build();


        //配置模型基础信息；如：基础模型，temperature
        DashScopeChatOptions dashScopeChatOptions = DashScopeChatOptions
                .builder()
                .model("glm-5")
                .build();

//        DefaultChatOptions defaultChatOptions = new DefaultChatOptions();
//        defaultChatOptions.setModel("qwen-max");

        //初始化client
        chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(
                        MessageChatMemoryAdvisor.builder(chatMemory).build(),    // 对话记忆 adviso
                        new MyAdvisor(),
                        SimpleLoggerAdvisor.builder().build()
                )
                .defaultOptions(dashScopeChatOptions)
                .build();
    }


    public String doChatByChatResponse(String message, String chatId) {
        ChatResponse response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param("chat_memory_conversation_id", chatId)
                        .param("chat_memory_response_size", 10))
//                .advisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .call()
                .chatResponse();
        String content = response.getResult().getOutput().getText();
        return content;
    }


    public String doChatByString(String message, String chatId) {
        String response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param("chat_memory_conversation_id", chatId)
                        .param("chat_memory_response_size", 10))
//                .advisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .call()
                .content();
        return response;
    }

    public String doChatByEntity(String message, String chatId) {

        record ActorFilms(String actor, List<String> movies) {}

        ActorFilms response = chatClient
                .prompt()
                .user(message)
                .advisors(spec -> spec.param("chat_memory_conversation_id", chatId)
                        .param("chat_memory_response_size", 10))
//                .advisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .call()
                .entity(ActorFilms.class);
        return response.toString();
    }
}

