package net.kanth.control;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import net.kanth.authservice.AuthService;
import net.kanth.payload.PayloadLogin;
import net.kanth.payload.PayloadUser;
import net.kanth.service.ServiceEntityUser;
import net.kanth.service.demo.InterfacePayment;

@Slf4j
@RestController
@RequestMapping(value = "/api/users", name = "userapi")
public class ControlUsers {

	private ServiceEntityUser serviceEntityUser;
	private AuthService authService;
	private InterfacePayment payment;
	
	
	
	public ControlUsers(ServiceEntityUser serviceEntityUser,  @Qualifier("cardpayment")  InterfacePayment payment,AuthService authService) {
		super();
		this.serviceEntityUser = serviceEntityUser;
		this.payment = payment;
		this.authService=authService;
	}

	@PostMapping
	public ResponseEntity<?> savedTheUsers(@Valid @RequestBody PayloadUser payload) {
		return new ResponseEntity<>(serviceEntityUser.saveUser(payload), HttpStatus.CREATED);
	}

	@GetMapping("/updateuserrole/{id}")
	public ResponseEntity<?> updateUserRole(@PathVariable UUID id) {
		serviceEntityUser.testToSaveUserRoles(String.valueOf(id));
		return new ResponseEntity<>("Ok", HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable UUID id) {
		return new ResponseEntity<>(serviceEntityUser.deleteById(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<?> findAll() {
		return new ResponseEntity<>(serviceEntityUser.findAll(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable UUID id) {
		return new ResponseEntity<>(serviceEntityUser.findById(id), HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<?> findByUsername(@RequestParam String username) {
		return new ResponseEntity<>(serviceEntityUser.findByUsername(username), HttpStatus.OK);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> updateUser(@PathVariable UUID id,@RequestBody PayloadUser payload){
		return new ResponseEntity<>(serviceEntityUser.updateUserByIdV2(id,payload), HttpStatus.OK);
	}	
	
	@GetMapping("/payment")
	public ResponseEntity<?> goingToreturnPayment(){
		return new ResponseEntity<>(payment.doPayment(), HttpStatus.OK);
	}

	
	@GetMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody PayloadLogin login){
		return new ResponseEntity<>(authService.verifyUser(login), HttpStatus.OK);
	}
}
