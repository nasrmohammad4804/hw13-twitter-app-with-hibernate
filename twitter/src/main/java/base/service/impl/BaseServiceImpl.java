package base.service.impl;

import base.domain.BaseEntity;
import base.repository.impl.BaseRepositoryImpl;
import base.service.BaseService;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public abstract   class BaseServiceImpl<E extends BaseEntity<ID>, ID extends Serializable, R extends BaseRepositoryImpl<E, ID>>
        implements BaseService<E, ID> {

    protected final R repository;
    protected final EntityManager entityManager;


    public BaseServiceImpl(R repository) {
        this.repository = repository;

        entityManager = repository.getEntityManager();
    }

    @Override
    public E save(E element) {

        entityManager.getTransaction().begin();
        repository.save(element);
        entityManager.getTransaction().commit();

        return element;
    }

    @Override
    public E update(E element) {
        E data;

        entityManager.getTransaction().begin();
        data = repository.update(element);
        entityManager.getTransaction().commit();

        return data;
    }

    @Override
    public Optional<E> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public boolean existsById(ID id) {
        return repository.existsById(id);
    }

    @Override
    public List<E> findAll() {
        return repository.findAll();
    }
}
