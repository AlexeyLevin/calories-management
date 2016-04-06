package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserWithUserMeals;

public interface AdminUserService extends UserService {
    UserWithUserMeals getWithUserMealTO(int userId);

    User getWithUserMealOneToMany(int userId);
}
