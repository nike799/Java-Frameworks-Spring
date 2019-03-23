package residentevil.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import residentevil.domain.entities.Role;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByAuthority(String authority);
}
