package com.felipeborba.webTemplate.services.validation;

import com.felipeborba.webTemplate.dto.UserUpdateDTO;
import com.felipeborba.webTemplate.entities.User;
import com.felipeborba.webTemplate.repositories.UserRepository;
import com.felipeborba.webTemplate.resouces.exceptions.FieldMessage;
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
        long userId = Long.parseLong(uriVars.get("id"));

        List<FieldMessage> list = new ArrayList<>();

        User user = repository.findByEmail(dto.getEmail());
        if (user != null && userId != user.getId()) {
            //TODO se o front tentar atualizar o nome, mas mandar o obj inteiro e nesse obj vai ter o email do próprio usuário isso vai gerar um erro aqui...
            list.add(new FieldMessage("email", "Email já existe"));
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
