package com.zmh.ai.demo.invoke;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.checkpoint.savers.MemorySaver;
import com.alibaba.cloud.ai.graph.exception.GraphRunnerException;
import com.zmh.ai.tools.SearchTool;
import com.zmh.ai.tools.WeatherTool;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.function.FunctionToolCallback;


/**
 * @Description
 * @ClassName SimpleAgent
 * @Author zmh
 * @Date 2026/4/9 11:14
 */
public class SimpleAgent {

    public void getResponse() throws GraphRunnerException {
        // 初始化 ChatModel
        DashScopeApi dashScopeApi = DashScopeApi.builder()
                .apiKey("sk-e6276010e9f94f7dad21e3684d38b011")
                .build();

        DashScopeChatOptions dashScopeChatOptions = DashScopeChatOptions.builder()
                .model("qwen-max")
                .build();

        ChatModel chatModel = DashScopeChatModel.builder()
                .dashScopeApi(dashScopeApi)
                .defaultOptions(dashScopeChatOptions)
                .build();

        ToolCallback weatherTool = FunctionToolCallback.builder("get_weather", new WeatherTool())
                .description("Get weather for a given city")
                .inputType(String.class)
                .build();

        ToolCallback searchTool = FunctionToolCallback.builder("search", new SearchTool())
                .description("搜索工具")
                .inputType(String.class)
                .build();

        // 创建 agent
        ReactAgent agent = ReactAgent.builder()
                .name("weather_agent")
                .model(chatModel)
                .tools(weatherTool)
                .tools(searchTool)
                .systemPrompt("You are a helpful assistant")
                .saver(new MemorySaver())
                .build();

        // 运行 agent
        AssistantMessage response = agent.call("what is the weather in San Francisco");
        System.out.println(JSONUtil.toJsonStr(response));
    }


}