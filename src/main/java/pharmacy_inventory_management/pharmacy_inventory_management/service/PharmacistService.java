package pharmacy_inventory_management.pharmacy_inventory_management.service;

import org.springframework.data.domain.Page;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.request.PharmacistRequest;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.ApiResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.PharmacistResponse;

public interface PharmacistService {

    ApiResponse<PharmacistResponse> addPharmacist(PharmacistRequest request);

    ApiResponse<Page<PharmacistResponse>> getAllPharmacists(
            int page,
            int size,
            String sortBy,
            String direction
    );

    ApiResponse<PharmacistResponse> getPharmacist(Long id);

    ApiResponse<PharmacistResponse> updatePharmacist(
            Long id,
            PharmacistRequest request
    );

    ApiResponse<String> deletePharmacist(Long id);

    ApiResponse<Page<PharmacistResponse>> searchPharmacists(
            String keyword,
            int page,
            int size
    );
}