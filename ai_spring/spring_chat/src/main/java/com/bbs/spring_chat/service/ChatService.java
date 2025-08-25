package com.bbs.spring_chat.service;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bbs.spring_chat.mapper.ChatMapper;

import com.bbs.spring_chat.pojo.ChatList;
import org.springframework.stereotype.Service;


@Service
public class ChatService extends ServiceImpl<ChatMapper, ChatList> {



}
