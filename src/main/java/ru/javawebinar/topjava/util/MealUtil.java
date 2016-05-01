package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealTo;

public class MealUtil {
    public static UserMeal createFromTo(UserMealTo newMeal) {
        return new UserMeal(null, newMeal.getDateTime(), newMeal.getDescription(), newMeal.getCalories());
    }
}