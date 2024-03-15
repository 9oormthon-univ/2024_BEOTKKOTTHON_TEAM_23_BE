package com.beotkkotthon.areyousleeping.domain.nosql;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Document(collection = "chats")
public class ChatMessageList {
    @Id
    private String id; // 팀 아이디
    private List<ChatMessage> messages;
}
