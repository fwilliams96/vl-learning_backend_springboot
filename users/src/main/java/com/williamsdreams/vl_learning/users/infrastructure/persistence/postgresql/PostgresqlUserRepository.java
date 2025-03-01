package com.williamsdreams.vl_learning.users.infrastructure.persistence.postgresql;

import com.williamsdreams.vl_learning.users.domain.User;
import com.williamsdreams.vl_learning.users.domain.UserRepository;
import com.williamsdreams.vl_learning.users.infrastructure.persistence.postgresql.entity.UserEntity;
import com.williamsdreams.vl_learning.users.infrastructure.persistence.postgresql.repository.SpringDataPostgresqlUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PostgresqlUserRepository implements UserRepository {

    private final SpringDataPostgresqlUserRepository springDataPostgresqlUserRepository;

    @Override
    public Optional<User> findById(UUID userId) {
        Optional<UserEntity> byId = springDataPostgresqlUserRepository.findById(userId);
        return byId.map(this::mapUserEntityToUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> byEmail = springDataPostgresqlUserRepository.findByEmail(email);
        return byEmail.map(this::mapUserEntityToUser);
    }

    @Override
    public User create(User user) {
        UserEntity insert = springDataPostgresqlUserRepository.save(mapUserToUserEntity(user));
        return mapUserEntityToUser(insert);
    }

    private UserEntity mapUserToUserEntity(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        userEntity.setEmail(user.getEmail());
        userEntity.setName(user.getName());
        return userEntity;
    }

    private User mapUserEntityToUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .name(userEntity.getName())
                .build();
    }
}
