package com.masai.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.masai.constant.JwtConstant;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenGeneratorFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		SecurityContext context	=SecurityContextHolder.getContext();
		
		Authentication authentication=context.getAuthentication();
		
		if(authentication!=null) {
			
			Date iatDate=new Date();
			
			Date expDate=new Date();
			
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(expDate);
			calendar.add(calendar.HOUR, 4);
			
			SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_KEY.getBytes(StandardCharsets.UTF_8));
			
			String jwt = Jwts.builder().setIssuer("MasaiSchool").setSubject("JWT Token")
						.claim("username", authentication.getName())
					  .claim("authorities", authentication.getAuthorities())
					  .setIssuedAt(iatDate)
					.setExpiration(calendar.getTime())
					.signWith(key).compact();
			
			System.out.println(jwt);
			
			response.setHeader(JwtConstant.JWT_HEADER, jwt);
		
			filterChain.doFilter(request, response);
		
		}
	}
	
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return  !request.getServletPath().equals("/masai/welcomeP");
	}

}
