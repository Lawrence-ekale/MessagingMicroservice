package com.lawrenceekale.MessageClient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagesAndProfiles {
    private List<GetMessagesResponse> messages;
    private List<SenderProfile> profiles;
}
