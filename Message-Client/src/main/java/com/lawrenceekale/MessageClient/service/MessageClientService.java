package com.lawrenceekale.MessageClient.service;

import com.lawrenceekale.MessageClient.model.MessageSendRequest;
import com.lawrenceekale.MessageClient.model.MessageSuccessSaved;
import com.lawrenceekale.message.Message;
import com.lawrenceekale.message.MessageSavedId;
import com.lawrenceekale.message.MessageServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class MessageClientService {

    @GrpcClient("grpc-message-service")
    private MessageServiceGrpc.MessageServiceBlockingStub synchronousClient;

    public MessageSuccessSaved sendMessage(MessageSendRequest messageSendRequest) {
        Message request = Message.newBuilder()
                .setContent(messageSendRequest.getContent())
                .setSenderId(messageSendRequest.getSenderId())
                .setRecipientId(messageSendRequest.getRecipientId())
                .build();
        MessageSavedId messageSavedId = synchronousClient.sendMessage(request);


        return MessageSuccessSaved.builder()
                .time(Timestamp.valueOf(messageSavedId.getTimestamp()))
                .MessageId(messageSavedId.getMessageId())
                .build();
    }

}
