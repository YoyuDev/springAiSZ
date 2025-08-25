package com.bbs.spring_chat.pojo;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.w3c.dom.Text;

import java.sql.Timestamp;

@Data
@TableName("spring_ai_chat_memory")
public class Memory {


    @Alias("conversation_id")
    private String conversationId;

    @Alias("content")
    private String content;


    @Alias("type")
    private String type;

    @Alias("timestamp")
    private Timestamp timestamp;
}
