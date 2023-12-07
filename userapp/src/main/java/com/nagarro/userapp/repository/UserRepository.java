package com.nagarro.userapp.repository;

/**
 * @author rishabhsinghla
 * User Repository interface using JPA
 */

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.userapp.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
