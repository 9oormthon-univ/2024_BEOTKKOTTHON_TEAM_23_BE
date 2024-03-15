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
import org.springframework.web.bind.annotation.*;

import java.time.OffsetDateTime;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatService;

    @MessageMapping("/chat/message/{teamId}")
    public ResponseDto<?> sendMessage(
            @DestinationVariable String teamId,
            @RequestBody ChatMessageDto chatMessageDto, // type, content, sendTime
            @UserId Long userId) {
        chatService.sendChatMessage(teamId, chatMessageDto, userId);
        return ResponseDto.created(null);
    }

    @GetMapping("/api/v1/chats/{teamId}/chat")
    public ResponseDto<?> showBarMessages(@PathVariable String teamId,
                                              @RequestParam
                                              @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime date){

        return ResponseDto.ok(chatService.getChatMessages(teamId, date));
    }
}
