package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> USER_LIST = Arrays.asList(
            new User(null, "AuserName1", "email1", "password1", Role.ROLE_ADMIN),
            new User(null, "CuserName2", "email2", "password2", Role.ROLE_USER),
            new User(null, "BuserName3", "email@3", "password3", Role.ROLE_USER)
    );
}
