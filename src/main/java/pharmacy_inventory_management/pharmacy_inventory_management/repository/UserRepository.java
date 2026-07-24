package pharmacy_inventory_management.pharmacy_inventory_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.User;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    @Query("""
SELECT u
FROM User u
JOIN u.roles r
WHERE r.roleName = pharmacy_inventory_management.pharmacy_inventory_management.enums.RoleType.PHARMACIST
""")
    Page<User> findAllPharmacists(Pageable pageable);
    @Query("""
SELECT u
FROM User u
JOIN u.roles r
WHERE r.roleName = pharmacy_inventory_management.pharmacy_inventory_management.enums.RoleType.PHARMACIST
AND (
LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%'))
OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))
)
""")
    Page<User> searchPharmacists(String keyword, Pageable pageable);
    @Query("""
    SELECT COUNT(u)
    FROM User u
    JOIN u.roles r
    WHERE r.roleName = pharmacy_inventory_management.pharmacy_inventory_management.enums.RoleType.PHARMACIST
    """)
    long countPharmacists();
}
