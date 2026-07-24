package pharmacy_inventory_management.pharmacy_inventory_management.service;



import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.ApiResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.ReportResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.CategoryStat;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.ExpiryStat;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.Medicine;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.StockStat;
import pharmacy_inventory_management.pharmacy_inventory_management.repository.MedicineRepository;
import pharmacy_inventory_management.pharmacy_inventory_management.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final MedicineRepository medicineRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<ReportResponse> getReports() {

        long totalMedicines = medicineRepository.count();

        long totalPharmacists = userRepository.countPharmacists();

        long lowStock = medicineRepository.countByQuantityLessThanEqual(10);

        long expired = medicineRepository.countByExpiryDateBefore(LocalDate.now());

        long outOfStock = medicineRepository.countByQuantity(0);

        long inStock = totalMedicines - outOfStock - lowStock;

        long expiringSoon =
                medicineRepository.countByExpiryDateBetween(
                        LocalDate.now(),
                        LocalDate.now().plusDays(30)
                );

        long safe = totalMedicines - expired - expiringSoon;

        List<Medicine> medicines = medicineRepository.findAll();

        Map<String, Long> categoryMap =
                medicines.stream()
                        .collect(Collectors.groupingBy(
                                Medicine::getCategory,
                                Collectors.counting()
                        ));

        List<CategoryStat> categoryStats =
                categoryMap.entrySet()
                        .stream()
                        .map(entry -> CategoryStat.builder()
                                .category(entry.getKey())
                                .count(entry.getValue())
                                .build())
                        .toList();

        StockStat stockStat = StockStat.builder()
                .inStock(inStock)
                .lowStock(lowStock)
                .outOfStock(outOfStock)
                .build();

        ExpiryStat expiryStat = ExpiryStat.builder()
                .expired(expired)
                .expiringSoon(expiringSoon)
                .safe(safe)
                .build();

        ReportResponse response = ReportResponse.builder()
                .totalMedicines(totalMedicines)
                .totalPharmacists(totalPharmacists)
                .lowStock(lowStock)
                .expired(expired)
                .categoryStats(categoryStats)
                .stockStat(stockStat)
                .expiryStat(expiryStat)
                .build();

        return ApiResponse.<ReportResponse>builder()
                .success(true)
                .message("Reports loaded successfully")
                .data(response)
                .build();
    }
}