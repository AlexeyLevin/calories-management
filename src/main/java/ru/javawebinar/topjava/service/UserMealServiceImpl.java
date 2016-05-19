package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

/**
 * ALevin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {
    private static final Logger LOG = LoggerFactory.getLogger(UserMealServiceImpl.class);
    private UserMealRepository repository;

    @Autowired
    public void setRepository(UserMealRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserMeal save(int userId, UserMeal userMeal) {
        LOG.info("Service: try to save " + userMeal + " to user " + userId);
        return ExceptionUtil.check(repository.save(userId, userMeal), "Service: can't save meal " + userMeal + " to User " + userId);
    }

    @Override
    public void update(int userId, UserMeal userMeal) throws NotFoundException {
        LOG.info("Service: try to update " + userMeal + " to user " + userId);
        ExceptionUtil.check(repository.save(userId, userMeal), "Service: can't update meal " + userMeal + " to User " + userId);
    }

    @Override
    public void delete(int userId, int mealId) throws NotFoundException {
        LOG.info("Service: try to delete " + mealId + " to user " + userId);
        ExceptionUtil.check(repository.delete(userId, mealId), "Service: can't delete meal " + mealId + " to User " + userId);
    }

    @Override
    public UserMeal get(int userId, int mealId) throws NotFoundException {
        LOG.info("Service: try to get " + mealId + " to user " + userId);
        return ExceptionUtil.check(repository.get(userId, mealId), "Service: can't get meal " + mealId + " to User " + userId);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        LOG.info("Service: Get all meals to user " + userId);
        return repository.getAll(userId);
    }
}
