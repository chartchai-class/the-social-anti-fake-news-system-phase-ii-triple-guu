package se331.lab.security.user;

import jakarta.transaction.Transactional;
import se331.lab.entity.UserEntity;

public interface UserService {
    UserEntity save(UserEntity user);

    @Transactional
    UserEntity findByUsername(String username);
}