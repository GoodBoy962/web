package ru.tinkoff.sirius.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tinkoff.sirius.web.service.UserService;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

}
