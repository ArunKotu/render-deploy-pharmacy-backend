package pharmacy_inventory_management.pharmacy_inventory_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.request.MedicineRequest;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.ApiResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.MedicineResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.Medicine;
import pharmacy_inventory_management.pharmacy_inventory_management.repository.MedicineRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;

    /**
     * Add Medicine
     */
    public ApiResponse<MedicineResponse> addMedicine(MedicineRequest request) {

        if (medicineRepository.existsByBatchNumber(request.getBatchNumber())) {
            throw new RuntimeException("Batch Number already exists.");
        }

        Medicine medicine = Medicine.builder()
                .medicineName(request.getMedicineName())
                .manufacturer(request.getManufacturer())
                .category(request.getCategory())
                .batchNumber(request.getBatchNumber())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .expiryDate(request.getExpiryDate())
                .description(request.getDescription())
                .active(true)
                .build();

        Medicine savedMedicine = medicineRepository.save(medicine);

        return ApiResponse.<MedicineResponse>builder()
                .success(true)
                .message("Medicine added successfully.")
                .data(convertToResponse(savedMedicine))
                .build();
    }

    /**
     * Entity -> DTO
     */
    private MedicineResponse convertToResponse(Medicine medicine) {

        return MedicineResponse.builder()
                .id(medicine.getId())
                .medicineName(medicine.getMedicineName())
                .manufacturer(medicine.getManufacturer())
                .category(medicine.getCategory())
                .batchNumber(medicine.getBatchNumber())
                .price(medicine.getPrice())
                .quantity(medicine.getQuantity())
                .expiryDate(medicine.getExpiryDate())
                .description(medicine.getDescription())
                .active(medicine.getActive())
                .createdAt(medicine.getCreatedAt())
                .updatedAt(medicine.getUpdatedAt())
                .build();
    }
    public Page<MedicineResponse> searchMedicines(
            String keyword,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size);

        return medicineRepository
                .search(keyword, pageable)
                .map(this::convertToResponse);

    }
    /**
     * Get All Medicines
     */
    public ApiResponse<Page<MedicineResponse>> getAllMedicines(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<MedicineResponse> medicines =
                medicineRepository.findByActiveTrue(pageable)
                        .map(this::convertToResponse);

        return ApiResponse.<Page<MedicineResponse>>builder()
                .success(true)
                .message("Medicines fetched successfully.")
                .data(medicines)
                .build();
    }

    /**
     * Get Medicine By ID
     */
    public ApiResponse<MedicineResponse> getMedicineById(Long id) {

        Medicine medicine = medicineRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new RuntimeException("Medicine not found."));

        return ApiResponse.<MedicineResponse>builder()
                .success(true)
                .message("Medicine fetched successfully.")
                .data(convertToResponse(medicine))
                .build();
    }

    /**
     * Update Medicine
     */
    public ApiResponse<MedicineResponse> updateMedicine(
            Long id,
            MedicineRequest request) {

        Medicine medicine = medicineRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new RuntimeException("Medicine not found."));

        if (!medicine.getBatchNumber().equals(request.getBatchNumber())
                && medicineRepository.existsByBatchNumber(request.getBatchNumber())) {

            throw new RuntimeException("Batch Number already exists.");
        }

        medicine.setMedicineName(request.getMedicineName());
        medicine.setManufacturer(request.getManufacturer());
        medicine.setCategory(request.getCategory());
        medicine.setBatchNumber(request.getBatchNumber());
        medicine.setPrice(request.getPrice());
        medicine.setQuantity(request.getQuantity());
        medicine.setExpiryDate(request.getExpiryDate());
        medicine.setDescription(request.getDescription());

        Medicine updatedMedicine = medicineRepository.save(medicine);

        return ApiResponse.<MedicineResponse>builder()
                .success(true)
                .message("Medicine updated successfully.")
                .data(convertToResponse(updatedMedicine))
                .build();
    }

    /**
     * Soft Delete Medicine
     */
    public ApiResponse<String> deleteMedicine(Long id) {

        Medicine medicine = medicineRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new RuntimeException("Medicine not found."));

        medicine.setActive(false);

        medicineRepository.save(medicine);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Medicine deleted successfully.")
                .data(null)
                .build();
    }

    /**
     * Search Medicine by Name
     */
    public ApiResponse<Page<MedicineResponse>> searchMedicine(
            String keyword,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<MedicineResponse> medicines =
                medicineRepository
                        .findByMedicineNameContainingIgnoreCase(keyword, pageable)
                        .map(this::convertToResponse);

        return ApiResponse.<Page<MedicineResponse>>builder()
                .success(true)
                .message("Medicines fetched successfully.")
                .data(medicines)
                .build();
    }

    /**
     * Search Medicine by Category
     */
    public ApiResponse<Page<MedicineResponse>> getByCategory(
            String category,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<MedicineResponse> medicines =
                medicineRepository
                        .findByCategoryIgnoreCaseAndActiveTrue(category, pageable)
                        .map(this::convertToResponse);

        return ApiResponse.<Page<MedicineResponse>>builder()
                .success(true)
                .message("Medicines fetched successfully.")
                .data(medicines)
                .build();
    }

    /**
     * Low Stock Medicines
     */
    public ApiResponse<List<MedicineResponse>> getLowStockMedicines() {

        List<MedicineResponse> medicines =
                medicineRepository
                        .findByQuantityLessThanEqual(10)
                        .stream()
                        .map(this::convertToResponse)
                        .toList();

        return ApiResponse.<List<MedicineResponse>>builder()
                .success(true)
                .message("Low stock medicines fetched successfully.")
                .data(medicines)
                .build();
    }

    /**
     * Expired Medicines
     */
    public ApiResponse<List<MedicineResponse>> getExpiredMedicines() {

        List<MedicineResponse> medicines =
                medicineRepository
                        .findByExpiryDateBefore(LocalDate.now())
                        .stream()
                        .map(this::convertToResponse)
                        .toList();

        return ApiResponse.<List<MedicineResponse>>builder()
                .success(true)
                .message("Expired medicines fetched successfully.")
                .data(medicines)
                .build();
    }
}