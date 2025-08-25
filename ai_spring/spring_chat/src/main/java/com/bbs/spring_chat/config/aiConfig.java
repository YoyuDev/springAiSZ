package com.bbs.spring_chat.config;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepositoryDialect;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Configuration
public class aiConfig {






    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository repository) {
        // 使用 JDBC 存储库创建带窗口限制的记忆
        return MessageWindowChatMemory.builder()
                .chatMemoryRepository(repository)  // 使用 JDBC 存储库
                .maxMessages(20)  // 最多保存20条消息
                .build();
    }

    @Bean
    public ChatClient ChatClient(OpenAiChatModel chatModel, ChatMemory chatMemory) {
        return ChatClient
                .builder(chatModel)
                .defaultSystem("你将作为问答ai“问小星”，对于用户的使用需求做出幽默的回答,注意不要说太多技术上的东西")
                .defaultAdvisors(new SimpleLoggerAdvisor(),
                        MessageChatMemoryAdvisor
                                .builder(chatMemory)
                                .build())
                .build();
    }



    @Bean
    VectorStore vectorStore(EmbeddingModel embeddingModel) {
        SimpleVectorStore vectorStore = SimpleVectorStore.builder(embeddingModel)
                .build();

        //生成一个说明文档
        List<Document> documents = List.of(
                new Document("产品说明：名称：问小星(只是个名字)" +
                        "产品描述：问小星是一个免费的ai问答网站/软件，是仿照问小白、豆包等知名ai的网站" +
                        "其作者是由“一只游鱼”的大学生在学习中构建创造出来的" +
                        "技术栈是由spring boot + vue + spring ai 等流行框架制作完成" +
                        "“问小星”目前有软件和网站两个平台。目前有ai问答、图片生成、语音合成等功能。" +
                        "未来还可能添加语音聊天、歌曲生成等功能，取决于作者学到什么。。。"));
        //向量化 文档储存
        vectorStore.add(documents);
        return vectorStore;
    }

}
