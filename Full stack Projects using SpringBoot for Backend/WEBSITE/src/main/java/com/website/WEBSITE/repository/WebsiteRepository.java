package com.website.WEBSITE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.website.WEBSITE.models.Website;

public interface WebsiteRepository extends JpaRepository<Website, Long> {
}
