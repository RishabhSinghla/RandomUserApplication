package com.nagarro.userapp.service;

/**
 * @author rishabhsinghla
 * Interface for sort strategy
 */

import java.util.List;

import com.nagarro.userapp.model.User;

public interface SortStrategy {

	List<User> sortUsers(List<User> users, String sortOrder);
}
