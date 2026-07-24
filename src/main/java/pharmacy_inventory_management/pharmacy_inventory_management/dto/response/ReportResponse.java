package pharmacy_inventory_management.pharmacy_inventory_management.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.CategoryStat;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.ExpiryStat;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.StockStat;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {

    private long totalMedicines;

    private long totalPharmacists;

    private long lowStock;

    private long expired;

    private List<CategoryStat> categoryStats;

    private StockStat stockStat;

    private ExpiryStat expiryStat;
}