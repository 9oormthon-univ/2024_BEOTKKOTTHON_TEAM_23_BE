package com.beotkkotthon.areyousleeping.controller;

import com.beotkkotthon.areyousleeping.dto.global.ResponseDto;
import com.beotkkotthon.areyousleeping.dto.request.ChatMessageDto;
import com.beotkkotthon.areyousleeping.service.ChatMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Chat", description = "채팅 관련 API")
public class ChatController {

    private final ChatMessageService chatService;

    @MessageMapping("/chat/message/{teamId}")
    @Operation(summary = "메시지 전송", description = "메시지를 전송합니다.")
    public ResponseDto<?> sendMessage(
            @DestinationVariable String teamId,
            @Payload ChatMessageDto chatMessageDto) {
        chatService.sendChatMessage(teamId, chatMessageDto);
        return ResponseDto.created(null);
    }

    @GetMapping("/api/v1/chats/{teamId}/chat")
    @Operation(summary = "팀에서 오고갔던 메시지 조회", description = "채팅방을 잠시 벗어나있다가 다시 들어왔을 때, 지금까지 오고갔던 채팅 내역 전체를 불러옵니다.")
    public ResponseDto<?> showTeamMessages(
            @PathVariable String teamId,
            @RequestParam(value="page") Integer page,
            @RequestParam(value="size") Integer size
    ){
        return ResponseDto.ok(chatService.getChatMessages(teamId, page, size));
    }
}
