package com.hopital.personnelms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hopital.personnelms.model.Personnel;
import com.hopital.personnelms.repository.PersonnelRepository;

@RestController
@RequestMapping("/api")
public class PersonnelController {

	@Autowired
	private PersonnelRepository personnelRepository;

	// GET READ

	@GetMapping("/personnel")
	public List<Personnel> getAllPersonnel() {
		return this.personnelRepository.findAll();
	}

}
