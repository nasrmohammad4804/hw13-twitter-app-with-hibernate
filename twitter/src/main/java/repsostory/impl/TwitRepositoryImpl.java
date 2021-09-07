package repsostory.impl;

import base.repository.impl.BaseRepositoryImpl;
import domain.Twit;
import repsostory.TwitRepository;

import javax.persistence.EntityManager;

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
}
