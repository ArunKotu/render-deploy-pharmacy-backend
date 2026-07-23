package pharmacy_inventory_management.pharmacy_inventory_management.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardResponse {

    private long totalMedicines;
    private long lowStockMedicines;
    private long expiredMedicines;
    private double totalInventoryValue;

}
