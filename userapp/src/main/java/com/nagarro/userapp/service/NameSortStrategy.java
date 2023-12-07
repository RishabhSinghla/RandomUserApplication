package com.nagarro.userapp.service;

/**
 * @author rishabhsinghla
 * Sort Users based on Name
 */

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.nagarro.userapp.model.User;

@Component
public class NameSortStrategy implements SortStrategy {

	@Override
	public List<User> sortUsers(List<User> users, String sortOrder) {
		return users.stream().sorted(Comparator.comparingInt(
				user -> sortOrder.equalsIgnoreCase("EVEN") ? user.getName().length() : -user.getName().length()))
				.collect(Collectors.toList());
	}
}
