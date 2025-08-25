package com.bbs.spring_chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.bbs.spring_chat.mapper")
@ComponentScan(basePackages = {  // 扫描所有需要的包
        "com.bbs.spring_chat.common",
        "com.bbs.spring_chat.controller",
        "com.bbs.spring_chat.service",
        "com.bbs.spring_chat.mapper",
        "com.bbs.spring_chat.pojo",
        "com.bbs.spring_chat.config",
})
public class SpringChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringChatApplication.class, args);
    }

}
