package com.beotkkotthon.areyousleeping.domain;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "chat")
public class ChatMessage {
    private String type;
    private String content;
}
