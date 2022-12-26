package com.masai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.masai.beans.Employee;
import com.masai.dao.EmployeeDao;

@RestController
@RequestMapping("/masai/employee")
public class EmployeeeController {

	@Autowired
	private EmployeeDao employeeDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@PostMapping("/register")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		
		employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		
		Employee emp= employeeDao.save(employee);
		
		return new ResponseEntity<Employee>(emp, HttpStatus.OK);
	}
	
	
	//Only those employee can access this page who have the authority of admin
	@GetMapping("/admin")
	public ResponseEntity<String> admin(){
		
		return new ResponseEntity<String>("Welcome to Masai App for Admin",HttpStatus.ACCEPTED);
	}
}
