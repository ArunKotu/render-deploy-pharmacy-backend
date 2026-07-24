package pharmacy_inventory_management.pharmacy_inventory_management.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.request.PharmacistRequest;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.ApiResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.PharmacistResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.service.PharmacistService;

@RestController
@RequestMapping("/api/pharmacists")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PharmacistController {

    private final PharmacistService pharmacistService;

    /**
     * Add Pharmacist
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PharmacistResponse> addPharmacist(
            @Valid @RequestBody PharmacistRequest request) {

        return pharmacistService.addPharmacist(request);
    }

    /**
     * Get All Pharmacists
     */
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<PharmacistResponse>> getAllPharmacists(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fullName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return pharmacistService.getAllPharmacists(
                page,
                size,
                sortBy,
                direction
        );
    }

    /**
     * Get Pharmacist By Id
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PharmacistResponse> getPharmacist(
            @PathVariable Long id) {

        return pharmacistService.getPharmacist(id);
    }

    /**
     * Update Pharmacist
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<PharmacistResponse> updatePharmacist(
            @PathVariable Long id,
            @Valid @RequestBody PharmacistRequest request) {

        return pharmacistService.updatePharmacist(id, request);
    }

    /**
     * Delete Pharmacist
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<String> deletePharmacist(
            @PathVariable Long id) {

        return pharmacistService.deletePharmacist(id);
    }

    /**
     * Search Pharmacists
     */
    @GetMapping("/search")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<PharmacistResponse>> searchPharmacists(

            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return pharmacistService.searchPharmacists(
                keyword,
                page,
                size
        );
    }
}