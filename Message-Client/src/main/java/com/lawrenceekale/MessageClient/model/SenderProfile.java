package com.lawrenceekale.MessageClient.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SenderProfile {
    private String profileUrl;
    private String fullName;
}
