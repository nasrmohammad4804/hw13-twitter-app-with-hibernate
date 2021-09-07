package repsostory;

import base.repository.BaseRepository;
import domain.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User,Long> {

    Optional<User> findByUserName(String userName);

    Optional<User> findByUserNameForRecovery(String userName,String password);
}
