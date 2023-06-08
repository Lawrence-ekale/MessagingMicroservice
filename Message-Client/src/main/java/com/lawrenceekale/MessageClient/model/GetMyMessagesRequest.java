package com.lawrenceekale.MessageClient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetMyMessagesRequest {
    @NotNull(message = "User must be identified")
    private Long userId;//advance and use email then get userId from users database if exists
}
