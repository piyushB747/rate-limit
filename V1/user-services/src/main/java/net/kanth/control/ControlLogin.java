package net.kanth.control;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.kanth.authservice.AuthService;
import net.kanth.payload.PayloadLogin;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping(value = "/login", name = "userapi")
public class ControlLogin {
	
	private AuthService authService;

	@GetMapping("/v1")
	public ResponseEntity<?> loginUser(@RequestBody PayloadLogin login){
		return new ResponseEntity<>(authService.verifyUser(login), HttpStatus.OK);
	}
}
