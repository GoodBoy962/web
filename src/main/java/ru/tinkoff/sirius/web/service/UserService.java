package ru.tinkoff.sirius.web.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.tinkoff.sirius.web.exception.NonUniqueUserPhoneException;
import ru.tinkoff.sirius.web.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Сервис для работы с пользователем
 */
@Service
@RequestMapping
public class UserService {

    private Map<Long, User> userStore;
    private long idSeq;

    public UserService() {
        this.userStore = new HashMap<>();
    }

    public User getById(Long id) {
        return userStore.get(id);
    }

    public User create(User user) {
        validate(user);
        var id = idSeq++;
        user.setId(id);
        userStore.put(id, user);
        return user;
    }

    public User update(User user) {
        validate(user);
        if (user.getId() != null && userStore.containsKey(user.getId())) {
            userStore.put(user.getId(), user);
            return user;
        }
        if (user.getId() == null) {
            return create(user);
        }
        userStore.put(user.getId(), user);
        return user;
    }

    public void deleteById(Long id) {
        userStore.remove(id);
    }

    private void validate(User user) {
        String phoneNumber = user.getPhoneNumber();
        if (phoneNumber == null) {
            return;
        }
        Optional<User> foundUser = userStore.values().stream()
                .filter(user1 -> phoneNumber.equals(user1.getPhoneNumber()))
                .findAny();
        if (foundUser.isPresent()) {
            throw new NonUniqueUserPhoneException(String.format("Phone %s is already exists", phoneNumber));
        }
    }

}
