package repsostory;

import base.repository.BaseRepository;
import domain.Twit;
import domain.User;

import java.util.List;

public interface TwitRepository extends BaseRepository<Twit,Long> {

    List<Twit> findAllTwitOfUser(User user);
}
