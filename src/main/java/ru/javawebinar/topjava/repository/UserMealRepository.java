package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.List;

public interface UserMealRepository {

    List<UserMeal> getList();

    void remove(int id);

    void save(UserMeal userMeal);

    int getNewId();

    int getCurrentMaxId();
}
