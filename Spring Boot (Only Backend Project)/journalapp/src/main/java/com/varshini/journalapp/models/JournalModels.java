package com.varshini.journalapp.models;

import java.time.LocalDateTime;
//import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
//import lombok.NoArgsConstructor;
import lombok.Setter;
//import lombok.ToString;
//import lombok.Data;

@Entity
@Table(name = "JournalEntries")

//Instead of creating our own getters, setters, constructors we can use Lomboks annotations as below which automatically generates the code during compilation of that class.(So removed all getters and setters.
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@EqualsAndHashCode
//@Builder

//Instead of writing all these there is still more simpler way
//Uisng @Data annotation which is a interface which contains all these methods in built - Getter, Setter, @Value, EqualandHashCode, @RequiredArgsConstructor ,@ToString
@Data
@NoArgsConstructor //must
public class JournalModels {

	// This is pojo class - plain old java object
	@Id // to map it as a primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	// @GeneratedValue(strategy = GenerationType.IDENTITY) tells Hibernate to let MySQL generate unique value using AUTO_INCREMENT.
	
	/* This is used in JPA (Java Persistence API) to tell the persistence provider 
	 * (like Hibernate) how to auto-generate the value of the primary key when a new
	* entity is inserted into the database.
	* 
	* =>@GeneratedValue:This annotation is used to specify how the primary key should be generated.
	* =>strategy = GenerationType.IDENTITY:This is one of the strategies for generating primary key values.
	* */
	
	// if we dont use this sql wants us to create unique id manually so this is mandatory , if created the table without this sentence and added entry and we get error for next entity we try to add then go to sql in DB and add this query :ALTER TABLE journal_entries MODIFY COLUMN id BIGINT NOT NULL AUTO_INCREMENT;

	@NonNull
	private String title;
	private String content;
	private LocalDateTime date;
	//Changed to LocalDateTime since we dont want to write date ourselves.

	@ManyToOne //Owning side 
	@JoinColumn(name = "user_id") // Foreign key in journal_entries / if not usd hibernate will give name itselves which we may not like
	@JsonBackReference
	private User user;

}
