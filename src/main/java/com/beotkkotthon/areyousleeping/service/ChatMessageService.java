package com.beotkkotthon.areyousleeping.service;

import com.beotkkotthon.areyousleeping.domain.User;
import com.beotkkotthon.areyousleeping.domain.nosql.ChatMessage;
import com.beotkkotthon.areyousleeping.domain.nosql.ChatMessageList;
import com.beotkkotthon.areyousleeping.dto.request.ChatMessageDto;
import com.beotkkotthon.areyousleeping.dto.response.ChatMessageListDto;
import com.beotkkotthon.areyousleeping.dto.response.ChatMessageResponseDto;
import com.beotkkotthon.areyousleeping.exception.CommonException;
import com.beotkkotthon.areyousleeping.exception.ErrorCode;
import com.beotkkotthon.areyousleeping.repository.ChatMessageListRepository;
import com.beotkkotthon.areyousleeping.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageListRepository chatMessageListRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public void sendChatMessage(String teamId, ChatMessageDto requestDto, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_USER));
        OffsetDateTime parsedDate = OffsetDateTime.parse(requestDto.sendTime());

        Optional<ChatMessageList> chatMessageList = chatMessageListRepository.findById(teamId);
        if(chatMessageList.isPresent()){
            ChatMessageList chat = chatMessageList.get();
            chat.getMessages().add(
                    ChatMessage.builder()
                            .sender(user)
                            .content(requestDto.content())
                            .date(parsedDate)
                            .build()
            );
            chatMessageListRepository.save(chat);

        } else throw new CommonException(ErrorCode.NOT_FOUND_TEAM);

        ChatMessageResponseDto responseDto = ChatMessageResponseDto.builder()
                .type(requestDto.type())
                .sender(user.getNickname())
                .content(requestDto.content())
                .sendTime(requestDto.sendTime())
                .build();
        messagingTemplate.convertAndSend("/subscribe/team/" + teamId, responseDto);
    }

    public ChatMessageListDto getChatMessages(String teamId, OffsetDateTime date) {

        ChatMessageList chat = chatMessageListRepository.findById(teamId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_TEAM));

        List<ChatMessage> sortedMessages = sortMessagesByDate(chat).stream()
                .filter(msg -> msg.getDate().isAfter(date))
                .collect(Collectors.toList());

        return ChatMessageListDto.builder()
                .messageList(convertToMessageDTOList(sortedMessages))
                .build();
    }

    private List<ChatMessage> sortMessagesByDate(ChatMessageList chat) {
        return chat.getMessages()
                .stream()
                .sorted(Comparator.comparing(ChatMessage::getDate))
                .collect(Collectors.toList());
    }

    private List<ChatMessageResponseDto> convertToMessageDTOList(List<ChatMessage> messages) {
        return messages.stream()
                .map(msg -> ChatMessageResponseDto.builder()
                        .type(msg.getType())
                        .sender(msg.getSender().getNickname())
                        .content(msg.getContent())
                        .sendTime(msg.getDate().toString())
                        .build())
                .collect(Collectors.toList());
    }
}