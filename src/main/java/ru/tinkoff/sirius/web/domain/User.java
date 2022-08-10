package ru.tinkoff.sirius.web.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class User {
    private long id;
    private String login;
    private String fio;
    private String account;
    private String phoneNumber;
}
