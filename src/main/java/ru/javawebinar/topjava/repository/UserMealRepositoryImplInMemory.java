package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserMealRepositoryImplInMemory implements UserMealRepository {

    private static int idCount;
    //// TODO: 09.03.2016 AtomicInteger
    private static Map<Integer, UserMeal> userMeals;
    private static UserMealRepository userMealRepository;


    static {
        userMeals = new ConcurrentHashMap<>();
        userMeals.put(1, new UserMeal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        userMeals.put(2, new UserMeal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        userMeals.put(3, new UserMeal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        userMeals.put(4, new UserMeal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        userMeals.put(5, new UserMeal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        userMeals.put(6, new UserMeal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        userMeals.put(7, new UserMeal(7, LocalDateTime.of(2015, Month.MAY, 29, 12, 0), "Полдник", 2000));
        userMeals.put(8, new UserMeal(8, LocalDateTime.of(2015, Month.MAY, 28, 12, 0), "Полдник", 2001));
        idCount = 9;
    }
    private UserMealRepositoryImplInMemory() {
    }

    public static UserMealRepository getInstance() {
        if (userMealRepository == null) {
            userMealRepository = new UserMealRepositoryImplInMemory();
        }
        return userMealRepository;
    }

    @Override
    public List<UserMeal> getList() {
        return new ArrayList<>(userMeals.values());
    }

    @Override
    public void remove(int id) {
        userMeals.remove(id);
    }

    public synchronized int getNewId () {
        int returnId = idCount;
        ++idCount;
        return returnId;
    }

    public synchronized int getCurrentMaxId() {
        return idCount - 1;
    }

    @Override
    public synchronized void save(UserMeal userMeal) {
        userMeals.put(userMeal.getId(), userMeal);
    }
}
