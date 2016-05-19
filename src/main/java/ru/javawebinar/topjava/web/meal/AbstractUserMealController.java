package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalTime;
import java.util.Collection;
import java.util.List;


public class AbstractUserMealController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    private UserMealService service;

    @Autowired
    protected void setService(UserMealService service) {
        this.service = service;
    }

    protected Collection<UserMeal> getAll(int userId) {
        LOG.info("user " + userId + " getAll");
        return service.getAll(userId);
    }

    protected UserMeal get(int userId, int mealId) {
        LOG.info("user " + userId + " get " + mealId);
        return service.get(userId, mealId);
    }

    protected UserMeal create(int userId, UserMeal userMeal) {
        userMeal.setId(null);
        LOG.info("user " + userId + " create " + userMeal);
        return service.save(userId, userMeal);
    }

    protected void delete(int userId, int mealId) {
        LOG.info("user " + userId + " delete " + mealId);
        service.delete(userId, mealId);
    }

    protected void update(int userId, UserMeal userMeal, int updateMealId) {
        userMeal.setId(updateMealId);
        LOG.info("user " + userId + " update " + userMeal);
        service.save(userId, userMeal);
    }

    protected List<UserMealWithExceed> getUserMealWithExceed(int id, int calories) {
        return UserMealsUtil.getFilteredWithExceeded(service.getAll(id), LocalTime.MIN, LocalTime.MAX, calories);
    }
}
