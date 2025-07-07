package com.varshini.journalapp.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.varshini.journalapp.models.JournalModels;
import com.varshini.journalapp.models.User;
import com.varshini.journalapp.repository.JournalEntryRepository;

import jakarta.transaction.Transactional;

@Service //holds business logic of our app
public class JournalEntryService {
	@Autowired
	private JournalEntryRepository journalEntryRepository; //dependency injection of repo in service 
	
	@Autowired
	private UserService userService;

//	public JournalEntryService(JournalEntryRepository journalEntryRepository) {
//		super();
//		this.journalEntryRepository = journalEntryRepository;
//	}
	
	//Its a method created to save - save()
	@Transactional //@Transactional ensures that a method runs within a database transaction, so all operations inside it are either fully completed or fully rolled back if an error occurs.
	public void SaveEntry(JournalModels journalEntry, String username) {
	   try {
		   User user = userService.findByUsername(username);// Fetches user by username.
		    journalEntry.setDate(LocalDateTime.now()); // Sets current timestamp for journal entry.
		    journalEntry.setUser(user); //Sets the `user` object (the fetched user) as the owner of the `journalEntry`. //Associates user with journal entry.
		    JournalModels saved = journalEntryRepository.save(journalEntry); //Saves journal entry to database.
		    user.getJournalEntries().add(saved); // Optional, since setting the user already links it, //Adds saved entry to user's in-memory list.
//		    user.setUsername(null);//lets say theres a sentence here which causes a error and the execution stops here and the next line is not executed at all. So to avoid this we use -	@Transactional
		    userService.saveUser(user); //Saves updated user with new journal entry.
	   } catch (Exception e) {
		   System.out.println(e);
		   throw new RuntimeException("An error occured while saving the entry.",e);
	   }
	}
	
	public void SaveEntry(JournalModels journalEntry) {
	    journalEntryRepository.save(journalEntry);
	}

	
	//Now to get all entries we use findAll()
	public List<JournalModels> getAll(){
		return journalEntryRepository.findAll();
	}
	
	//Now to get the entry by its id:  - findById()
	public Optional<JournalModels> findById(Long id) {
		return journalEntryRepository. findById(id);
		
	}
	
	//To delete by id   -deleteById()
	
	//For Sql (when dealing with delete by id also deleting that id journal entry in users also)
//	public void deleteById(Long id) {
//		journalEntryRepository.deleteById(id);  //This method in JPA repo doesnt return anything
//	}
	
	//For MongoDB -works same but needs extra code as below
	@Transactional 
	public boolean deleteById(Long id, String username) {
		boolean removed = false;
		try {
			User user = userService.findByUsername(username);
			removed = user.getJournalEntries().removeIf(x -> id.equals(x.getId())); 
			if(removed) {
				userService.saveUser(user);
				journalEntryRepository.deleteById(id);  //This method in JPA repo doesnt return anything
			}
		}catch(Exception e) {
			System.out.println(e);
			throw new RuntimeException("An error occured while saving entry");
		}
		return removed;
	}
	
	
}
