package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UsersUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    {
        LOG.info("init mock user repository");
        UsersUtil.USER_LIST.forEach(this::save);
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            LOG.info("set id to new user " + user.getId());
        }
        LOG.info("save user " + user.getId());
        LOG.info("repository size: " + repository.size() + " id counter = " + counter.get());
        return repository.put(user.getId(), user);
    }

    @Override
    public boolean delete(int id) {
        LOG.info("repository size:  " + repository.size() + " id counter = " + counter.get());
        User user = repository.remove(id);
        LOG.info("delete user " + id);
        LOG.info("repository size:  " + repository.size() + " id counter = " + counter.get());
        return user != null;
    }

    @Override
    public User get(int id) {
        LOG.info("try to get user by id " + id);
        return repository.get(id);
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("try to get user by email " + email);
        return repository.values()
                .parallelStream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAll() {
        LOG.info("get all users");
        return repository.values()
                .stream()
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .collect(Collectors.toList());
    }
}