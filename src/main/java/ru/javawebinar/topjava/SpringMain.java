package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    private static final Logger LOG = LoggerFactory.getLogger(SpringMain.class);

    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            LOG.info(Arrays.toString(applicationContext.getBeanDefinitionNames()));
            LOG.info("step0////////////////////////////////////////////");
            UserMealRestController userMealRestController = applicationContext.getBean(UserMealRestController.class);
            userMealRestController.create(new UserMeal(LocalDateTime.of(2001, 11, 25, 11, 15),"blabla",20));
            userMealRestController.create(new UserMeal(LocalDateTime.of(2001, 11, 25, 11, 13),"alabla",21));
            userMealRestController.create(new UserMeal(LocalDateTime.of(2001, 11, 25, 11, 14),"clabla",22));
            LOG.info("step1////////////////////////////////////////////");
            LOG.info(String.valueOf(userMealRestController.getUserMealWithExceed()));
            LOG.info(userMealRestController.get(3).toString());
            userMealRestController.delete(2);
            userMealRestController.update(new UserMeal(LocalDateTime.of(2001, 11, 26, 11, 13),"dlabla",22222), 3);
            LOG.info("step2////////////////////////////////////////////");
            LOG.info(String.valueOf(userMealRestController.getUserMealWithExceed()));
            LOG.info(userMealRestController.get(4).toString());
            userMealRestController.delete(4);
            userMealRestController.update(new UserMeal(LocalDateTime.of(2001, 11, 26, 11, 13),"dlabla",22222), 4);
            LOG.info(userMealRestController.get(4).toString());
            LOG.info("step3////////////////////////////////////////////");
            LOG.info(String.valueOf(userMealRestController.getUserMealWithExceed()));
            LOG.info(String.valueOf(userMealRestController.getUserMealWithExceed()));
            LOG.info(userMealRestController.get(4).toString());
            userMealRestController.delete(4);
            userMealRestController.update(new UserMeal(LocalDateTime.of(2001, 11, 26, 11, 13),"dlabla",22222), 4);
            userMealRestController.create(new UserMeal(LocalDateTime.of(2001, 11, 26, 11, 13),"dlabla",22222));
            LOG.info(userMealRestController.get(4).toString());
            LOG.info("step4////////////////////////////////////////////");
            /*AdminRestController adminUserController = applicationContext.getBean(AdminRestController.class);
            System.out.println(adminUserController.create(new User(null, "AuserName", "Aemail@4", "password", Role.ROLE_ADMIN)));
            System.out.println(adminUserController.getByMail("Aemail@4"));
            adminUserController.create(new User(null, "BuserName", "Bemail@3", "password", Role.ROLE_USER));
            adminUserController.update(new User(4, "BuserName", "Bemail@4", "password", Role.ROLE_USER), 4);
            System.out.println(adminUserController.getAll());
            System.out.println(adminUserController.getByMail("Bemail@4"));
            adminUserController.delete(4);
            System.out.println(adminUserController.getByMail("Bemail@4"));*/
        }
    }
}
