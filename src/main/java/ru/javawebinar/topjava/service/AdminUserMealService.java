package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithUser;

public interface AdminUserMealService extends UserMealService {
    UserMealWithUser getWithUserTO(int id, int userId);

    UserMeal getWithUser(int id, int userId);
}
