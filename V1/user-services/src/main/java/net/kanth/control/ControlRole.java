package net.kanth.control;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kanth.service.ServiceEntityRole;


@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(name="roles",value="/api/roles")
public class ControlRole {
	
	private ServiceEntityRole serviceEntityRole;
	
	@GetMapping
	public ResponseEntity<?> fetchAllRoles(){
		return new ResponseEntity<>(serviceEntityRole.findAllRoles(),HttpStatus.OK);
	}

    @GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id){
		return new ResponseEntity<>(serviceEntityRole.findById(id), HttpStatus.OK);
	}
    
    @GetMapping("/search")
    public ResponseEntity<?> findUsersByRoleName(@RequestParam String roleName){
    	return new ResponseEntity<>(serviceEntityRole.findUsersByRoleName(roleName), HttpStatus.OK);
    }
    
    @GetMapping("/assignrole")
    public ResponseEntity<?> assignRoleToUsers(@RequestParam String roleName,@RequestParam String userId){
    	serviceEntityRole.assignUserRole(userId,roleName);
    	return new ResponseEntity<>("Role Assign Successfully!", HttpStatus.OK);
    }
    
    @GetMapping("/removerole")
    public ResponseEntity<?> removeRoleFromUsers(@RequestParam String roleName,@RequestParam String userId){
    	serviceEntityRole.removeUserRoleV2(userId,roleName);
    	return new ResponseEntity<>("Role Deleted Succssfully!", HttpStatus.OK);
    }
    
  
}
