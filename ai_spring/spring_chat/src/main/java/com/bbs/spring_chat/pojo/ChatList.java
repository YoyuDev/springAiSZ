package com.bbs.spring_chat.pojo;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("chat_list")
public class ChatList {

    @TableId(type = IdType.AUTO)
    @Alias("id")
    private String id;

    @Alias("title")
    private String title;

}
