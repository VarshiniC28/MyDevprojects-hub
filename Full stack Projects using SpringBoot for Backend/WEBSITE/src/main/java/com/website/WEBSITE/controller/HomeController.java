
package com.website.WEBSITE.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.website.WEBSITE.dto.WebsiteDTO;
import com.website.WEBSITE.models.Website;
import com.website.WEBSITE.service.WebsiteService;

import jakarta.validation.Valid;
import java.util.List;

@Controller
public class HomeController {

    private final WebsiteService websiteService;

    public HomeController(WebsiteService websiteService) {
        this.websiteService = websiteService;
    }

    @GetMapping({"/", ""})
    public String getAllWebsites(Model model) {
        List<Website> websites = websiteService.getAllWebsites();
        model.addAttribute("websites", websites);
        return "index";
    }

    @GetMapping("/register")
    public String registerWebsite(Model model) {
        WebsiteDTO websiteDTO = new WebsiteDTO();
        model.addAttribute("websiteDTO", websiteDTO);
        return "register_user";
    }

    @PostMapping("/register")
    public String saveWebsite(@ModelAttribute("websiteDTO") @Valid WebsiteDTO websiteDTO,
                               BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "register_user";
        }
        websiteService.saveWebsite(websiteDTO);
        return "redirect:/";
    }

    @GetMapping("/edit-website")
    public String editWebsite(@RequestParam Long id, Model model) {
        Website website = websiteService.getWebsite(id);
        if (website == null) {
            return "redirect:/";
        }

        WebsiteDTO websiteDTO = new WebsiteDTO();
        websiteDTO.setId(website.getId());
        websiteDTO.setName(website.getName());
        websiteDTO.setAge(website.getAge());
        websiteDTO.setEmail(website.getEmail());
        websiteDTO.setPhone(website.getPhone());
        websiteDTO.setPassword(website.getPassword());
        websiteDTO.setDob(website.getDob());
        websiteDTO.setCity(website.getCity());
        websiteDTO.setGender(website.getGender());
        websiteDTO.setSkills(website.getSkills());
        websiteDTO.setAddress(website.getAddress());

        model.addAttribute("websiteDTO", websiteDTO);
        return "edit_user";
    }

    @PostMapping("/edit-website")
    public String updateWebsite(@ModelAttribute("websiteDTO") WebsiteDTO websiteDTO) {
        if (websiteDTO.getId() == null) {
            return "redirect:/";
        }
        websiteService.updateWebsite(websiteDTO, websiteDTO.getId());
        return "redirect:/";
    }

    @GetMapping("/delete")
    public String deleteWebsite(@RequestParam Long id) {
        websiteService.deleteWebsite(id);
        return "redirect:/";
    }
}