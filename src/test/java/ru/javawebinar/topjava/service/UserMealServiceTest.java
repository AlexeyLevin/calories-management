package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testSave() {
        UserMeal createdMeal = service.save(MealTestData.MEAL_ONE, MealTestData.USER_ID);
        UserMeal getMeal = service.get(createdMeal.getId(), MealTestData.USER_ID);
        MealTestData.MATCHER.assertEquals(getMeal, createdMeal);
    }

    @Test
    public void testGet() {
        UserMeal userMeal = service.get(MealTestData.MEAL_TWO_ID, ADMIN_ID);
        MealTestData.MATCHER.assertEquals(MealTestData.MEAL_TWO, userMeal);
    }

    @Test
    public void testDelete() {
        service.delete(100002, USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(Collections.singletonList(MealTestData.MEAL_THREE), service.getAll(USER_ID));
    }

    @Test
    public void testGetBetweenDates() {
        Collection<UserMeal> Expected = service.getBetweenDates(LocalDate.of(2015, 11, 25),
                LocalDate.of(2015, 11, 25), USER_ID);
        Collection<UserMeal> Actual = Arrays.asList(MealTestData.MEAL_THREE, MealTestData.MEAL_ONE);
        MealTestData.MATCHER.assertCollectionEquals(Expected, Actual);
    }

    @Test
    public void testGetBetweenDateTimes() {
        Collection<UserMeal> Expected = service.getBetweenDateTimes(LocalDateTime.of(2015, 11, 25, 13, 0, 0),
                LocalDateTime.of(2015, 11, 25, 16, 0, 0), ADMIN_ID);
        Collection<UserMeal> Actual = Collections.singletonList(MealTestData.MEAL_FOUR);
        MealTestData.MATCHER.assertCollectionEquals(Expected, Actual);
    }

    @Test
    public void testGetAll() {
        Collection<UserMeal> all = service.getAll(USER_ID);
        MealTestData.MATCHER.assertCollectionEquals(Arrays.asList(MealTestData.MEAL_THREE, MealTestData.MEAL_ONE), all);
    }

    @Test
    public void testUpdate() {
        UserMeal userMeal = new UserMeal(MealTestData.MEAL_ONE);
        userMeal.setCalories(21111);
        userMeal.setDescription("Юзер съел слона");
        service.update(userMeal, MealTestData.USER_ID);
        MealTestData.MATCHER.assertEquals(userMeal, service.get(userMeal.getId(), MealTestData.USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(MealTestData.MEAL_TWO.getId(), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundGet() throws Exception {
        service.get(MealTestData.MEAL_TWO.getId(), USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundUpdate() throws Exception {
        service.update(MealTestData.MEAL_ONE, ADMIN_ID);
    }
}