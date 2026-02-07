package net.kanth.entity;


import java.util.Set;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedNativeQueries;
import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kanth.payload.UserNameDTO;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.ColumnResult;

@ToString // EXCLUDE THIS
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_tbl")
@SqlResultSetMapping(
	    name = "UserNameDTOMapping",
	    classes = @ConstructorResult(
	        targetClass = UserNameDTO.class,
	        columns = {
	            @ColumnResult(name = "id", type = UUID.class),
	            @ColumnResult(name = "full_name", type = String.class)
	        }
	    )
	)

@NamedNativeQueries({
    @NamedNativeQuery(
        name = "EntityUser.findByRoleName",
        query = """
            SELECT  CONCAT(u.first_name, ' ', u.last_name) AS full_name, u.id
            FROM user_tbl u
            JOIN user_roles ur ON u.id = ur.user_id
            JOIN role_tbl r ON r.id = ur.role_id
            WHERE r.role_name = :roleName
        """,
        resultSetMapping = "UserNameDTOMapping"
    )
})
@NamedQueries(
		@NamedQuery(name= "EntityUser.findByUsername",
        query="Select u From EntityUser u where u.username =:username"
	)
		)
public class EntityUser {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable=false,unique=true)
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	@Column(nullable=false)
	private String password;
	
	@JsonManagedReference("user-userRole")
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
	private Set<EntityUserRole> roles; 

	@OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name="address_id",referencedColumnName = "id",nullable = false)
	private EntityAddress address;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(referencedColumnName = "id",name = "org_id")
	private EntityOrganization organization;

}
/*
@CreationTimestamp
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
private LocalDateTime created;

@UpdateTimestamp
@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
private LocalDateTime updation;
*/

/*
	@ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinTable(name="user_roles",
	joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id")
			)
	private Set<EntityRole> roles;  
*/