package com.masai.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.masai.beans.Employee;

public interface EmployeeDao extends JpaRepository<Employee, Integer>{

	
	public Employee findByUserName(String userName);
}
