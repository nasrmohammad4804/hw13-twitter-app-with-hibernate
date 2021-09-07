package service.impl;

import base.service.impl.BaseServiceImpl;
import domain.Twit;
import domain.User;
import domain.embeddable.TwitLike;
import domain.enumerated.LikeState;
import repsostory.impl.TwitRepositoryImpl;
import service.TwitService;
import util.ApplicationContext;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class TwitServiceImpl extends BaseServiceImpl<Twit, Long, TwitRepositoryImpl>
        implements TwitService {

    private UserServiceImpl userService;

    public TwitServiceImpl(TwitRepositoryImpl repository) {
        super(repository);
    }

    public void initialize() {
        userService = ApplicationContext.getApplicationContext().getUserService();
    }

    @Override
    public void delete(Twit element) {
        repository.delete(element);
    }

    @Override
    public void changeContextOfTwit(User user, Twit twit, String newContext) {

        twit.setContext(newContext);
        userService.update(user);
        entityManager.refresh(twit);
    }

    @Override
    public void addComments(Twit twit, User user) {

        System.out.println("enter your comment");
        String comment = ApplicationContext.getApplicationContext().getScannerForString().nextLine();

        twit.getComments().add(new Twit(comment, user));
        update(twit);

        entityManager.refresh(twit);
    }

    @Override
    public void setLikeStateOfTwit(User user, Twit twit) {

        AtomicReference<TwitLike> twitLikeAtomicReference = new AtomicReference<>();

        twit.getTwitLikes().stream().filter(x -> x.getUser().equals(user))
                .findFirst().ifPresent(x -> {
            assert false;
            twitLikeAtomicReference.set(x);

        });
        for (TwitLike twitLike : twit.getTwitLikes())
            if (twitLike.getUser().equals(user))
                twitLike = twitLikeAtomicReference.get();


        if (twitLikeAtomicReference.get() == null) {
            System.out.println("you dont have already like twit ");
            twitLikeAtomicReference.set(new TwitLike(user, LikeState.UNFOLLOW));
            twit.getTwitLikes().add(twitLikeAtomicReference.get());
        }

        twitLikeAtomicReference.get().setState(chooseLikeState(twitLikeAtomicReference.get()));


        System.out.println("your state changed to  " + twitLikeAtomicReference.get().getState());
        update(twit);

        entityManager.refresh(twit);
    }

    private LikeState chooseLikeState(TwitLike twitLike) {

        System.out.println("your state is : " + twitLike.getState());

        if (twitLike.getState().equals(LikeState.FOLLOW))
            return LikeState.UNFOLLOW;

        else
            return LikeState.FOLLOW;
    }

    @Override
    public Optional<Twit> findById(Long id) {

        Optional<Twit> optional = Optional.empty();

        try {

            Twit twit = entityManager.createQuery("select t from Twit as t" +
                    " where t.id=:myId and t.isDeleted=false", Twit.class)
                    .setParameter("myId", id).getSingleResult();

            optional = Optional.of(twit);
            return optional;

        } catch (Exception e) {
            return optional;
        }

    }
}