package ru.tinkoff.sirius.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumberValidator.class)
public @interface ValidPhoneNumber {

    String message() default "Невалидный номер телефона"; // обязательное поле

    Class<?>[] groups() default {}; //необязательное поле

    Class<? extends Payload>[] payload() default {}; //необязательное поле

}
