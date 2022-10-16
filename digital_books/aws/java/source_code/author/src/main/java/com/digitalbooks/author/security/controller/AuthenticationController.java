package com.digitalbooks.author.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.author.entities.payload.AuthorPayload;
import com.digitalbooks.author.exceptions.DigitalBooksException;
import com.digitalbooks.author.interfaces.AuthorIf;
import com.digitalbooks.author.security.payload.CredentialPayload;
import com.digitalbooks.author.security.payload.JwtResponse;
import com.digitalbooks.author.security.service.JwtUserDetailsService;
import com.digitalbooks.author.security.util.JwtUtil;

@RestController
@CrossOrigin
@RequestMapping("api/v1/digitalbooks/author")
public class AuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;
	@Autowired
	private AuthorIf authorIf;

	@PostMapping("signup")
	public JwtResponse signup(@RequestBody CredentialPayload credentialPayload) throws DigitalBooksException {
		AuthorPayload authorPayload = authorIf.signup(credentialPayload);
		return getGeneratedTokenFromUsername(authorPayload.getName());
	}

	@PostMapping("login")
	public JwtResponse authenticate(@RequestBody CredentialPayload credentialPayload) throws Exception {
		authenticate(credentialPayload.getUsername(), credentialPayload.getPassword());
		return getGeneratedTokenFromUsername(credentialPayload.getUsername());

	}

	private JwtResponse getGeneratedTokenFromUsername(String username) {
		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
		return new JwtResponse(jwtUtil.generateToken(userDetails));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}

}
