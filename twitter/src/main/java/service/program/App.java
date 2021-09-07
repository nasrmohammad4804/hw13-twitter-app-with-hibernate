package service.program;

import domain.Twit;
import domain.User;
import service.impl.TwitServiceImpl;
import service.impl.UserServiceImpl;
import util.ApplicationContext;
import util.HibernateUtil;

import java.util.Optional;

public class App {

    private UserServiceImpl userService = ApplicationContext.getApplicationContext().getUserService();
    private TwitServiceImpl twitService = ApplicationContext.getApplicationContext().getTwitService();

    public void initialize() {

        userService.initialize();
        twitService.initialize();
    }

    public void start() {

        System.out.println("1.register");
        System.out.println("2.login");
        System.out.println("3.account recovery");
        System.out.println("4.exit");

        switch (ApplicationContext.getApplicationContext().getScannerForInteger().nextInt()) {

            case 1 -> {

                userService.register().ifPresent(this::userPanel);
                start();
            }
            case 2 -> {
                userService.login().ifPresent(this::userPanel);
                start();
            }
            case 3 -> {
                userService.recovery();
                System.out.println("account is recovery\n");
                start();
            }
            case 4 -> {
                System.out.println("have nice day ###");
                ApplicationContext.getApplicationContext().getEntityManager().close();
                HibernateUtil.getEntityManagerFactory().close();
                System.exit(0);
            }
        }
    }

    public void userPanel(User user) {

        System.out.println("1.change profile");
        System.out.println("2.show profile");
        System.out.println("3.add twit");
        System.out.println("4.change twit");
        System.out.println("5.show all twit");
        System.out.println("6.show specific twit");
        System.out.println("7.search another users");
        System.out.println("8.logout");
        System.out.println("9.delete account");

        switch (ApplicationContext.getApplicationContext().getScannerForInteger().nextInt()) {

            case 1 -> {
                userService.changeProfile(user);
                userPanel(user);
            }

            case 2 -> {
                System.out.println(user);
                userPanel(user);
            }

            case 3 -> {
                userService.addTwit(user);
                userPanel(user);
            }
            case 4 -> {

                Optional<Twit> optional = findTwit(user);
                optional.ifPresent(twit -> myTwitPanel(user, twit));

                if (optional.isEmpty())
                    System.out.println("dont find any exists twit !!!\n");

                userPanel(user);
            }
            case 5 -> {
                userService.showUserTwits(user, user);
                userPanel(user);
            }
            case 6 -> {

                if (twitService.countOfTwitsOfUser(user.getId()).longValue()== 0){
                    System.out.println("dont find any exists twit !!!\n");
                    userPanel(user);
                }

                userService.showUserTwits(user, user);

                userService.showSpecificTwit(user);
                userPanel(user);
            }
            case 7 -> {
                System.out.println("enter userName");
                String anotherUserName = ApplicationContext.getApplicationContext().getScannerForString().nextLine();
                Optional<User> optional = userService.findByUserName(anotherUserName);
                if (optional.isEmpty()) {
                    System.out.println("this userName not exists");
                    userPanel(user);
                }

                anotherTwitPanel(optional.get(), user);
                userPanel(user);
            }

            case 8 -> start();

            case 9 -> {
                userService.delete(user);
                System.out.println("account is deleted -_-\n");
                start();
            }
        }
    }

    private Optional<Twit> findTwit(User user) {

        if (twitService.countOfTwitsOfUser(user.getId()).longValue()== 0)
            return Optional.empty();

        userService.showUserTwits(user, user);

        System.out.println("enter witch one twit from all twits");
        Long id = ApplicationContext.getApplicationContext().getScannerForInteger().nextLong();
        return twitService.findById(id);
    }

    public void myTwitPanel(User user, Twit twit) {

        System.out.println();
        System.out.println("1.change context ");
        System.out.println("2.like or dislike");
        System.out.println("3.add comments");
        System.out.println("4.update comment");
        System.out.println("5.delete twit");
        System.out.println("6.go back ");

        switch (ApplicationContext.getApplicationContext().getScannerForInteger().nextInt()) {

            case 1 -> {
                System.out.println("already context of twit : \n" + twit.getContext());
                System.out.println("enter newContext for twit");
                String newContext = ApplicationContext.getApplicationContext().getScannerForString().nextLine();

                twitService.changeContextOfTwit(user, twit, newContext);
                myTwitPanel(user, twit);
            }
            case 2 -> {
                twitService.setLikeStateOfTwit(user, twit);
                myTwitPanel(user, twit);
            }
            case 3 -> {
                twitService.addComments(twit, user);
                myTwitPanel(user, twit);
            }
            case 4 -> {
                twitService.updateComment(user, twit);
                myTwitPanel(user, twit);
            }
            case 5 -> {
                twitService.delete(twit);
                userPanel(user);
            }

            case 6 -> userPanel(user);

            default -> {
                System.out.println("input not valid ... try again");
                myTwitPanel(user, twit);
            }
        }
    }

    public void anotherTwitPanel(User user, User anotherUser) {

        System.out.println("if you want to show All twit of :" +
                user.getUserName() + "  press 1 otherwise press 0");

        if (ApplicationContext.getApplicationContext().getScannerForInteger().nextInt() == 1)
            userService.showAllTwitsOfAnotherUser(user, anotherUser);

    }

}
