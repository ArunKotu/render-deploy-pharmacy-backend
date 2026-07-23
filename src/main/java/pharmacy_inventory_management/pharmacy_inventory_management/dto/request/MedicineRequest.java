package pharmacy_inventory_management.pharmacy_inventory_management.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicineRequest {

    @NotBlank(message = "Medicine name is required")
    @Size(max = 100)
    private String medicineName;

    @NotBlank(message = "Manufacturer is required")
    @Size(max = 100)
    private String manufacturer;

    @NotBlank(message = "Category is required")
    private String category;

    @NotBlank(message = "Batch number is required")
    private String batchNumber;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @NotNull(message = "Expiry date is required")
    @Future(message = "Expiry date must be in the future")
    private LocalDate expiryDate;

    @Size(max = 1000)
    private String description;
}
