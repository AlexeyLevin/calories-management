package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger LOG = getLogger(MealServlet.class);
    private UserMealServiceImpl userMealServiceImpl = UserMealServiceImpl.getInstance();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.debug("redirect to mealList");

        if (request.getParameter("removeId") != null) {
            userMealServiceImpl.remove(Integer.parseInt(request.getParameter("removeId")));
        }
        request.setAttribute("maxId", userMealServiceImpl.getCurrentMaxId());
        request.setAttribute("userMealWithExceeds", userMealServiceImpl.getListWithExceed());
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        int id;
        try {
            if (request.getParameter("mealId") == null) {
                id = userMealServiceImpl.getNewId();
            } else {
                id = Integer.parseInt(request.getParameter("mealId"));
            }
            UserMeal userMeal = new UserMeal(id,
                    LocalDateTime.of(LocalDate.parse(request.getParameter("mealDate")),
                            LocalTime.parse(request.getParameter("mealTime"))),
                    request.getParameter("mealDescription"),
                    Integer.parseInt(request.getParameter("mealCalories")));
            userMealServiceImpl.save(userMeal);
        } catch (Exception e) {
            LOG.debug(Arrays.toString(e.getStackTrace()));
            request.setAttribute("error", true);
        }

        request.setAttribute("maxId", userMealServiceImpl.getCurrentMaxId());
        request.setAttribute("userMealWithExceeds", userMealServiceImpl.getListWithExceed());
        request.getRequestDispatcher("/mealList.jsp").forward(request, response);
    }
}
