package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.util.List;

public interface UserMealService {

    void save(UserMeal userMeal);

    List<UserMeal> getList();

    List<UserMealWithExceed> getListWithExceed();

    void remove(int id);
}
