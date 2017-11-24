package com.repositories;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entities.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, String>{

	Collection<User> findByEmail(String email);
}
