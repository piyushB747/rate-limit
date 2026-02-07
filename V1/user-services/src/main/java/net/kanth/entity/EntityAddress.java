package net.kanth.entity;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kanth.enums.EnumCountry;

@Getter
@ToString
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address_tbl")
public class EntityAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id; 

	@NotBlank(message = "Phone number is mandatory")
	@Pattern(regexp = "^[6-9][0-9]{9}$", message = "Invalid phone number")
	private String phoneNo;

	@NotBlank(message = "Email is mandatory")
	@Email(message = "Invalid email format")
	private String email;

	private String street;

	private String city;

	private String state;

	private EnumCountry country;


}
