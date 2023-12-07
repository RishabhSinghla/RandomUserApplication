package com.nagarro.userapp.service;

/**
 * @author rishabhsinghla
 * UserServiceImpl for creating random users, verifying, saving and retrieving users
 */

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.annotation.PreDestroy;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nagarro.userapp.exception.ApiException;
import com.nagarro.userapp.model.User;
import com.nagarro.userapp.repository.UserRepository;
import com.nagarro.userapp.validator.Numeric;

@Service
public class UserServiceImpl implements UserService {

	private final ExecutorService executorService = Executors.newFixedThreadPool(3);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private WebClient api1Client;

	@Autowired
	private WebClient api2Client;

	@Autowired
	private WebClient api3Client;

	private static class RandomUserResponse {
		private List<Map<String, Object>> results;

		public List<Map<String, Object>> getResults() {
			return results;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> generateRandomUsers(@Numeric int size) {
		List<User> users = new ArrayList<>();

		for (int i = 0; i < size; i++) {
			ParameterizedTypeReference<RandomUserResponse> responseType = new ParameterizedTypeReference<>() {
			};
			RandomUserResponse response = api1Client.get().retrieve().bodyToMono(responseType).block();

			if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
				Map<String, Object> resultMap = response.getResults().get(0);
				Map<String, Object> nameMap = (Map<String, Object>) resultMap.get("name");
				String fullName = nameMap.get("first") + " " + nameMap.get("last");
				String gender = getGender(fullName);
				Map<String, Object> dobMap = (Map<String, Object>) resultMap.get("dob");
				int age = (int) dobMap.get("age");
				String nationality = (String) resultMap.get("nat");
				users.add(new User(fullName, gender, age, nationality));
			} else {
				System.out.println("No results available from API.");
			}
		}
		return users;
	}

	@Override
	public List<User> verifyUsers(List<User> users) {
		List<CompletableFuture<Void>> futures = users.stream().map(user -> CompletableFuture.runAsync(() -> {
			try {
				String nationality = getNationality(user.getName());
				String gender = getGender(user.getName());

				if (user.getNationality().equalsIgnoreCase(nationality) && user.getGender().equalsIgnoreCase(gender)) {
					user.setVerificationStatus("VERIFIED");
					user.setGender(gender);
				} else {
					user.setVerificationStatus("UNVERIFIED");
				}
			} catch (Exception e) {
				user.setVerificationStatus("UNVERIFIED");
			}
		}, executorService)).collect(Collectors.toList());

		CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

		try {
			allOf.join();
		} catch (CompletionException e) {
			users.forEach(user -> user.setVerificationStatus("UNVERIFIED"));
		}

		return users;
	}

	@Override
	public void saveUsersToDB(List<User> users) {
		users.forEach(user -> {
			user.setDateCreated(LocalDateTime.now());
			user.setDateModified(LocalDateTime.now());
		});

		userRepository.saveAll(users);
	}

	@Override
	public List<User> getUsersFromDB(String sortType, String sortOrder, @Numeric int limit, @Min(0) int offset) {
		Sort sort = sortOrder.equalsIgnoreCase("EVEN") ? Sort.by(Sort.Direction.ASC, sortType)
				: Sort.by(Sort.Direction.DESC, sortType);

		List<User> users = userRepository.findAll(sort);

		int fromIndex = Math.min(offset, users.size());
		int toIndex = Math.min(offset + limit, users.size());

		List<User> paginatedUsers = users.subList(fromIndex, toIndex);

		return paginatedUsers.stream().sorted((user1, user2) -> {
			if ("Name".equalsIgnoreCase(sortType)) {
				return sortOrder.equalsIgnoreCase("EVEN")
						? Integer.compare(user1.getName().length(), user2.getName().length())
						: Integer.compare(user2.getName().length(), user1.getName().length());
			} else if ("Age".equalsIgnoreCase(sortType)) {
				return sortOrder.equalsIgnoreCase("EVEN") ? Integer.compare(user1.getAge(), user2.getAge())
						: Integer.compare(user2.getAge(), user1.getAge());
			}
			return 0;
		}).collect(Collectors.toList());
	}

	private String getNationality(String fullName) {
		String firstName = getFirstName(fullName);
		ParameterizedTypeReference<Map<String, List<Map<String, Object>>>> responseType = new ParameterizedTypeReference<>() {
		};
		Map<String, List<Map<String, Object>>> responseMap = api2Client.get()
				.uri(uriBuilder -> uriBuilder.queryParam("name", firstName).build()).retrieve().bodyToMono(responseType)
				.block();

		if (responseMap != null && responseMap.containsKey("country")) {
			List<Map<String, Object>> countryList = responseMap.get("country");

			if (!countryList.isEmpty()) {
				return (String) countryList.get(0).get("country_id");
			} else {
				throw new ApiException("Country information not available for name: " + firstName);
			}
		}
		return "Unknown";
	}

	private String getGender(String fullName) {
		String firstName = getFirstName(fullName);
		ParameterizedTypeReference<Map<String, Object>> responseType = new ParameterizedTypeReference<>() {
		};
		Map<String, Object> responseMap = api3Client.get()
				.uri(uriBuilder -> uriBuilder.queryParam("name", firstName).build()).retrieve().bodyToMono(responseType)
				.block();

		if (responseMap != null && responseMap.containsKey("gender")) {
			return (String) responseMap.get("gender");
		}
		return "Unknown";
	}

	private String getFirstName(String fullName) {
		return fullName.split(" ")[0];
	}

	@PreDestroy
	public void shutdownExecutorService() {
		executorService.shutdown();
	}
}
