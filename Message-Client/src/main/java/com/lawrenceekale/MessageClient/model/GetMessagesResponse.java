package com.lawrenceekale.MessageClient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMessagesResponse {
    private Long messageId;
    private String content;
    private Long senderId;
    private Long recipientId;
    private Timestamp time;
    private Boolean isRead;
    private int profileIndex;
}
