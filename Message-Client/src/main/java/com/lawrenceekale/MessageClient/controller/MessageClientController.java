package com.lawrenceekale.MessageClient.controller;

import com.lawrenceekale.MessageClient.model.MessageSendRequest;
import com.lawrenceekale.MessageClient.model.MessageSuccessSaved;
import com.lawrenceekale.MessageClient.service.MessageClientService;
import com.lawrenceekale.message.MessageSavedId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
}
