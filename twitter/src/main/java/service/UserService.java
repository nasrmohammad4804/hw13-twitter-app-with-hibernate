package service;

import base.service.BaseService;
import domain.User;

import java.util.Optional;

public interface UserService extends BaseService<User,Long> {

    Optional<User> register();
    Optional<User> login();

    void addTwit(User user);

    void changeProfile(User user);

    Optional<User> findByUserName(String userName);

    void recovery();
}
