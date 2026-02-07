package net.kanth.authservice;

import java.util.Collection;
import java.util.stream.Collectors;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import net.kanth.entity.EntityUser;

public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 1L;
	private EntityUser user;

	public UserPrincipal(EntityUser user) {
		super();
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return user.getRoles().stream().map(p -> new SimpleGrantedAuthority("ROLE_" + p.getRole().getRoleName()))
				.collect(Collectors.toList());
	}

	@Override
	public @Nullable String getPassword() {
		// TODO Auto-generated method stub
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}

}
