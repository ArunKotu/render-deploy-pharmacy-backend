package pharmacy_inventory_management.pharmacy_inventory_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
