package com.varshini.journalapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.varshini.journalapp.models.JournalModels;

public interface JournalEntryRepository extends JpaRepository<JournalModels, Long>{

}
