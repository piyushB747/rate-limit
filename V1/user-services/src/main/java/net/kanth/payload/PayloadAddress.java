package net.kanth.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kanth.enums.EnumCountry;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PayloadAddress {
	
	@NotNull(message="Phone Required")
	private String phoneNo;

	private String street;

	private String city;

	private String state;

	@NotNull(message = "Country required")
	private EnumCountry country;

	@NotNull(message="Email required")
	private String email;
}
