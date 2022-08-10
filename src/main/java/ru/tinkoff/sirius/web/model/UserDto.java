package ru.tinkoff.sirius.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.tinkoff.sirius.web.validator.ValidPhoneNumber;

import javax.validation.constraints.NotBlank;

/**
 * Сущность пользователя
 */
@Getter
@Setter
@Accessors(chain = true)
@Schema(description = "Пользователь")
public class UserDto {

    private Long id;
    @NotBlank
    private String login;
    @Schema(description = "Фамилия Имя Отчество пользователя", required = true)
    private String fio;
    @ValidPhoneNumber
    @Schema(description = "Телефонный номер")
    private String phoneNumber;

}
