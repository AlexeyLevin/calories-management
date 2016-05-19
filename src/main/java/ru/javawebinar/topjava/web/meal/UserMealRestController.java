package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.to.UserMealWithExceed;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController extends AbstractUserMealController {

    public UserMeal get(int mealId) {
        return super.get(LoggedUser.getId(), mealId);
    }

    public UserMeal create(UserMeal userMeal) {
        return super.create(LoggedUser.getId(), userMeal);
    }

    public void delete(int mealId) {
        super.delete(LoggedUser.getId(), mealId);
    }

    public void update(UserMeal userMeal, int updateMealId) {
        super.update(LoggedUser.getId(), userMeal, updateMealId);
    }

    public List<UserMealWithExceed> getUserMealWithExceed() {
        return super.getUserMealWithExceed(LoggedUser.getId(), LoggedUser.getCaloriesPerDay());
    }
}
