package service;

import base.service.BaseService;
import domain.Twit;
import domain.User;

import java.math.BigInteger;
import java.util.List;

public interface TwitService extends BaseService<Twit,Long> {

    void changeContextOfTwit(User user,Twit twit,String newContext);

    void addComments(Twit twit,User user);

    void setLikeStateOfTwit(User user,Twit twit);

    void updateComment(User user, Twit twit);

    void deleteComment(User user,Twit twit);

    List<Twit> findAllTwitOfUser(User user);

    BigInteger countOfTwitsOfUser(Long userId);

}
