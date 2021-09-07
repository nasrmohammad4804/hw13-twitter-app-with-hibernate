package domain.enumerated;

public enum LikeState {

    FOLLOW, UNFOLLOW;

    @Override
    public String toString() {

        switch (this) {

            case FOLLOW -> {
                return FOLLOW.name();
            }
            case UNFOLLOW -> {
                return UNFOLLOW.name();
            }
        }
        return null;
    }
}
