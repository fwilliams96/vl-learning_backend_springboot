package com.williamsdreams.vl_learning.gateway_app.clients.dto;

import lombok.Data;

@Data
public class UserDto {

    private String email;
    private String password;
    private String externalId;
    private String name;
    private String id;

}
