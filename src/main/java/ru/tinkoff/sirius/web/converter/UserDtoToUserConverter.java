package ru.tinkoff.sirius.web.converter;

import org.springframework.stereotype.Component;
import ru.tinkoff.sirius.web.domain.User;
import ru.tinkoff.sirius.web.model.UserDto;

@Component
public class UserDtoToUserConverter {

    public User convert(UserDto dto) {
        var user = new User();
        user.setFio(dto.getFio());
        user.setPhoneNumber(dto.getPhoneNumber());
        return user;
    }

    public UserDto convert(User user) {
        return new UserDto()
            .setId(user.getId())
            .setFio(user.getFio())
            .setPhoneNumber(user.getPhoneNumber());

    }

}
