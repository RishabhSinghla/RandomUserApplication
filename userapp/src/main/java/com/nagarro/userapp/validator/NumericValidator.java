package com.nagarro.userapp.validator;

/**
 * @author rishabhsinghla
 * Numeric Validator class
 */

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NumericValidator implements ConstraintValidator<Numeric, Integer> {

	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		return value != null && value >= 1 && value <= 5;
	}
}
