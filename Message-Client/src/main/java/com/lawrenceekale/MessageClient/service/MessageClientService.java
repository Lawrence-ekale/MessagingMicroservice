package com.lawrenceekale.MessageClient.service;


import com.lawrenceekale.MessageClient.model.MessageSendRequest;
import com.lawrenceekale.MessageClient.model.MessageSuccessSaved;
import com.lawrenceekale.message.Message;
import com.lawrenceekale.message.MessageSavedId;
import com.lawrenceekale.message.MessageServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.netty.shaded.io.grpc.netty.NettyChannelBuilder;
import io.grpc.netty.shaded.io.netty.handler.ssl.SslContext;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import utils.TLSUtils;

import javax.net.ssl.SSLException;
import java.sql.Timestamp;

@Service
public class MessageClientService {

    @GrpcClient("grpc-message-service")
    private MessageServiceGrpc.MessageServiceBlockingStub synchronousClient;


    /*public ManagedChannel initializeChannel() throws SSLException {
        SslContext sslContext = TLSUtils.createClientSslContext();

        return NettyChannelBuilder.forAddress("localhost", 9001)
                .sslContext(io.grpc.netty.shaded.io.netty.handler.ssl.SslContextBuilder.forClient().build())
                .build();
    }*/
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
