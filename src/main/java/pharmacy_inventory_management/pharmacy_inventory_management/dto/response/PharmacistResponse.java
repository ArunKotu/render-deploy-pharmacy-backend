package pharmacy_inventory_management.pharmacy_inventory_management.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PharmacistResponse {

    private Long id;

    private String fullName;

    private String email;

    private Boolean enabled;
}