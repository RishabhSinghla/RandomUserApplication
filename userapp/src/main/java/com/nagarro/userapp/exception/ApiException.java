package com.nagarro.userapp.exception;

/**
 * @author rishabhsinghla
 * APIException class for custom exception
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String message;
}
