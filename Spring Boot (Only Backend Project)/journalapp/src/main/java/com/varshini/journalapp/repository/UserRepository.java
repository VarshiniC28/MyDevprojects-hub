package com.varshini.journalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varshini.journalapp.models.JournalModels;
import com.varshini.journalapp.models.User;

public interface UserRepository extends JpaRepository<User, Long>{

	User findByUsername(String username); // This is our our own method unlike deleteById, findById etc from JPA Repository
	
	void deleteByUsername(String username);
}
