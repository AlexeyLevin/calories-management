package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealTo;

public class MealUtil {
    public static UserMeal createFromTo(UserMealTo newMeal) {
        return new UserMeal(null, newMeal.getDateTime(), newMeal.getDescription(), newMeal.getCalories());
    }

    public static UserMeal updateFromTo(UserMeal userMeal, UserMealTo userMealTo) {
        userMeal.setDateTime(userMealTo.getDateTime());
        userMeal.setDescription(userMealTo.getDescription());
        userMeal.setCalories(userMealTo.getCalories());
        return userMeal;
    }
}