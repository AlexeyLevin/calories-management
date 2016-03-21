package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * User: gkislin
 * Date: 26.08.2014
 */

@Repository
public class JdbcUserMealRepositoryImpl implements UserMealRepository {

    //private static final BeanPropertyRowMapper<UserMeal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(UserMeal.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertUserMeal;

    @Autowired
    public JdbcUserMealRepositoryImpl(DataSource dataSource) {
        this.insertUserMeal = new SimpleJdbcInsert(dataSource)
                .withTableName("MEALS")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public UserMeal save(UserMeal userMeal, int userId) {
        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", userMeal.getId())
                .addValue("user_id", userId)
                .addValue("datetime", Timestamp.valueOf(userMeal.getDateTime()))
                .addValue("description", userMeal.getDescription())
                .addValue("calories", userMeal.getCalories());
        if (userMeal.isNew()) {
            Number newKey = insertUserMeal.executeAndReturnKey(map);
            userMeal.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE meals SET datetime=:datetime, description=:description, calories=:calories WHERE id=:id AND user_id=:user_id", map);
        }
        return userMeal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("DELETE FROM meals WHERE id=? AND user_id=?", id, userId) != 0;
    }

    @Override
    public UserMeal get(int id, int userId) {
        Map<String, Object> rows = jdbcTemplate.queryForMap("SELECT * FROM meals WHERE user_id =? AND id =? ORDER BY datetime DESC", userId, id);
        Timestamp timestamp = (Timestamp) rows.get("datetime");
        return new UserMeal((int) rows.get("id"), timestamp.toLocalDateTime(), rows.get("description").toString(), (int) rows.get("calories"));
    }

    @Override
    public List<UserMeal> getAll(int userId) {
        List<UserMeal> userMeals = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM meals WHERE user_id =? ORDER BY datetime DESC", userId);
        for (Map row : rows) {
            Timestamp timestamp = (Timestamp) row.get("datetime");
            UserMeal userMeal = new UserMeal((int) row.get("id"), timestamp.toLocalDateTime(), row.get("description").toString(), (int) row.get("calories"));
            userMeals.add(userMeal);
        }
        return userMeals;
    }

    @Override
    public List<UserMeal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<UserMeal> userMeals = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM meals WHERE user_id =? AND datetime >=? AND datetime <=? ORDER BY datetime DESC",
                userId, Timestamp.valueOf(startDate), Timestamp.valueOf(endDate));
        for (Map row : rows) {
            Timestamp timestamp = (Timestamp) row.get("datetime");
            UserMeal userMeal = new UserMeal((int) row.get("id"), timestamp.toLocalDateTime(), row.get("description").toString(), (int) row.get("calories"));
            userMeals.add(userMeal);
        }
        return userMeals;
    }
}
