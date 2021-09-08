package service.impl;

import base.service.impl.BaseServiceImpl;
import domain.Twit;
import domain.User;
import domain.embeddable.TwitLike;
import domain.enumerated.LikeState;
import repsostory.impl.TwitRepositoryImpl;
import service.TwitService;
import util.ApplicationContext;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

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
        super.update(twit);

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


        System.out.println("your state changed to  " + twitLikeAtomicReference.get().getState() + "\n");
        super.update(twit);

        entityManager.refresh(twit);
    }

    @Override
    public void updateComment(User user, Twit twit) {

        List<Twit> comments = getCommentOfUserForUpdate(user, twit);

        if (comments.isEmpty())
            return;

        comments.forEach(x -> {
            System.out.println("your already comment is:\n" + x.getContext());
            System.out.println("enter new comment");
            String newComment = ApplicationContext.getApplicationContext().getScannerForString().nextLine();
            x.setContext(newComment);
        });

        super.update(twit);
    }

    @Override
    public void deleteComment(User user, Twit twit) {

        List<Twit> comments = getCommentOfUserForUpdate(user, twit);

        if (comments.isEmpty())
            return;

        comments.forEach(x -> {
            x.setDeleted(true);
            System.out.println("this comment successfully deleted ##");
        });

        super.update(twit);
    }

    private List<Twit> getCommentOfUserForUpdate(User user, Twit twit) {
        List<Twit> comments = twit.getComments().stream().filter(x -> x.getUser().equals(user))
                .collect(Collectors.toList());

        if (comments.isEmpty()) {
            System.out.println("you are dont confirm any comment for this twit ");
            return List.of();
        }

        comments.forEach(Twit::print);
        System.out.println("enter one comments witch you confirmed !!!");
        Long id = ApplicationContext.getApplicationContext().getScannerForInteger().nextLong();

        return comments.stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
    }

    @Override
    public List<Twit> findAllTwitOfUser(User user) {
        return repository.findAllTwitOfUser(user);
    }

    @Override
    public BigInteger countOfTwitsOfUser(Long userId) {
        return repository.countOfTwitsOfUser(userId);
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

        return repository.findById(id);
    }
}
