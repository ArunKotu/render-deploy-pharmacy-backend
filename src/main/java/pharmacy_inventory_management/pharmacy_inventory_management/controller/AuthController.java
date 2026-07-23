package pharmacy_inventory_management.pharmacy_inventory_management.controller;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.request.ChangePasswordRequest;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.request.LoginRequest;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.request.RegisterRequest;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.ApiResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.AuthResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.service.AuthenticationService;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ApiResponse<String> register(
            @Valid @RequestBody RegisterRequest request) {

        return authenticationService.register(request);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(
            @Valid @RequestBody LoginRequest request) {

        return authenticationService.login(request);
    }

    @PutMapping("/change-password")
    public ApiResponse<String> changePassword(
            @Valid @RequestBody ChangePasswordRequest request) {

        return authenticationService.changePassword(request);
    }

}
