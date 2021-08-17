package ru.tinkoff.sirius.web.model;

import lombok.Builder;
import lombok.Getter;

/**
 * Wrapper для ошибок
 */
@Builder
@Getter
public class ApiErrorResponse {
    private String errorCode;
    private String errorMessage;
}
