package net.kanth.payload;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PayloadLogin {
	
	private String username;
	private String password;

}
