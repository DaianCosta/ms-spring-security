package com.rasmoo.client.financescontroll.core.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rasmoo.client.financescontroll.entity.User;

public class ResourceOwner implements UserDetails{
	
	private User usuario;
	
	public ResourceOwner(User usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {


		String [] items = this.usuario.getRole().split("\\s*,\\s*");
		List<String> repositoryRoles = Arrays.asList(items);
		List<SimpleGrantedAuthority> roles = new ArrayList<>();

		for (String role:repositoryRoles) {
			roles.add(new SimpleGrantedAuthority(role));
		}

		return roles;
	}

	@Override
	public String getPassword() {
		return this.usuario.getCredencial().getSenha();
	}

	@Override
	public String getUsername() {
		return this.usuario.getCredencial().getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
