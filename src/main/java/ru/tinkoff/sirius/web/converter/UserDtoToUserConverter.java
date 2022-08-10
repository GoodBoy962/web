package ru.tinkoff.sirius.web.converter;

import org.springframework.stereotype.Component;
import ru.tinkoff.sirius.web.domain.User;
import ru.tinkoff.sirius.web.model.UserDto;

@Component
public class UserDtoToUserConverter {

    public User convert(UserDto dto) {
        return new User()
            .setFio(dto.getFio())
            .setPhoneNumber(dto.getPhoneNumber())
            .setLogin(dto.getLogin());
    }

    public UserDto convert(User user) {
        return new UserDto()
            .setId(user.getId())
            .setLogin(user.getLogin())
            .setFio(user.getFio())
            .setPhoneNumber(user.getPhoneNumber());

    }

}
