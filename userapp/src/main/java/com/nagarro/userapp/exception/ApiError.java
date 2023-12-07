package com.nagarro.userapp.exception;

/**
 * @author rishabhsinghla
 * Exception class for APIError
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiError {

	private final String message;
}
