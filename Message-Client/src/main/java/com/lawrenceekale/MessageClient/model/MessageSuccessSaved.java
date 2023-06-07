package com.lawrenceekale.MessageClient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSuccessSaved {
    private Long MessageId;
    private Timestamp time;
}
