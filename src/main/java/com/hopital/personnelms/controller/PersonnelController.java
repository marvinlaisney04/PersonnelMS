package com.hopital.personnelms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hopital.personnelms.model.Personnel;
import com.hopital.personnelms.repository.PersonnelRepository;

@Controller
@RestController
@RequestMapping
public class PersonnelController {
	
	@Autowired
	private PersonnelRepository personnelRepository;
	
	@RequestMapping(value="/savePersonnel", method=RequestMethod.GET)
	public Personnel savePersonnel(Personnel e){
		return personnelRepository.save(e);
	}
	
	
	@GetMapping("/personnel")
		public List<Personnel> getAllPersonnel(){
			return this.personnelRepository.findAll();
		}
	
	/*@RequestMapping("/login")
	public String login(){
		return "login";
	}*/
	

}
