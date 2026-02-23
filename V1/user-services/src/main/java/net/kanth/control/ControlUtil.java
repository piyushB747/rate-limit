package net.kanth.control;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import net.kanth.service.ServiceEntityRole;

@AllArgsConstructor
@RestController
@RequestMapping("/apiv1demo")
public class ControlUtil {
	
	private ServiceEntityRole serviceEntityRole;

	  @GetMapping("/createsomeuser")
	    public ResponseEntity<?> createSomeUser(){
	    	serviceEntityRole.createSomeUser();
	    	return new ResponseEntity<>("Role Deleted Succssfully!", HttpStatus.OK);
	    }
}
