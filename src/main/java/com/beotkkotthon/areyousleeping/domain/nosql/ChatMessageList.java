package com.beotkkotthon.areyousleeping.domain.nosql;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Document(collection = "chats")
public class ChatMessageList {
    @Id
    private String id; // 팀 아이디
    private List<ChatMessage> messages;

    @Builder
    public ChatMessageList(String id){
        this.id = id;
        this.messages = new ArrayList<>();
    }
}
