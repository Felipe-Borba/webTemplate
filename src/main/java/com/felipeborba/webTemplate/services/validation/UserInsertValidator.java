package com.felipeborba.webTemplate.services.validation;

import com.felipeborba.webTemplate.dto.UserInsertDTO;
import com.felipeborba.webTemplate.entities.User;
import com.felipeborba.webTemplate.repositories.UserRepository;
import com.felipeborba.webTemplate.resouces.exceptions.FieldMessage;
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

		User user = repository.findByEmail(dto.getEmail());
		if (user != null) {
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
