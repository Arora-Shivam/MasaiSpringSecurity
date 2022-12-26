package com.masai.configure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.masai.beans.Employee;
import com.masai.dao.EmployeeDao;

@Service
public class MyUserDetailService  implements UserDetailsService{

	
	@Autowired
	private EmployeeDao employeeDao;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		Employee e=employeeDao.findByUserName(username);
		
		if(e!=null) {
			
			return new MyUserDetails(e);
		}
		else {
			throw new UsernameNotFoundException("No User Found");
		}
		
	}

}
