package com.zmh.ai.tools;

import cn.hutool.json.JSONUtil;
import org.springframework.ai.chat.model.ToolContext;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @Description
 * @ClassName WeatherTool
 * @Author zmh
 * @Date 2026/4/9 14:24
 */
// 定义天气查询工具 - 修改为返回 JSON 格式
public class WeatherTool implements BiFunction<String, ToolContext, String> {
    @Override
    public String apply(String city, ToolContext toolContext) {

        // 返回 JSON 字符串
        return "获取天气";
    }
}
