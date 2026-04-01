package com.zmh.ai.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // ==================== 成功 (200xx) ====================
    SUCCESS(20000, "操作成功"),

    // ==================== 客户端错误 (400xx) ====================
    BAD_REQUEST(40000, "请求参数错误"),
    BAD_REQUEST_PARAMS(40001, "请求参数格式错误"),
    BAD_REQUEST_BODY(40002, "请求体格式错误"),
    BAD_REQUEST_MISSING_PARAM(40003, "缺少必需参数"),

    UNAUTHORIZED(40100, "未授权，请先登录"),
    TOKEN_EXPIRED(40101, "Token 已过期"),
    TOKEN_INVALID(40102, "Token 无效或格式错误"),

    FORBIDDEN(40300, "拒绝访问"),
    FORBIDDEN_NO_PERMISSION(40301, "权限不足，无法访问"),
    FORBIDDEN_ACCOUNT_DISABLED(40302, "账号已被禁用"),

    NOT_FOUND(40400, "资源不存在"),
    NOT_FOUND_USER(40401, "用户不存在"),
    NOT_FOUND_DATA(40402, "数据不存在"),
    NOT_FOUND_API(40403, "接口不存在"),

    METHOD_NOT_ALLOWED(40500, "请求方法不支持"),

    CONFLICT(40900, "资源冲突"),
    CONFLICT_DATA_EXISTS(40901, "数据已存在"),
    CONFLICT_VERSION(40902, "版本冲突"),

    TOO_MANY_REQUESTS(42900, "请求过多，请稍后重试"),
    RATE_LIMIT_EXCEEDED(42901, "请求频率超限"),

    // ==================== 服务端错误 (500xx) ====================
    INTERNAL_SERVER_ERROR(50000, "服务器内部错误"),
    INTERNAL_SERVICE_ERROR(50001, "服务处理异常"),
    INTERNAL_DB_ERROR(50002, "数据库操作异常"),
    INTERNAL_CACHE_ERROR(50003, "缓存操作异常"),

    NOT_IMPLEMENTED(50100, "功能尚未实现"),

    SERVICE_UNAVAILABLE(50300, "服务暂时不可用"),
    SERVICE_TIMEOUT(50301, "服务响应超时"),

    // ==================== 业务异常 (600xx) ====================
    BUSINESS_ERROR(60000, "业务异常"),
    BUSINESS_PARAMS_INVALID(60001, "业务参数校验失败"),
    BUSINESS_RULE_VIOLATION(60002, "违反业务规则"),
    BUSINESS_STATUS_ERROR(60003, "业务状态异常"),
    BUSINESS_DATA_PROCESS_ERROR(60004, "业务数据处理失败"),
    BUSINESS_FILE_PROCESS_ERROR(60005, "文件处理失败"),

    // ==================== 认证授权异常 (700xx) ====================
    LOGIN_FAILED(70000, "登录失败"),
    LOGIN_PASSWORD_ERROR(70001, "账号或密码错误"),
    LOGIN_ACCOUNT_DISABLED(70002, "账号已被禁用"),
    LOGIN_TOO_MANY_ATTEMPTS(70003, "登录尝试次数过多，请稍后重试"),

    LOGOUT_FAILED(70100, "退出登录失败"),

    // ==================== 第三方服务异常 (800xx) ====================
    REMOTE_CALL_FAILED(80000, "远程调用失败"),
    REMOTE_CALL_TIMEOUT(80001, "远程调用超时"),
    REMOTE_CALL_SERVICE_UNAVAILABLE(80002, "远程服务不可用"),

    THIRD_PARTY_SERVICE_ERROR(80100, "第三方服务异常"),
    THIRD_PARTY_API_LIMIT(80101, "第三方 API 调用受限");


    private final int code;
    private final String message;

    ErrorCode(int code, String msg) {
        this.code = code;
        this.message = msg;
    }
}
