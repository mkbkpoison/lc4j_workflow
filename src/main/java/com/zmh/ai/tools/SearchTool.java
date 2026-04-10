package com.zmh.ai.tools;

import org.springframework.ai.chat.model.ToolContext;

import java.util.function.BiFunction;

/**
 * @Description
 * @ClassName SearchTool
 * @Author zmh
 * @Date 2026/4/9 14:23
 */
public class SearchTool implements BiFunction<String, ToolContext, String> {
    @Override
    public String apply(String query, ToolContext context) {
        // 实现搜索逻辑
        return "搜索结果: " + query;
    }
}