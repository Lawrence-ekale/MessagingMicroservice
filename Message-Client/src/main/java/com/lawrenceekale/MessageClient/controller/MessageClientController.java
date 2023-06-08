package com.lawrenceekale.MessageClient.controller;

import com.lawrenceekale.MessageClient.model.GetMessagesResponse;
import com.lawrenceekale.MessageClient.model.GetMyMessagesRequest;
import com.lawrenceekale.MessageClient.model.MessageSendRequest;
import com.lawrenceekale.MessageClient.model.MessageSuccessSaved;
import com.lawrenceekale.MessageClient.model.MessagesAndProfiles;
import com.lawrenceekale.MessageClient.service.MessageClientService;
import com.lawrenceekale.message.GetMessageByIdRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/messages")
@Validated
public class MessageClientController {
    @Autowired
    private  MessageClientService messageClientService;

    @PostMapping("/send")
    public ResponseEntity<MessageSuccessSaved> sendMessage(@RequestBody @Valid MessageSendRequest messageSendRequest) {
        return ResponseEntity.ok(messageClientService.sendMessage(messageSendRequest));
    }

    @PostMapping("/retrieve")
    public ResponseEntity<MessagesAndProfiles> getMessages(@RequestBody @Valid GetMyMessagesRequest request) {
        GetMessageByIdRequest getMessageByIdRequest = GetMessageByIdRequest.newBuilder()
                .setRecipientId(request.getUserId())
                .build();
        return ResponseEntity.ok(messageClientService.getMessages(getMessageByIdRequest));
    }
}
