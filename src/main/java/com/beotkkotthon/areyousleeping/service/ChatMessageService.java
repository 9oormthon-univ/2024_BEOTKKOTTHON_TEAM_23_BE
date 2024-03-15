package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.domain.nosql.ChatMessage;
import com.beotkkotthon.areyousleeping.dto.response.ChatMessageDto;
import com.beotkkotthon.areyousleeping.repository.ChatMessageRepository;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public ChatMessageDto saveChatMessage(ChatMessageDto chatMessageDto, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        ChatMessage chatMessage = chatMessageRepository.save(ChatMessage.fromJson(chatMessageDto, user));
        return ChatMessageDto.fromEntity(
                chatMessage.getType(),
                user,
                chatMessage.getContent(),
                chatMessage.getDate()
        );
    }
}
