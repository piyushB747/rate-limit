package net.kanth.entity;



import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.HashSet;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@ToString(exclude = "userRoles") // EXCLUDE THIS
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="role_tbl")
public class EntityRole {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String roleName;
	
	@OneToMany(mappedBy = "role")
	@JsonManagedReference("role-userRole")
	private Set<EntityUserRole> userRoles = new HashSet<>();
}
