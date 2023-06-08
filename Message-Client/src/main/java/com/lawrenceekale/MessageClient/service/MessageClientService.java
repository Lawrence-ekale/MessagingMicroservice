package com.lawrenceekale.MessageClient.service;


import com.lawrenceekale.MessageClient.model.GetMessagesResponse;
import com.lawrenceekale.MessageClient.model.GetMyMessagesRequest;
import com.lawrenceekale.MessageClient.model.MessageSendRequest;
import com.lawrenceekale.MessageClient.model.MessageSuccessSaved;
import com.lawrenceekale.MessageClient.model.MessagesAndProfiles;
import com.lawrenceekale.MessageClient.model.SenderProfile;
import com.lawrenceekale.message.GetMessageByIdRequest;
import com.lawrenceekale.message.Message;
import com.lawrenceekale.message.MessageResponse;
import com.lawrenceekale.message.MessageSavedId;
import com.lawrenceekale.message.MessageServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class MessageClientService {

    @GrpcClient("grpc-message-service")
    private MessageServiceGrpc.MessageServiceBlockingStub synchronousClient;

    @GrpcClient("grpc-message-service")
    private MessageServiceGrpc.MessageServiceStub asynchronousClient;

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

    public MessagesAndProfiles getMessages(GetMessageByIdRequest request) {
        List<GetMessagesResponse> messages = new ArrayList<>();
        List<SenderProfile> senderProfiles = new ArrayList<>();
        Map<Long,Integer> senderProfileMap = new HashMap<>();//keep userId in key

        MessagesAndProfiles messagesAndProfiles = new MessagesAndProfiles();

        CompletableFuture<MessagesAndProfiles> future = new CompletableFuture<>();

        StreamObserver<Message> responseObserver = new StreamObserver<Message>() {
            @Override
            public void onNext(Message message) {
                int senderProfileIndex = processMessage(message,senderProfiles,senderProfileMap);

                GetMessagesResponse response =GetMessagesResponse.builder()
                        .messageId(message.getMessageId())
                        .recipientId(message.getRecipientId())
                        .senderId(message.getRecipientId())
                        .content(message.getContent())
                        .isRead(message.getIsRead())
                        .time(Timestamp.valueOf(message.getTimestamp()))
                        .profileIndex(senderProfileIndex)
                        .build();


                messages.add(response);
            }

            @Override
            public void onError(Throwable throwable) {
                //handle errors of a message during streaming
                future.completeExceptionally(throwable);
            }

            @Override
            public void onCompleted() {
                //perform any final task here when done
                messagesAndProfiles.setMessages(messages);
                messagesAndProfiles.setProfiles(senderProfiles);

                future.complete(messagesAndProfiles);
            }
        };
        asynchronousClient.getMessageById(request,responseObserver);

        return future.join();
    }

    private int processMessage(Message message, List<SenderProfile> senderProfiles, Map<Long,Integer> senderProfileMap) {
            Long userId = message.getRecipientId();
            Integer senderProfileIndex = senderProfileMap.get(userId);

            if(senderProfileIndex == null) {
                SenderProfile senderProfile = getUserProfile(userId);

                senderProfileIndex = senderProfiles.size();
                senderProfiles.add(senderProfile);
                senderProfileMap.put(userId,senderProfileIndex);
            }

            return senderProfileIndex;
    }

    private SenderProfile getUserProfile(Long userId) {
        //Make request to the user grpc service and get this details
        if (userId == 122) {//hard coded values
            return SenderProfile.builder()
                    .fullName("Lawrence Ekale")
                    .profileUrl("lawrence122")
                    .build();
        } else {
            return SenderProfile.builder()
                    .fullName("Lawrence Emuria")
                    .profileUrl("lawrence100")
                    .build();
        }
    }
}
