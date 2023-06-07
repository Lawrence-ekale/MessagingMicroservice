package com.lawrenceekale.MessageClient.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageSendRequest {
    @NotEmpty(message = "Message must not be empty")
    private String content;
    @NotNull(message = "Recipient must not be null")
    private Long recipientId;
    @NotNull(message = "Sender must not be null")
    private Long senderId;
}
