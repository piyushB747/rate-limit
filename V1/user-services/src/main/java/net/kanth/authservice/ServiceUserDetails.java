package net.kanth.authservice;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Optional;
import lombok.AllArgsConstructor;
import net.kanth.repo.RepoEntityUser;
import net.kanth.entity.EntityUser;
@AllArgsConstructor
@Service
public class ServiceUserDetails implements UserDetailsService{

	private RepoEntityUser repoEntityUser;

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<EntityUser> optionalObj = repoEntityUser.findByUsername(username) ;
		if(optionalObj.isEmpty()) {
			throw new UsernameNotFoundException("User Not Found");
		}
		
		return new UserPrincipal(optionalObj.get());
	}

}
