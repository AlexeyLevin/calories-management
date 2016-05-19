package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );

        long t0 = System.nanoTime();
        System.out.println(getFilteredMealsWithExceededJava7Style(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        long t1 = System.nanoTime();
        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("sequential sort took: %d ms", millis));

        long t2 = System.nanoTime();
        System.out.println(getFilteredMealsWithExceededJava8Style(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
        long t3 = System.nanoTime();
        long millis2 = TimeUnit.NANOSECONDS.toMillis(t3 - t2);
        System.out.println(String.format("sequential sort took: %d ms", millis2));
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceededJava7Style(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> dayCaloriesMap = new HashMap<>();
        for (UserMeal userMeal : mealList) {
            LocalDate date = userMeal.getDateTime().toLocalDate();
            int mealCalories = userMeal.getCalories();
            if (dayCaloriesMap.containsKey(date)) {
                dayCaloriesMap.put(date, dayCaloriesMap.get(date) + mealCalories);
            } else {
                dayCaloriesMap.put(date, mealCalories);
            }
        }

        List<UserMealWithExceed> filteredMealsWithExceededList = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            LocalDateTime dateTimeUserMeal = userMeal.getDateTime();
            if (TimeUtil.isBetween(LocalTime.from(dateTimeUserMeal), startTime, endTime)) {
                filteredMealsWithExceededList.add(new UserMealWithExceed(
                        dateTimeUserMeal, userMeal.getDescription(), userMeal.getCalories(),
                        dayCaloriesMap.get(dateTimeUserMeal.toLocalDate()) > caloriesPerDay));
            }
        }

        return filteredMealsWithExceededList;
    }

    public static List<UserMealWithExceed> getFilteredMealsWithExceededJava8Style(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dayMealCaloriesMap = mealList
                .stream()
                .collect(Collectors.groupingBy(userMeal -> userMeal.getDateTime().toLocalDate(),
                        Collectors.summingInt(UserMeal::getCalories)));

        return mealList
                .stream()
                .filter(userMeal -> TimeUtil.isBetween(LocalTime.from(userMeal.getDateTime()), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(),
                        userMeal.getDescription(), userMeal.getCalories(), dayMealCaloriesMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
