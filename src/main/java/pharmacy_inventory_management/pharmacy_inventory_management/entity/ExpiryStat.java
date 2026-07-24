package pharmacy_inventory_management.pharmacy_inventory_management.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExpiryStat {

    private long expired;

    private long expiringSoon;

    private long safe;
}