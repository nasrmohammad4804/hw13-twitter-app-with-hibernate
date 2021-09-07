package domain;

import base.domain.BaseEntity;
import domain.embeddable.TwitLike;

import javax.persistence.*;
import java.util.*;

@Entity

@NamedNativeQuery(name = "allTwitOfUser",query = "SELECT t.* FROM twit as t where t.twit_comment is null" +
        "  and t.user_id=:id and t.isDeleted=false ")
@NamedNativeQuery(name = "countOfAllTwitsOfUser",query = "SELECT count(*) FROM twit as t where t.twit_comment is null" +
        " and t.user_id=:myId and t.isDeleted=false")

public class Twit extends BaseEntity<Long> {

    public static final String TABLE_NAME_OF_ALL_LIKES_TWIT = "twit_like";
    public static final String TABLE_NAME_FOR_COMMENTS = "twit_comment";


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = TABLE_NAME_FOR_COMMENTS)
    Set<Twit> comments = new HashSet<>();

    @Column(length = 280)
    private String context;

    @ManyToOne
    private User user;

    @ElementCollection
    @JoinTable(name = TABLE_NAME_OF_ALL_LIKES_TWIT)
    private Set<TwitLike> twitLikes = new LinkedHashSet<>();

    public Twit() {
    }

    public Twit(Set<Twit> comments, String context, User user, Set<TwitLike> twitLikes) {
        this.comments = comments;
        this.context = context;
        this.user = user;
        this.twitLikes = twitLikes;
    }

    public Twit(String context, User user) {
        this.context = context;
        this.user = user;
    }

    public Set<Twit> getComments() {
        return comments;
    }

    public void setComments(Set<Twit> comments) {
        this.comments = comments;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Set<TwitLike> getTwitLikes() {
        return twitLikes;
    }

    public void setTwitLikes(Set<TwitLike> twitLikes) {
        this.twitLikes = twitLikes;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public String toString() {
        return "Twit{" +
                "comments=" + comments +
                ", user=" + user +
                ", twitLikes=" + twitLikes +
                ", context=" + context +
                '}';
    }

    public void print() {

        System.out.println("-".repeat(80));
        System.out.printf("|%-35s!!twit!!%-35s %s\n"," "," ","|");
        System.out.printf("%-80s%s\n","|"+"twit id :"+getId()+"   "+"owner of twit :"+user.getUserName(),"|");
        System.out.printf("%-80s%s\n" ,"|context of twit : ","|");
        System.out.printf("%-80s%s\n","|"+context,"|");
        System.out.println("-".repeat(80));
        getComments().forEach(comment -> {
            System.out.println("comment id :" + comment.getId() + "  " + " writed by :"
                    + comment.getUser().getUserName()
                    + "\n text of comment : \n" + comment.getContext());
            System.out.println("- - ".repeat(40));
        });

    }
}
