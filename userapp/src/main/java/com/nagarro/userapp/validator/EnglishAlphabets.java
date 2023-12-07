package com.nagarro.userapp.validator;

/**
 * @author rishabhsinghla
 * English Alphabets custom annotation
 */
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = EnglishAlphabetsValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnglishAlphabets {

	String message() default "Invalid English alphabets";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
