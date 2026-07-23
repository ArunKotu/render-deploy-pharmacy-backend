package pharmacy_inventory_management.pharmacy_inventory_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.Role;
import pharmacy_inventory_management.pharmacy_inventory_management.enums.RoleType;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByRoleName(RoleType roleName);
}
