package com.digitalbooks.author.security.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.digitalbooks.author.entities.db.TblAuthorCredential;
import com.digitalbooks.author.repositories.TblAuthorCredentialRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private TblAuthorCredentialRepository tblAuthorCredentialRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<TblAuthorCredential> tblAuthorCredentialOptional = tblAuthorCredentialRepository
				.findByUsername(username);
		if (tblAuthorCredentialOptional.isPresent()) {
			TblAuthorCredential tblAuthorCredential = tblAuthorCredentialOptional.get();
			return new User(tblAuthorCredential.getUsername(), tblAuthorCredential.getPassword(),
					new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("Author Not Found");
		}
	}

}
