package com.masai.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.masai.beans.Employee;
import com.masai.dao.EmployeeDao;

@Component
public class GetCurrentUser {

	
	@Autowired
	private EmployeeDao employeeDao;
	
	public Employee getLoggedInUserDetails() {
		
		
		SecurityContext sc =SecurityContextHolder.getContext();
		
		
		Authentication auth=sc.getAuthentication();
		
		return employeeDao.findByUserName(auth.getName());
	}
}
