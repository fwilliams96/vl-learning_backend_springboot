package com.williamsdreams.vl_learning.users.descriptions.infrastructure.persistence.postgresql;

import com.williamsdreams.vl_learning.shared.domain.Image;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescription;
import com.williamsdreams.vl_learning.users.descriptions.domain.UserDescriptionRepository;
import com.williamsdreams.vl_learning.users.descriptions.infrastructure.persistence.postgresql.entity.UserDescriptionEntity;
import com.williamsdreams.vl_learning.users.descriptions.infrastructure.persistence.postgresql.repository.SpringDataPostgresqlUserDescriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostgresqlUserDescriptionRepository implements UserDescriptionRepository {

    private final SpringDataPostgresqlUserDescriptionRepository springDataPostgresqlUserDescriptionRepository;

    @Override
    public Optional<UserDescription> findById(UUID userDescriptionId) {
        Optional<UserDescriptionEntity> byId = springDataPostgresqlUserDescriptionRepository.findById(userDescriptionId);
        return byId.map(this::mapUserDescriptionEntityToUserDescription);
    }

    @Override
    public UserDescription create(UserDescription userDescription) {
        UserDescriptionEntity save = springDataPostgresqlUserDescriptionRepository.save(mapUserDescriptionToUserDescriptionEntity(userDescription));
        return mapUserDescriptionEntityToUserDescription(save);
    }

    private UserDescriptionEntity mapUserDescriptionToUserDescriptionEntity(UserDescription userDescription) {
        UserDescriptionEntity userEntity = new UserDescriptionEntity();
        userEntity.setId(userDescription.getId());
        userEntity.setTitle(userDescription.getTitle());
        userEntity.setTopic(userDescription.getTopic());
        userEntity.setImage(userDescription.getImage().getContent());
        userEntity.setEventId(userDescription.getEventId());
        userEntity.setUserId(userDescription.getUserId());
        return userEntity;
    }

    private UserDescription mapUserDescriptionEntityToUserDescription(UserDescriptionEntity userEntity) {
        return UserDescription.builder()
                .id(userEntity.getId())
                .title(userEntity.getTitle())
                .topic(userEntity.getTopic())
                .image(Image.builder().content(userEntity.getImage()).build())
                .eventId(userEntity.getEventId())
                .userId(userEntity.getUserId())
                .build();
    }
}
