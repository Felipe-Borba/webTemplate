package com.felipeborba.webTemplate.service.validation;

import com.felipeborba.webTemplate.controller.exception.FieldMessage;
import com.felipeborba.webTemplate.dto.UserUpdateDTO;
import com.felipeborba.webTemplate.entity.User;
import com.felipeborba.webTemplate.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserUpdateValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {
        @SuppressWarnings("unchecked")
        var uriVars = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String userId = uriVars.get("id");

        List<FieldMessage> list = new ArrayList<>();

        var user = repository.findByLogin(dto.getLogin());
        if (user.isPresent() && !user.get().getId().equals(userId)) {
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
