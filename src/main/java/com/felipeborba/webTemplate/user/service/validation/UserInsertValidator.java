package com.felipeborba.webTemplate.user.service.validation;

import com.felipeborba.webTemplate.exception.handler.FieldMessage;
import com.felipeborba.webTemplate.user.dto.UserInsertDTO;
import com.felipeborba.webTemplate.user.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {
    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();

        var user = repository.findByLogin(dto.getLogin());
        if (user.isPresent()) {
            list.add(new FieldMessage("login", "Login already registered"));
        }

        insertOnBeanValidationErrorList(context, list);
        return list.isEmpty();
    }

    private static void insertOnBeanValidationErrorList(ConstraintValidatorContext context, List<FieldMessage> list) {
        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }
    }
}
