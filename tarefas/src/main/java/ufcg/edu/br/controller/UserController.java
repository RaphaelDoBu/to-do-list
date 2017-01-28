package ufcg.edu.br.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ufcg.edu.br.repositories.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository userRepo;
	

}
