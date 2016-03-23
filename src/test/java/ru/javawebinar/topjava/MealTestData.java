package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_ONE_ID = START_SEQ + 2;
    public static final int MEAL_TWO_ID = START_SEQ + 3;
    public static final int MEAL_THREE_ID = START_SEQ + 4;
    public static final int MEAL_FOUR_ID = START_SEQ + 5;

    public static final UserMeal MEAL_ONE = new UserMeal(MEAL_ONE_ID, LocalDateTime.of(2015, 11, 25, 11, 0, 0), "Юзер поел бобра", 1500);
    public static final UserMeal MEAL_TWO = new UserMeal(MEAL_TWO_ID, LocalDateTime.of(2015, 11, 25, 11, 0, 0), "Админ поел бобра", 1500);
    public static final UserMeal MEAL_THREE = new UserMeal(MEAL_THREE_ID, LocalDateTime.of(2015, 11, 25, 15, 0, 0), "Юзер поел осла", 500);
    public static final UserMeal MEAL_FOUR = new UserMeal(MEAL_FOUR_ID, LocalDateTime.of(2015, 11, 25, 15, 0, 0), "Админ поел осла", 500);

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);
}
