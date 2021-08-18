package ru.tinkoff.sirius.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * Сущность пользователя
 */
@Getter
@Setter
@Schema(description = "Пользователь")
public class User {

    private Long id;
    @NotBlank
    private String login;
    @Schema(description = "Фамилия Имя Отчество пользователя", required = true)
    private String fio;
    //    @ValidPhoneNumber
    @Schema(description = "Телефонный номер", required = true)
    private String phoneNumber;
    @Schema(description = "Аккаунт пользователя")
    private String account;

}
