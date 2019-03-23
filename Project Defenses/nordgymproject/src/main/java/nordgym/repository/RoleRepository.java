package nordgym.repository;

import nordgym.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByAuthority(String roleName);
}
