package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Controller
@RequestMapping(value = "/meals")
public class MealController {

    private final String contextPath = "/meals";

    @Autowired
    private UserMealRestController mealRestController;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getMealList(HttpServletRequest request) {
        return new ModelAndView("mealList", "mealList", mealRestController.getBetween(
                (LocalDate) request.getSession().getAttribute("startDate"),
                (LocalTime) request.getSession().getAttribute("startTime"),
                (LocalDate) request.getSession().getAttribute("endDate"),
                (LocalTime) request.getSession().getAttribute("endTime")));
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable int id) {
        mealRestController.delete(id);
        return "redirect:" + contextPath;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        return new ModelAndView("mealEdit", "meal", new UserMeal(LocalDateTime.now(), "", 1000));
    }

    @RequestMapping(path = "/update/{id}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable int id) {
        return new ModelAndView("mealEdit", "meal", mealRestController.get(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public String save(final String id, final String dateTime, final String description, final int calories) {
        if (id.isEmpty()) {
            final UserMeal userMeal = new UserMeal(LocalDateTime.parse(dateTime), description, calories);
            mealRestController.create(userMeal);
        } else {
            final UserMeal userMeal = new UserMeal(Integer.parseInt(id), LocalDateTime.parse(dateTime), description, calories);
            mealRestController.update(userMeal, Integer.parseInt(id));
        }
        return "redirect:" + contextPath;
    }

    @RequestMapping(value = "/filter", method = RequestMethod.POST)
    public ModelAndView dateTimeFilter(HttpServletRequest request) {
        LocalDate sd = TimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate ed = TimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime st = TimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime et = TimeUtil.parseLocalTime(request.getParameter("endTime"));
        request.getSession().setAttribute("startDate", sd);
        request.getSession().setAttribute("endDate", ed);
        request.getSession().setAttribute("startTime", st);
        request.getSession().setAttribute("endTime", et);
        return new ModelAndView("mealList", "mealList", mealRestController.getBetween(sd, st, ed, et));
    }
}
