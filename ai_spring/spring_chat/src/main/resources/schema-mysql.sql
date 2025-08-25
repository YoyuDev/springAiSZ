# -- schema-mysql.sql
# -- 创建数据库（如果使用 root 用户执行脚本）
# CREATE DATABASE IF NOT EXISTS spring_ai_chat_memory;
# USE spring_ai_chat_memory;
#
# -- 删除旧表（如果存在）
# DROP TABLE IF EXISTS spring_ai_chat_memory;
-- schema-mysql.sql
CREATE TABLE IF NOT EXISTS spring_ai_sz_chat_memory (
                                                     conversation_id VARCHAR(36) NOT NULL,
                                                     content TEXT NOT NULL,
                                                     type VARCHAR(10) NOT NULL,
                                                     `timestamp` TIMESTAMP NOT NULL,
                                                     CONSTRAINT TYPE_CHECK CHECK (type IN ('USER', 'ASSISTANT', 'SYSTEM', 'TOOL'))
);

CREATE INDEX IF NOT EXISTS SPRING_AI_SZ_CHAT_MEMORY_CONVERSATION_ID_TIMESTAMP_IDX
    ON SPRING_AI_SZ_CHAT_MEMORY(conversation_id, `timestamp`);
