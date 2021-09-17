package com.rasmoo.client.financescontroll.v1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rasmoo.client.financescontroll.core.impl.ResourceOwner;
import com.rasmoo.client.financescontroll.entity.User;
import com.rasmoo.client.financescontroll.repository.IUserRepository;

@Service
public class UserInfoService implements UserDetailsService,IUserInfoService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<User> usuario = this.userRepository.findByEmail(email);

		if (usuario.isPresent()) {
			UserDetails userDetails = new ResourceOwner(usuario.get());
			return userDetails;
		} else {
			throw new UsernameNotFoundException("Usuário não encontrado");
		}

	}
	
	@Override
	public User findAuth() throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Optional<User> usuario = userRepository.findByEmail(auth.getName());

		if (!usuario.isPresent()) {
			throw new Exception();
		}

		return usuario.get();
	}

}
