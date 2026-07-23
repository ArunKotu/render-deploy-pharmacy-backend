package pharmacy_inventory_management.pharmacy_inventory_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.DashboardResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.repository.MedicineRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final MedicineRepository medicineRepository;

    public DashboardResponse getDashboard() {

        return new DashboardResponse(
                medicineRepository.count(),
                medicineRepository.countByQuantityLessThanEqual(10),
                medicineRepository.countByExpiryDateBefore(LocalDate.now()),
                medicineRepository.getInventoryValue()
        );
    }

}
