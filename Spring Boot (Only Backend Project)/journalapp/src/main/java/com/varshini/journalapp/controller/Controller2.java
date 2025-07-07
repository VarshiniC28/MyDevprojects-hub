package com.varshini.journalapp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.varshini.journalapp.models.JournalModels;
import com.varshini.journalapp.models.User;
import com.varshini.journalapp.service.JournalEntryService;
import com.varshini.journalapp.service.UserService;

@RestController
@RequestMapping("/journal")
public class Controller2 {

    private final DaoAuthenticationProvider daoAuthenticationProvider;
	
	@Autowired
	private JournalEntryService journalEntryService;
	
	@Autowired
	private UserService userService;

    Controller2(DaoAuthenticationProvider daoAuthenticationProvider) {
        this.daoAuthenticationProvider = daoAuthenticationProvider;
    }

	@GetMapping //not using path variable anymore , since we will be getting authenticated username form securitycontextHolder's context by getAuthentication() which has authenticated details like username and password,..and store it in the authentication variable and the getName() from authentication var and assign to a Stirng username variable which is used there later in that method.
	public ResponseEntity<?> getJournalEntriesOfUser(){ //localjost:8080/journal  - GET
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userService.findByUsername(username); //creating object of user class and use userService.findByUsername(username); method from its service
//		List<JournalModels> all = journalEntryService.getAll(); //Method from Service which has method findAll() method provided by JPA repo // this getALl() method now gets all the entries.
		List<JournalModels> all = user.getJournalEntries(); //getting the journalEntries key from the user object which has all username, password, journalentries OBJECT of that particualar user
		if(all!=null && !all.isEmpty()) {
			return new ResponseEntity<>(all, HttpStatus.OK);
		}else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PostMapping  //not using path variable anymore , since we will be getting authenticated username form securitycontextHolder's context by getAuthentication() which has authenticated details like username and password,..and store it in the authentication variable and the getName() from authentication var and assign to a Stirng username variable which is used there later in that method.
	public ResponseEntity<JournalModels> createEntry(@RequestBody JournalModels myEntry ) {  //localhost:8080/journal     -POST
		//To set date and time automatically added below line after changing the datatype from Date to LocalDateTime
		try {
//			myEntry.setDate(LocalDateTime.now());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String username = authentication.getName();
			journalEntryService.SaveEntry(myEntry, username); // Saves to DB
			 // Calls the `SaveEntry` method in the `journalEntryService`, passing the `myEntry` and the `username`. 
	        // This saves the journal entry to the database and links it with the user.
			return new ResponseEntity<>(myEntry, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
//		System.out.println("Successfully added the entry entered in API to database");// make void and this prints in eclipse console not on Po API
	}
	
//	@GetMapping("id/{myId}")
//	public JournalModels getJournalModelById(@PathVariable Long myId) {
//		return journalEntryService.findById(myId).orElse(null); //since the findById method in service is optional its must to add orElse statement
//	}
	
	//To send the HTTP status code along with response
	//Now, consider that u r requesting the id/7 which doenst exist still the Http status code will be 200OK which means the request was fulfilled successfully
	// But The actual HTTP status code shuld have been 404 NotFound , soo we ourselves send this HTTP status code along with the response from the server to the client whenever a particular id we are requesting dosent exist i.e 404 NOT FOUND
	//To do that we use ResponseEntity class which is extended to HttpEntity as below
	
	@GetMapping("id/{myId}")
	public ResponseEntity<?> getJournalModelById(@PathVariable Long myId) { //Change the return type to ResponseEntity
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userService.findByUsername(username);
		List <JournalModels> collect = user.getJournalEntries().stream().filter(x -> x.getId()!=0 && x.getId() == myId).collect(Collectors.toList());
		//or
//		List<JournalModels> collect = user.getJournalEntries().stream().filter(x -> Long.valueOf(x.getId()).equals(myId)).collect(Collectors.toList()); //x.getId() is of type long (primitive) but .equals() is being used — which is a method from Object, so it requires boxing.
		if(!collect.isEmpty()) {
			Optional<JournalModels> journalEntry =  journalEntryService.findById(myId); // Make a Optional class object which can contain data or may not.
			if(journalEntry.isPresent()) { // .isPresent()-  method in optional class  
				return new ResponseEntity<>(journalEntry.get() ,HttpStatus.OK); //200  // Returns that entry using new instance of responseEntity when there is that particular entry for that id and also sends Http status as 200OK since that id exists along with the response.
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND); //404  // return new instance of responseEntity when there is no reuested id to send Http status as 404 NOTFOUND along with response since that id doenst exist )
		
//		SAME if else replaced by single statement USING JAVA8 FEATURE LAMBDA EXPRESSION
//		return journalEntry.map(entry -> new ResponseEntity<>(entry, HttpStatus.OK))
//		        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	
//	@GetMapping("/id{myId}") also possible , here since its same url for gets

	
	//// In relational databases like MySQL, deleting an entry by its ID maintains consistency automatically.
	// But in MongoDB, things work differently — deletion needs extra manual handling.
	//
	// In MongoDB, when you delete a journal entry by its ID, the entry gets removed from the Journal collection,
	// BUT a reference to that entry still remains in the associated user's journalEntries list. //cascade deleting didnt happen here 
	// This reference becomes null (or orphaned) since the actual entry is deleted — leading to inconsistency.
	//
	// This inconsistency remains until a new entry is added to that user (which refreshes the list) .
	// So, in MongoDB, we must write custom code to remove the reference from the user's list immediately
	// when the journal entry is deleted.
	//
	// If you're using MySQL with proper JPA mapping (like cascade settings or orphan removal), the below delete code is enough.
	// But for MongoDB, you should refer to the next method: deleteMapping("/id/{username}/{myId}")

	//For MySql
//	@DeleteMapping("/id/{myId}")
//	public ResponseEntity<?> deleteJournalModelsById(@PathVariable Long myId) {
//		journalEntryService.deleteById(myId);
//		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//	}
	
	//For MONGODB:
	@DeleteMapping("/id/{myId}")
	public ResponseEntity<?> deleteJournalModelsById(@PathVariable Long myId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		boolean removed = journalEntryService.deleteById(myId, username);
		if(removed) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	//updating when we edit it using id
	@PutMapping("/id/{id}")
	public ResponseEntity<?> updateJournalModelById(@PathVariable Long id, @RequestBody JournalModels newEntry){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User user = userService.findByUsername(username);
		List<JournalModels> collect = user.getJournalEntries().stream().filter(x -> x.getId()!=0 && x.getId()==id).collect(Collectors.toList());
		if(!collect.isEmpty()) {
			Optional<JournalModels> journalEntry = journalEntryService.findById(id);
			if(journalEntry.isPresent()) {
				JournalModels old = journalEntry.get();
				old.setTitle(newEntry.getTitle()!=null && !newEntry.getTitle().equals("") ? newEntry.getTitle():old.getTitle());
				old.setContent(newEntry.getContent()!= null && !newEntry.getContent().equals("") ? newEntry.getContent():old.getContent());
				journalEntryService.SaveEntry(old);
				return new ResponseEntity<>(old, HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
