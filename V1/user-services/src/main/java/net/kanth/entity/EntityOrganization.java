package net.kanth.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.kanth.enums.EnumCountry;
import net.kanth.enums.EnumOrganizationType;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "organization_tbl")
public class EntityOrganization {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@Column(unique = true)
	private String organizationName;

	@Column(unique = true)
	private String gstno;

	private String ceo;

	private String salesPerson;

	@Enumerated(EnumType.STRING)
	private EnumOrganizationType type;

	private String phoneno;

	private String email;

	private String website;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
	private List<EntityUser> users;

	private String street;

	private String city;

	private String state;

	@Enumerated(EnumType.STRING)
	private EnumCountry countryCode;
	
	private String pincode;
}
