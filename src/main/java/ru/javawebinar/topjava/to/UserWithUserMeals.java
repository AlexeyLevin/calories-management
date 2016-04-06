package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;

public class UserWithUserMeals {
    private final User user;
    private final Collection<UserMeal> userMealCollection;

    public UserWithUserMeals(User user, Collection<UserMeal> userMealCollection) {
        this.user = user;
        this.userMealCollection = userMealCollection;
    }

    public User getUser() {
        return user;
    }

    public Collection<UserMeal> getUserMealCollection() {
        return userMealCollection;
    }
}