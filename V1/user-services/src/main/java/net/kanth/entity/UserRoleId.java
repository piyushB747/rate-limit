package net.kanth.entity;

import java.io.Serializable;
import java.util.UUID;
 
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserRoleId implements Serializable {
	private static final long serialVersionUID = 1L;
	private UUID userId;
	private UUID roleId;
}
