package pharmacy_inventory_management.pharmacy_inventory_management.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {

    @NotBlank
    private String oldPassword;

    @Size(min = 8)
    private String newPassword;

}
