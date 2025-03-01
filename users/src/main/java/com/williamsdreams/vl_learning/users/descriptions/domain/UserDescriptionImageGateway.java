package com.williamsdreams.vl_learning.users.descriptions.domain;

import com.williamsdreams.vl_learning.shared.domain.Image;

public interface UserDescriptionImageGateway {

    Image generateImage(String topic);

}
