package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

public class UserMealWithUser {
    private final UserMeal userMeal;
    private final User user;

    public UserMealWithUser(UserMeal userMeal, User user) {
        this.userMeal = userMeal;
        this.user = user;
    }

    public UserMeal getUserMeal() {
        return userMeal;
    }

    public User getUser() {
        return user;
    }
}