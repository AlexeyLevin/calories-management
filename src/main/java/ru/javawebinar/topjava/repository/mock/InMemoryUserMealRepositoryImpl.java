package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserMealRepositoryImpl.class);

    private Map<Integer, Map<Integer, UserMeal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    {
        LOG.info("init mock user-meals repository");
        repository.put(1, new ConcurrentHashMap<>());
        repository.put(2, new ConcurrentHashMap<>());
        repository.put(3, new ConcurrentHashMap<>());
    }
    @Override
    public UserMeal save(int userId, UserMeal userMeal) {
        Map<Integer, UserMeal> userMealMap = repository.get(userId);
        if (userMealMap == null) {
            LOG.info("create new repository from user " + userId);
            repository.computeIfAbsent(userId, ConcurrentHashMap::new);
            userMealMap = repository.get(userId);
        }
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
            LOG.info("User " + userId + " set id to new meal " + userMeal.getId());
            userMealMap.put(userMeal.getId(), userMeal);
            LOG.info("User " + userId + " put new meal " + userMeal.getId());
            return userMeal;
        }
        if (userMealMap.containsKey(userMeal.getId())) {
            userMealMap.put(userMeal.getId(), userMeal);
            LOG.info("User " + userId + " edit meal " + userMeal.getId());
            LOG.info("User " + userId + " meal list size:  " + userMealMap.size() + " id counter = " + counter.get());
            return userMeal;
        } else {
            LOG.info("incorrect meal from this user");
            return null;
        }
    }

    @Override
    public boolean delete(int userId ,int mealId) {
        LOG.info("User " + userId + " meal list size:  " + repository.get(userId).size() + " id counter = " + counter.get());
        UserMeal userMeal = repository.get(userId).remove(mealId);
        LOG.info("User " + userId + " delete meal " + mealId);
        LOG.info("User " + userId + " meal list size:  " + repository.get(userId).size() + " id counter = " + counter.get());
        return userMeal != null;
    }

    @Override
    public UserMeal get(int userId, int mealId) {
        LOG.info("User " + userId + " get meal " + mealId);
        return repository.get(userId).get(mealId);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        LOG.info("Get all meal to user " + userId);
        return repository.get(userId).values()
                .stream()
                .sorted((o1, o2) -> -o1.getDateTime().toLocalTime().compareTo(o2.getDateTime().toLocalTime()))
                .collect(Collectors.toList());
    }
}