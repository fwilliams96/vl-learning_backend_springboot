package com.williamsdreams.vl_learning.auth.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JWToken {

    private String accessToken;

}
