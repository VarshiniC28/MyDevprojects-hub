package com.website.WEBSITE.service;

import org.springframework.stereotype.Service;
import com.website.WEBSITE.dto.WebsiteDTO;
import com.website.WEBSITE.models.Website;
import com.website.WEBSITE.repository.WebsiteRepository;

import jakarta.validation.Valid;

import java.util.List;

@Service
public class WebsiteService {

    private final WebsiteRepository websiteRepository;

    public WebsiteService(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;
    }

    // Save a new website
    public void saveWebsite(WebsiteDTO websiteDTO) {
        Website website = new Website();
        website.setName(websiteDTO.getName());
        website.setAge(websiteDTO.getAge());
        website.setEmail(websiteDTO.getEmail());
        website.setPhone(websiteDTO.getPhone());
        website.setPassword(websiteDTO.getPassword());
        website.setDob(websiteDTO.getDob());
        website.setCity(websiteDTO.getCity());
        website.setGender(websiteDTO.getGender());

        // Ensure the `setSkills` method exists in the `Website` model
        website.setSkills(websiteDTO.getSkills());

        website.setAddress(websiteDTO.getAddress());
        websiteRepository.save(website);
    }

    // Update an existing website
    public void updateWebsite(WebsiteDTO websiteDTO, Long id) {
        Website website = websiteRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Website not found"));

        website.setName(websiteDTO.getName());
        website.setAge(websiteDTO.getAge());
        website.setEmail(websiteDTO.getEmail());
        website.setPhone(websiteDTO.getPhone());
        website.setPassword(websiteDTO.getPassword());
        website.setDob(websiteDTO.getDob());
        website.setCity(websiteDTO.getCity());
        website.setGender(websiteDTO.getGender());

        // Ensure the `setSkills` method exists in the `Website` model
        website.setSkills(websiteDTO.getSkills());

        website.setAddress(websiteDTO.getAddress());
        websiteRepository.save(website);
    }

    // Delete a website by ID
    public void deleteWebsite(Long id) {
        websiteRepository.deleteById(id);
    }

    // Get all websites
    public List<Website> getAllWebsites() {
        return websiteRepository.findAll();
    }

    // Get a website by ID
    public Website getWebsite(Long id) {
        return websiteRepository.findById(id).orElse(null);
    }

	public void saveUser(@Valid Website website) {
		// TODO Auto-generated method stub
		
	}
}
