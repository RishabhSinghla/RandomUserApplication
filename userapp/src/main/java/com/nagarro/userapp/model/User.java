package com.nagarro.userapp.model;

/**
 * @author rishabhsinghla
 * User entity class
 */

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

	public User(@NotBlank(message = "Name is required") String name, String gender, int age, String nationality,
			String verificationStatus, LocalDateTime dateCreated, LocalDateTime dateModified) {
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.nationality = nationality;
		this.verificationStatus = verificationStatus;
		this.dateCreated = dateCreated;
		this.dateModified = dateModified;
	}

	public User(@NotBlank(message = "Name is required") String name, String gender, int age, String nationality) {
		this.name = name;
		this.gender = gender;
		this.age = age;
		this.nationality = nationality;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Name is required")
	private String name;

	private String gender;

	private int age;

	private String nationality;

	private String verificationStatus;

	private LocalDateTime dateCreated;

	private LocalDateTime dateModified;
}
