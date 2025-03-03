package com.williamsdreams.vl_learning.app.mappers;

import com.williamsdreams.vl_learning.users.shared.domain.Image;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescription;
import com.williamsdreams.vl_learning.app.api.dto.UserDescriptionDto;
import org.springframework.stereotype.Component;

@Component
public class UserDescriptionToUserDescriptionDtoMapper {

    public UserDescriptionDto map(UserDescription userDescription) {
        UserDescriptionDto userDescriptionDto = new UserDescriptionDto();
        userDescriptionDto.setId(userDescription.getId());
        userDescriptionDto.setTitle(userDescription.getTitle());
        userDescriptionDto.setTopic(userDescription.getTopic());
        userDescriptionDto.setEventId(userDescription.getEventId());
        userDescriptionDto.setImage(mapImageToDto(userDescription.getImage()));
        userDescriptionDto.setUserId(userDescription.getUserId());
        return userDescriptionDto;
    }

    private String mapImageToDto(Image image) {
        return image.getContent();
    }

}
