package com.bbs.spring_chat.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbs.spring_chat.common.Result;
import com.bbs.spring_chat.pojo.ChatList;
import com.bbs.spring_chat.pojo.Memory;
import com.bbs.spring_chat.service.ChatService;
import com.bbs.spring_chat.service.MemoryService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import reactor.core.publisher.Flux;



@RestController
public class chatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private MemoryService memoryService;

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private VectorStore vectorStore;
    private static final String CONVERSATION_ID = "default";

    //流式对话

    //流式对话
//    @GetMapping(value = "/chat-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public Flux<String> chatStream(@RequestParam String message, ServerHttpResponse response) {
//        response.getHeaders().setCacheControl("no-cache");
//        response.getHeaders().add("X-Accel-Buffering", "no"); // 禁止 nginx 缓存
//        response.getHeaders().add("Connection", "keep-alive");
//        response.getHeaders().add("Cache-Control", "no-transform");
//
//        return chatClient.prompt()
//                .user(message)
//                .advisors(new QuestionAnswerAdvisor(vectorStore))
//                .stream()
//                .content()
//                // 打印日志
//                .doOnNext(chunk -> System.out.println("发送 chunk -> " + chunk))
//                // 在最后补一个结束标记
//                .concatWith(Flux.just("[END]"))
//                .onErrorResume(e -> Flux.just("[AI服务出错: " + e.getMessage() + "]"));
//    }
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/chat-stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> chatStream(@RequestParam String message,
                                                    @RequestParam(value = "conversationId", required = false) String conversationId,
                                                    ServerHttpResponse response) {

        response.getHeaders().setCacheControl("no-cache");
        response.getHeaders().add("X-Accel-Buffering", "no"); // 禁止 nginx 缓存
        response.getHeaders().add("Connection", "keep-alive");
        response.getHeaders().add("Cache-Control", "no-transform");
        return chatClient.prompt()
                .user(message)
                .advisors(new QuestionAnswerAdvisor(vectorStore))
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId == null ? CONVERSATION_ID : conversationId))
                .stream()
                .content()
//                .delayElements(Duration.ofMillis(50))               //延迟0.05秒
                .map(chunk -> ServerSentEvent.builder(chunk).build())
                .concatWith(Flux.just(ServerSentEvent.builder("[END]").build()));
    }

    //获取历史会话列表
    @GetMapping("/chat/chatList")
    public Result selectUserData() {

        List<ChatList> list = chatService.list();
        return Result.success(list);
    }
    //添加会话
    @PostMapping("/chat/save")
    public Result add(@RequestBody ChatList chatList) {
        chatService.save(chatList);
        return Result.success();
    }

    //获取指定对话历史记录
    @GetMapping("/chat/{id}")
    public Result selectUserDataId(@PathVariable("id") String id) {
        QueryWrapper<Memory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("conversation_id", id);
        List<Memory> list = memoryService.list(queryWrapper);
        return Result.success(list);
    }

    //修改会话标题
    @PostMapping("/chat/update")
    public Result update(@RequestBody ChatList chatList) {
        chatService.updateById(chatList);
        return Result.success();
    }

    //删除会话
    @DeleteMapping("/chat/delete/{id}")
    public Result delete(@PathVariable("id") String id) {
        QueryWrapper<Memory> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("conversation_id", id);
        memoryService.remove(queryWrapper);
        chatService.removeById(id);
        return Result.success();
    }

}
