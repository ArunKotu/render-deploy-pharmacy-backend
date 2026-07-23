package pharmacy_inventory_management.pharmacy_inventory_management.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineResponse {

    private Long id;

    private String medicineName;

    private String manufacturer;

    private String category;

    private String batchNumber;

    private BigDecimal price;

    private Integer quantity;

    private LocalDate expiryDate;

    private String description;

    private Boolean active;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
