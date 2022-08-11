package ru.tinkoff.sirius.web.service;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.tinkoff.sirius.grpc.AccountServiceGrpc;
import ru.tinkoff.sirius.grpc.CreateAccountRequest;
import ru.tinkoff.sirius.web.converter.UserDtoToUserConverter;
import ru.tinkoff.sirius.web.domain.User;
import ru.tinkoff.sirius.web.exception.NonUniqueUserPhoneException;
import ru.tinkoff.sirius.web.model.UserDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Сервис для работы с пользователем
 */
@Service
public class UserService {

    private Map<Long, User> userStore;
    private AtomicLong idSeq;

    @Autowired
    private UserDtoToUserConverter converter;

    @GrpcClient("account")
    private AccountServiceGrpc.AccountServiceBlockingStub accountServiceBlockingStub;

    public UserService() {
        this.userStore = new ConcurrentHashMap<>();
        this.idSeq = new AtomicLong(1);
    }

    public UserDto getById(Long id) {
        return converter.convert(userStore.get(id));
    }

    public List<UserDto> getAll() {
        return userStore.values().stream()
            .map(converter::convert).collect(Collectors.toList());
    }


    public UserDto create(UserDto userDto) {
        validate(userDto);
        var id = idSeq.getAndIncrement();
        userDto.setId(id);
        var user = converter.convert(userDto);
        user.setId(id);

        userStore.put(id, user);
        var account = accountServiceBlockingStub
            .create(CreateAccountRequest.newBuilder()
                .setId(String.valueOf(id))
                .setLogin(user.getLogin())
                .build()).getAccount();
        return converter.convert(user);
    }

    public UserDto update(UserDto userDto) {
        validate(userDto);
        if (userDto.getId() != null && userStore.containsKey(userDto.getId())) {
            userStore.put(userDto.getId(), converter.convert(userDto));
            return userDto;
        }
        if (userDto.getId() == null) {
            return create(userDto);
        }
        userStore.put(userDto.getId(), converter.convert(userDto));
        return userDto;
    }

    public void deleteById(Long id) {
        userStore.remove(id);
    }

    private void validate(UserDto userDto) {
        String phoneNumber = userDto.getPhoneNumber();
        if (phoneNumber == null) {
            return;
        }
        Optional<User> foundUser = userStore.values().stream()
            .filter(userDto1 -> phoneNumber.equals(userDto1.getPhoneNumber()))
            .findAny();
        if (foundUser.isPresent()) {
            throw new NonUniqueUserPhoneException(String.format("Phone %s is already exists", phoneNumber));
        }
    }

}
