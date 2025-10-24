package se331.lab.security.user;

import se331.lab.entity.UserEntity;
import se331.lab.repository.UserRepository;
import org.springframework.stereotype.Repository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    final UserRepository userRepository;

    @Override
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }
}