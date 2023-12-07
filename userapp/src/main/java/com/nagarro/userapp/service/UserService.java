package com.nagarro.userapp.service;

/**
 * @author rishabhsinghla
 * UserService interface
 */
import java.util.List;

import javax.validation.constraints.Min;

import com.nagarro.userapp.model.User;
import com.nagarro.userapp.validator.Numeric;

public interface UserService {

	List<User> generateRandomUsers(@Numeric int size);

	List<User> verifyUsers(List<User> users);

	void saveUsersToDB(List<User> users);

	List<User> getUsersFromDB(String sortType, String sortOrder, @Numeric int limit, @Min(0) int offset);
}
