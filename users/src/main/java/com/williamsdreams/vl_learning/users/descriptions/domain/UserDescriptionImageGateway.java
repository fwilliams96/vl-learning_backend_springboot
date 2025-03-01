package com.williamsdreams.vl_learning.users.descriptions.domain;

import com.williamsdreams.vl_learning.users.shared.domain.Image;

public interface UserDescriptionImageGateway {

    Image generateImage(String topic);

}
