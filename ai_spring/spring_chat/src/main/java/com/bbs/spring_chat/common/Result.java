package com.bbs.spring_chat.common;

public class Result<T> {
    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 默认构造函数
    public Result() {
    }

    // 带数据的构造函数（用于自定义构造Result对象）
    public Result(T data) {
        this.data = data;
    }

    // 成功响应的静态方法，不带数据
    public static Result<?> success() {
        Result result = new Result<>();
        result.setCode("200");
        result.setMsg("成功");
        return result;
    }

    // 成功响应的静态方法，带数据
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("200");
        result.setMsg("成功");
        return result;
    }

    // 错误响应的静态方法
    public static Result<?> error(String code, String msg) {
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }




    }
