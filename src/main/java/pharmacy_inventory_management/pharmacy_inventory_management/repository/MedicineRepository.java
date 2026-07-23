package pharmacy_inventory_management.pharmacy_inventory_management.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.Medicine;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {

    // Find by Medicine Name
    Optional<Medicine> findByMedicineName(String medicineName);

    // Search by Medicine Name (contains)
    Page<Medicine> findByMedicineNameContainingIgnoreCase(
            String keyword,
            Pageable pageable
    );


    // Search by Category
    Page<Medicine> findByCategoryIgnoreCase(
            String category,
            Pageable pageable
    );
    long countByQuantityLessThanEqual(int quantity);

    long countByExpiryDateBefore(LocalDate date);

    @Query("""
            SELECT COALESCE(SUM(m.price * m.quantity),0)
            FROM Medicine m
            """)
    Double getInventoryValue();

    // Search by Manufacturer
    Page<Medicine> findByManufacturerContainingIgnoreCase(
            String manufacturer,
            Pageable pageable
    );

    // Low Stock Medicines
    List<Medicine> findByQuantityLessThanEqual(Integer quantity);

    // Expired Medicines
    List<Medicine> findByExpiryDateBefore(LocalDate date);

    // Medicines Expiring Before a Date
    List<Medicine> findByExpiryDateLessThanEqual(LocalDate date);

    // Active Medicines
    Page<Medicine> findByActiveTrue(Pageable pageable);

    // Active Medicine by ID
    Optional<Medicine> findByIdAndActiveTrue(Long id);

    // Check Duplicate Batch Number
    boolean existsByBatchNumber(String batchNumber);

    // Active Medicines by Category
    Page<Medicine> findByCategoryIgnoreCaseAndActiveTrue(
            String category,
            Pageable pageable
    );

    @Query("""
    SELECT m
    FROM Medicine m
    WHERE LOWER(m.medicineName) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(m.manufacturer) LIKE LOWER(CONCAT('%', :keyword, '%'))
       OR LOWER(m.category) LIKE LOWER(CONCAT('%', :keyword, '%'))
""")
    Page<Medicine> search(@Param("keyword") String keyword, Pageable pageable);

}