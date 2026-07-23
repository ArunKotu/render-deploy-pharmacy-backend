package pharmacy_inventory_management.pharmacy_inventory_management.service;



import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.request.ChangePasswordRequest;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.request.LoginRequest;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.request.RegisterRequest;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.ApiResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.AuthResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.Role;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.User;
import pharmacy_inventory_management.pharmacy_inventory_management.enums.RoleType;
import pharmacy_inventory_management.pharmacy_inventory_management.repository.RoleRepository;
import pharmacy_inventory_management.pharmacy_inventory_management.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Bean
    CommandLineRunner initData(UserRepository userRepository,
                               RoleRepository roleRepository,
                               PasswordEncoder passwordEncoder) {

        return args -> {

            if (!userRepository.existsByEmail("root@gmail.com")) {

                Role adminRole = roleRepository
                        .findByRoleName(RoleType.ADMIN)
                        .orElseThrow();

                User admin = User.builder()
                        .fullName("System Admin")
                        .email("root@gmail.com")
                        .password(passwordEncoder.encode("root@123"))
                        .enabled(true)
                        .roles(Set.of(adminRole))
                        .build();

                userRepository.save(admin);
            }
        };
    }

    /**
     * Register New User
     */
    public ApiResponse<String> register(RegisterRequest request) {

        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        // Default role
        Role pharmacistRole = roleRepository
                .findByRoleName(RoleType.PHARMACIST)
                .orElseThrow(() ->
                        new RuntimeException("Default role not found."));

        Set<Role> roles = new HashSet<>();
        roles.add(pharmacistRole);

        // Create User
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(request.getPassword())
                )
                .enabled(true)
                .createdAt(LocalDateTime.now())
                .roles(roles)
                .build();

        userRepository.save(user);

        return ApiResponse.<String>builder()
                .success(true)
                .message("User registered successfully.")
                .data(user.getEmail())
                .build();
    }

    public ApiResponse<AuthResponse> login(LoginRequest request) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        String role = user.getRoles()
                .stream()
                .findFirst()
                .map(r -> r.getRoleName().name())
                .orElse("PHARMACIST");

        AuthResponse response = AuthResponse.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .expiresIn(86400L)
                .role(role)
                .name(user.getFullName())
                .build();

        return ApiResponse.<AuthResponse>builder()
                .success(true)
                .message("Login successful")
                .data(response)
                .build();
    }

    public ApiResponse<String> changePassword(
            ChangePasswordRequest request
    ) {

        Authentication authentication =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new RuntimeException(
                    "Old password is incorrect."
            );
        }

        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        userRepository.save(user);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Password changed successfully.")
                .data(null)
                .build();
    }
}