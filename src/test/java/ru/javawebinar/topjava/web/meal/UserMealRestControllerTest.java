package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class UserMealRestControllerTest extends AbstractControllerTest {
    public static final String REST_URL = UserMealRestController.REST_URL + '/';

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID).contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
        List<UserMeal> expectedUserMeals = new ArrayList<>(USER_MEALS);
        expectedUserMeals.remove(MEAL1);
        MATCHER.assertCollectionEquals(expectedUserMeals, userMealService.getAll(USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(WITH_EXCEED_MATCHER.contentListMatcher(UserMealsUtil.getWithExceeded(USER_MEALS, USER.getCaloriesPerDay()))));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated = new UserMeal(MEAL1_ID, of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
        updated.setDescription("Обновленный завтрак");
        updated.setCalories(200);
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, userMealService.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void testCreateWithLocation() throws Exception {
        UserMeal expected = new UserMeal(null, of(2014, Month.MAY, 30, 13, 0), "new meal", 150 );
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isCreated());

        UserMeal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        ArrayList<UserMeal> expectedList = new ArrayList<>(USER_MEALS);
        expectedList.add(expected);
        MATCHER.assertCollectionEquals(expectedList, userMealService.getAll(USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        // url - http://localhost:8080/topjava/rest/meals/"between?startDateTime=2015-05-30T15:00:00&endDateTime=2015-05-30T20:00:00"
        // expected JSON [{"id":100004,"dateTime":"2015-05-30T20:00:00","description":"Ужин","calories":500,"exceed":false}]
        mockMvc.perform(get(REST_URL + "between?startDateTime=2015-05-30T15:00:00&endDateTime=2015-05-30T20:00:00")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(WITH_EXCEED_MATCHER.contentListMatcher(Collections.singletonList(UserMealsUtil.createWithExceed(MEAL3, false))));
    }
}