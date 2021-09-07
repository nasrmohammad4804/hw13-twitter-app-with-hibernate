package base.service;

import base.domain.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseService<E extends BaseEntity<ID>, ID extends Serializable> {
    E save(E element);

    void delete(E element);

    E update(E element);

    Optional<E> findById(ID id);

    boolean existsById(ID id);

    List<E> findAll();
}
