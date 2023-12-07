package com.nagarro.userapp.validator;

/**
 * @author rishabhsinghla
 * English Alphabets Validator class
 */

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnglishAlphabetsValidator implements ConstraintValidator<EnglishAlphabets, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && value.matches("[a-zA-Z]+");
	}
}
