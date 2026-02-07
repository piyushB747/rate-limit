package net.kanth.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(exclude = {"user", "role"}) // EXCLUDE BOTH
@Entity
@Table(name = "user_roles")
public class EntityUserRole {

	@EmbeddedId
	private UserRoleId userRole;
	
	@JsonBackReference("user-userRole")
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("userId")
	private EntityUser user;
	
    @JsonBackReference("role-userRole")
	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("roleId")
	private EntityRole role;


}
