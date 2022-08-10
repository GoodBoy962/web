package ru.tinkoff.sirius.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Валидатор, для проверки корректности номера телефона
 */
public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    private static final Pattern phonePattern =
        Pattern.compile("^([+]{0,1})(7[0-9]{10})$");

    @Override
    public boolean isValid(String phone, ConstraintValidatorContext context) {
        return phone != null && phonePattern.matcher(phone).matches();
    }

}
