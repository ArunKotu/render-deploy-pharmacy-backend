package pharmacy_inventory_management.pharmacy_inventory_management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.request.MedicineRequest;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.ApiResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.MedicineResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.service.MedicineService;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/medicines")
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    /**
     * Add Medicine
     * ADMIN Only
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<MedicineResponse> addMedicine(
            @Valid @RequestBody MedicineRequest request) {

        return medicineService.addMedicine(request);
    }

    /**
     * Get All Medicines
     * ADMIN & PHARMACIST
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ApiResponse<Page<MedicineResponse>> getAllMedicines(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "medicineName") String sortBy,

            @RequestParam(defaultValue = "asc") String direction) {

        return medicineService.getAllMedicines(
                page,
                size,
                sortBy,
                direction
        );
    }

    /**
     * Get Medicine By ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ApiResponse<MedicineResponse> getMedicineById(
            @PathVariable Long id) {

        return medicineService.getMedicineById(id);
    }

    /**
     * Update Medicine
     * ADMIN Only
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<MedicineResponse> updateMedicine(

            @PathVariable Long id,

            @Valid @RequestBody MedicineRequest request) {

        return medicineService.updateMedicine(id, request);
    }

    /**
     * Delete Medicine
     * ADMIN Only
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deleteMedicine(
            @PathVariable Long id) {

        return medicineService.deleteMedicine(id);
    }

    /**
     * Search Medicine
     */
    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ApiResponse<Page<MedicineResponse>> searchMedicine(

            @RequestParam String keyword,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size) {

        return medicineService.searchMedicine(
                keyword,
                page,
                size
        );
    }

    /**
     * Search By Category
     */
    @GetMapping("/category/{category}")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ApiResponse<Page<MedicineResponse>> getByCategory(

            @PathVariable String category,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size) {

        return medicineService.getByCategory(
                category,
                page,
                size
        );
    }

    /**
     * Low Stock Medicines
     */
    @GetMapping("/low-stock")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ApiResponse<List<MedicineResponse>> lowStockMedicines() {

        return medicineService.getLowStockMedicines();
    }

    /**
     * Expired Medicines
     */
    @GetMapping("/expired")
    @PreAuthorize("hasAnyRole('ADMIN','PHARMACIST')")
    public ApiResponse<List<MedicineResponse>> expiredMedicines() {

        return medicineService.getExpiredMedicines();
    }

}
