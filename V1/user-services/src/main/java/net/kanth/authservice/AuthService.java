package net.kanth.authservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;
import net.kanth.entity.EntityUser;
import net.kanth.payload.PayloadLogin;
import net.kanth.repo.RepoEntityUser;

@Slf4j
@Service
public class AuthService {

	@Autowired
	private JwtServiceV2 jwtService;
	
	@Autowired
	private RepoEntityUser repoEntityUser;
	
	@Autowired
	private AuthenticationManager authManager;
	
	public String verifyUser(PayloadLogin user) {
		log.info("Going to Login User {}",user);
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if(auth.isAuthenticated()) {
			log.info("User Authenticate Successfully {} ",user.getUsername());
			
			EntityUser entityUser = repoEntityUser.findByUsername(user.getUsername())
	                .orElseThrow(() -> new RuntimeException("User not found"));
	        UUID userId = entityUser.getId();

			 List<String> roles = auth.getAuthorities()
		                .stream()
		                .map(a -> a.getAuthority())
		                .toList();	
			log.info("Users roles {}",roles);
			return jwtService.generateTokenV2(user.getUsername(),roles,userId);
		}
		throw new BadCredentialsException("Invalid username or password");	}

}
