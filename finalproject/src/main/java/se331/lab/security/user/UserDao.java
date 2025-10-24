package se331.lab.security.user;

import se331.lab.entity.UserEntity;

public interface UserDao {
    UserEntity findByUsername(String username);

    UserEntity save(UserEntity user);
}