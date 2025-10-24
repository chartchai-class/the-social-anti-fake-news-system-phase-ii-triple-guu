
package se331.lab.security.user;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import se331.lab.entity.UserEntity;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    final UserDao userDao;

    @Override
    @Transactional
    public UserEntity save(UserEntity user) {
        return userDao.save(user);
    }

    @Override
    @Transactional
    public UserEntity findByUsername(String username) {
        return userDao.findByUsername(username);
    }
}