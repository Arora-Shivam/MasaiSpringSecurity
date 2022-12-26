package com.masai.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import com.masai.constant.JwtConstant;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidatorFilter extends OncePerRequestFilter{

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String jwt = request.getHeader(JwtConstant.JWT_HEADER);
		
		if (null != jwt) {
			try {
				
				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.JWT_KEY.getBytes(StandardCharsets.UTF_8));
				
				
				Claims claims = Jwts.parserBuilder()
								.setSigningKey(key)
								.build()
								.parseClaimsJws(jwt)
								.getBody();
				
				
				String username = String.valueOf(claims.get("username"));
				
				String autorities= String.valueOf( claims.get("authorities"));
			
				String[] at=autorities.split("[{[=}]]");
			
				SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(at[2]);
				
				List<GrantedAuthority> authorities=new ArrayList<>();
				
				authorities.add(simpleGrantedAuthority);
				
				Authentication auth = new UsernamePasswordAuthenticationToken(username,null,authorities);
				
				SecurityContextHolder.getContext().setAuthentication(auth);
				
			}
			catch (Exception e) {
				throw new UsernameNotFoundException("Invalid Token received!");
			}
			
		}
		filterChain.doFilter(request, response);
	}
	
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return request.getServletPath().equals("/masai/welcomeP");
	}

}
