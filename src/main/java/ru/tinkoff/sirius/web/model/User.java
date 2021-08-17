package ru.tinkoff.sirius.web.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Сущность пользователя
 */
@Data
@Schema(description = "Пользователь")
public class User {

    private Long id;
    @NotBlank
    private String login;
    private String fio;
    //    @ValidPhoneNumber
    private String phoneNumber;
    private String account;

}
