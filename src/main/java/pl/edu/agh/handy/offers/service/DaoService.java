package pl.edu.agh.handy.offers.service;

import pl.edu.agh.handy.offers.exception.CategoryNotEmpty;
import pl.edu.agh.handy.offers.exception.EntityNotFound;
import pl.edu.agh.handy.offers.exception.UserAlreadyRegistered;

import java.util.List;

/**
 * Service API for DAO.
 * T - model
 * D -data transfer object (dto)
 */
public interface DaoService<T, D> {

    T create(D dto) throws UserAlreadyRegistered;
    void delete(D dto) throws EntityNotFound, CategoryNotEmpty;
    List<D> findAll();
    T update(D dto) throws EntityNotFound;
    D findById(long id);
}
