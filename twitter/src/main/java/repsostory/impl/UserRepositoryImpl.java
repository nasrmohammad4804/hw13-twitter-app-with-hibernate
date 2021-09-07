package repsostory.impl;

import base.repository.impl.BaseRepositoryImpl;
import domain.User;
import repsostory.UserRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

public class UserRepositoryImpl extends BaseRepositoryImpl<User, Long>
        implements UserRepository {


    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void delete(User user) {
        entityManager.getTransaction().begin();
        user.setDeleted(true);
        update(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    @Override
    public Optional<User> findByUserName(String userName) {

        Optional<User> optional = Optional.empty();
        try {
            User user = entityManager.createQuery("select u from User as u  " +
                    "  where u.userName=:myUserName" +
                    " and u.isDeleted=false or u.isDeleted is null   ", User.class)
                    .setParameter("myUserName", userName).getSingleResult();

            optional = Optional.of(user);
            return optional;

        } catch (Exception e) {
            return optional;

        }
    }

    @Override
    public Optional<User> findByUserNameForRecovery(String userName, String password) {
        Optional<User> optional = Optional.empty();
        try {
            User user = entityManager.createQuery("select u from User as u  " +
                    "  where u.userName=:myUserName" +
                    " and u.isDeleted=true and u.password=:myPassword ", User.class)
                    .setParameter("myUserName", userName).setParameter
                            ("myPassword",password).getSingleResult();

            optional = Optional.of(user);
            return optional;

        } catch (Exception e) {
            return optional;

        }
    }
}
