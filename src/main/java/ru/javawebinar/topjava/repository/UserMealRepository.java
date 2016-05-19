package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;

/**
 * GKislin
 * 06.03.2015.
 */
public interface UserMealRepository {
    //null if not found
    UserMeal save(int userId, UserMeal userMeal);

    // false if not found
    boolean delete(int userId ,int mealId);

    // null if not found
    UserMeal get(int userId, int mealId);

    Collection<UserMeal> getAll(int userId);
}
