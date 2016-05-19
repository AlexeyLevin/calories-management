package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.UserMealRepositoryImplInMemory;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalTime;
import java.util.List;

public class UserMealServiceImpl implements UserMealService {

    private UserMealRepository userMealRepository;

    private UserMealServiceImpl(UserMealRepository userMealRepository) {
        this.userMealRepository = userMealRepository;
    }

    public static UserMealServiceImpl getInstance() {
        return new UserMealServiceImpl(UserMealRepositoryImplInMemory.getInstance());
    }

    @Override
    public void save(UserMeal userMeal) {
        userMealRepository.save(userMeal);
    }

    @Override
    public List<UserMeal> getList() {
        return userMealRepository.getList();
    }

    @Override
    public List<UserMealWithExceed> getListWithExceed() {
        return UserMealsUtil.getFilteredMealsWithExceeded(
                userMealRepository.getList(), LocalTime.MIN, LocalTime.MAX, 2000);
    }

    @Override
    public void remove(int id) {
        userMealRepository.remove(id);
    }

    public int getNewId() {
        return userMealRepository.getNewId();
    }

    public int getCurrentMaxId() {
        return userMealRepository.getCurrentMaxId();
    }
}
