package domain.embeddable;

import domain.User;
import domain.enumerated.LikeState;

import javax.persistence.*;

@Embeddable
public class TwitLike {

    @OneToOne
    private User user;

    @Enumerated(EnumType.STRING)
    LikeState state;

    @Column(columnDefinition = "tinyint(1)")
    private boolean isDelete;

    public TwitLike(User user, LikeState state) {
        this.user = user;
        this.state = state;
    }

    public TwitLike(User user, LikeState state, boolean isDelete) {
        this.user = user;
        this.state = state;
        this.isDelete = isDelete;
    }

    public TwitLike() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LikeState getState() {
        return state;
    }

    public void setState(LikeState state) {
        this.state = state;
    }

    public boolean getDeleted() {
        return isDelete;
    }

    public void setDeleted(boolean deleted) {
        isDelete = deleted;
    }
}
