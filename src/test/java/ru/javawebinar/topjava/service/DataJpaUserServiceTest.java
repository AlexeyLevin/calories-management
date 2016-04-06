package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserWithUserMeals;
import ru.javawebinar.topjava.util.UserMealsUtil;

import static ru.javawebinar.topjava.MealTestData.USER_MEALS;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Autowired
    protected AdminUserService service;

    @Test
    public void testGetWithUserMeal() throws Exception {
        UserWithUserMeals userWithUserMeals = service.getWithUserMealTO(USER_ID);
        UserWithUserMeals userWithUserMealsTest = UserMealsUtil.createUserWithUserMeals(USER, USER_MEALS);
        MealTestData.MATCHER.assertCollectionEquals(userWithUserMeals.getUserMealCollection(), userWithUserMealsTest.getUserMealCollection());
        UserTestData.MATCHER.assertEquals(userWithUserMeals.getUser(), userWithUserMealsTest.getUser());
    }

    @Test
    public void testGetWithUserMealOneToMany() throws Exception {
        User expectedUser = service.getWithUserMealOneToMany(USER_ID);
        User actualUser = USER;
        actualUser.setUserMeals(USER_MEALS);
        UserTestData.MATCHER.assertEquals(expectedUser, actualUser);
        MealTestData.MATCHER.assertCollectionEquals(expectedUser.getUserMeals(), actualUser.getUserMeals());
    }
}
