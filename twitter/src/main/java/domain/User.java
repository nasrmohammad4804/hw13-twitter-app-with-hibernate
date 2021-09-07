package domain;

import base.domain.BaseEntity;
import domain.embeddable.Profile;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "unique_column", columnNames = {User.USER_NAME})})
public class User extends BaseEntity<Long> {

    public final static String USER_NAME = "user_name";

    @Column(name = USER_NAME, nullable = false)
    private String userName;

    private String password;
    private LocalDate birthDay;

    @Embedded
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Twit> twits=new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(userName, user.userName) && Objects.equals(password, user.password)
                && Objects.equals(birthDay, user.birthDay) &&
                Objects.equals(profile, user.profile) && Objects.equals(twits, user.twits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, birthDay, profile, twits);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Twit> getTwits() {
        return twits;
    }

    public void setTwits(List<Twit> twits) {
        this.twits = twits;
    }

    public User(UserBuilder userBuilder) {
        this.userName = userBuilder.userName;
        this.password = userBuilder.password;
        this.birthDay = userBuilder.birthDay;
        this.profile = userBuilder.profile;
        this.twits = userBuilder.twits;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", birthDay=" + birthDay +
                ", profile -> " + profile +
                '}';

    }

    public static class UserBuilder {

        private String userName;

        private String password;
        private LocalDate birthDay;

        @OneToOne(cascade = CascadeType.ALL)
        private Profile profile;

        @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
        private List<Twit> twits;

        public UserBuilder(String userName, String password) {

            this.userName = userName;
            this.password = password;
        }

        public UserBuilder getProfile(Profile profile) {
            this.profile = profile;
            return this;
        }

        public UserBuilder getBirthDay(LocalDate birthDay) {
            this.birthDay = birthDay;
            return this;
        }

        public UserBuilder getTwitLikes(List<Twit> twits) {
            this.twits = twits;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
