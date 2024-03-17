package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.annotation.UserId;
import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.ChatMessageDto;
import com.beotkkotthon.areyousleeping.dto.response.ChatMessageListDto;
import com.beotkkotthon.areyousleeping.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatService;

    @MessageMapping("/chat/message/{teamId}")
    public ResponseDto<?> sendMessage(
            @DestinationVariable String teamId,
            @Payload ChatMessageDto chatMessageDto) {
        chatService.sendChatMessage(teamId, chatMessageDto);
        return ResponseDto.created(null);
    }

    @GetMapping("/api/v1/chats/{teamId}/chat")
    public ResponseDto<?> showBarMessages(@PathVariable String teamId){

        return ResponseDto.ok(chatService.getChatMessages(teamId));
    }
}
