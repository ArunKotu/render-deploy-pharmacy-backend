package pharmacy_inventory_management.pharmacy_inventory_management.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

    private String accessToken;

    private String tokenType;

    private Long expiresIn;

    private String role;

    private String name;

}
