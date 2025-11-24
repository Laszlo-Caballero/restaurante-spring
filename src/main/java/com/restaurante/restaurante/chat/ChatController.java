package com.restaurante.restaurante.chat;

import java.util.ArrayList;
import java.util.List;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.restaurante.restaurante.chat.utils.ChatMessage;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatController {

    List<ChatMessage> messages = new ArrayList<>();

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessage handleChatMessage(@Payload ChatMessage message) throws Exception {
        messages.add(message);
        return message;
    }

    @MessageMapping("/history")
    @SendTo("/topic/history")
    public List<ChatMessage> handleHistoryRequest() throws Exception {
        return messages;
    }

}
