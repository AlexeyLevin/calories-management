package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithUser;
import ru.javawebinar.topjava.util.UserMealsUtil;

import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserMealServiceTest extends AbstractUserMealServiceTest {
    @Autowired
    protected AdminUserMealService service;
    @Test
    public void testGetWithUserMealTO() throws Exception {
        UserMealWithUser UserMealWithUser = service.getWithUserTO(MEAL1_ID, USER_ID);
        UserMealWithUser UserMealWithUserTest = UserMealsUtil.createUserMealWithUser(MEAL1, USER);
        MealTestData.MATCHER.assertEquals(UserMealWithUser.getUserMeal(), UserMealWithUserTest.getUserMeal());
        UserTestData.MATCHER.assertEquals(UserMealWithUser.getUser(), UserMealWithUserTest.getUser());
    }

    @Test
    public void testGetWithUserMeal() throws Exception {
        UserMeal userMeal = service.getWithUser(MEAL1_ID, USER_ID);
        MealTestData.MATCHER.assertEquals(userMeal, MEAL1);
        UserTestData.MATCHER.assertEquals(userMeal.getUser(), USER);
    }
}
