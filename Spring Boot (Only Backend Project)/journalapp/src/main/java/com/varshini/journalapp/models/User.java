package com.varshini.journalapp.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique = true)  //So that each username must be unique i.e cant repeat.
	//Use any one of this annotation or using all is not a problem too
	@NonNull //Its from lombok
	@NotBlank(message = "Username cannot be blank") // For string only, not null/ empty/ whitespace only
	@NotEmpty //for string, collection, map, array - not null or empty.
	private String username;
	
	@NonNull
	@NotEmpty
	private String password;
	
	//To link the journal entries(JournalModels) to a particular user
	//So now we create reference of JournalModels in users entity.
	@OneToMany(mappedBy = "user",cascade=CascadeType.ALL, fetch = FetchType.LAZY) //inverse side //most commonly used in sub class which we are trying to main or owning class(models) // others are OneToOne , @ManyToOne , @ManyToMany (JPA)
	@JsonBackReference
	private List<JournalModels> journalEntries = new ArrayList<>();
	
	//mappedBy = "user" -> user is the object/instance name we gave for User class in JournalModels class -> private User user;
//	create users instance in Models also and do inversed mapping and name the column that we would indicate and join the column userid with the entry
	
	@Column(nullable = false)
	private String roles; // This will store comma-separated roles like "USER" or "USER,ADMIN"

}
