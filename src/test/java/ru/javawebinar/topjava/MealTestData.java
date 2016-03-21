package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.Objects;

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


    public static final UserMeal MEAL_ONE = new UserMeal(MEAL_ONE_ID, LocalDateTime.of(2015, 11, 25, 11, 00, 00), "Юзер поел бобра", 1500);
    public static final UserMeal MEAL_TWO = new UserMeal(MEAL_TWO_ID, LocalDateTime.of(2015, 11, 25, 11, 00, 00), "Админ поел бобра", 1500);
    public static final UserMeal MEAL_THREE = new UserMeal(MEAL_THREE_ID, LocalDateTime.of(2015, 11, 25, 11, 00, 00), "Юзер поел осла", 500);
    public static final UserMeal MEAL_FOUR = new UserMeal(MEAL_FOUR_ID, LocalDateTime.of(2015, 11, 25, 11, 00, 00), "Юзер поел осла", 500);

    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static class TestUserMeal extends UserMeal {

        public TestUserMeal(UserMeal u) {
            super(u.getId(), u.getDateTime(), u.getDescription(), u.getCalories());
        }

        public TestUserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
            super(id, dateTime, description, calories);
        }

        public TestUserMeal(LocalDateTime dateTime, String description, int calories) {
            super(dateTime, description, calories);
        }


        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            TestUserMeal that = (TestUserMeal) o;
            return Objects.equals(this.toString(), that.toString());
        }
    }
}
