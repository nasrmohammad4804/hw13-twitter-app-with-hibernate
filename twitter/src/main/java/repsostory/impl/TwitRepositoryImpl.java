package repsostory.impl;

import base.repository.impl.BaseRepositoryImpl;
import domain.Twit;
import domain.User;
import repsostory.TwitRepository;

import javax.persistence.EntityManager;
import java.util.List;

public class TwitRepositoryImpl extends BaseRepositoryImpl<Twit, Long>
        implements TwitRepository {

    public TwitRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public void delete(Twit twit) {

        entityManager.getTransaction().begin();
        twit.setDeleted(true);
        update(twit);
        entityManager.getTransaction().commit();
    }

    @Override
    public Class<Twit> getEntityClass() {
        return Twit.class;
    }

    @Override
    public List<Twit> findAllTwitOfUser(User user) {

        return entityManager.createNativeQuery("SELECT t.* FROM twit as t where t.twit_comment is null" +
                "  and t.user_id=:id and t.isDeleted=false")
                .setParameter("id", user.getId())
                .getResultList();
    }
}
