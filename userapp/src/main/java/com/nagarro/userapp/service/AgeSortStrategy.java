package com.nagarro.userapp.service;

/**
 * @author rishabhsinghla
 * Sort users based on Age
 */

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nagarro.userapp.model.User;

@Component
public class AgeSortStrategy implements SortStrategy {

	@Override
	public List<User> sortUsers(List<User> users, String sortOrder) {
		return users.stream().sorted(Comparator.comparingInt(User::getAge)).collect(Collectors.toList());
	}
}
