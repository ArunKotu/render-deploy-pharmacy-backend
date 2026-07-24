package pharmacy_inventory_management.pharmacy_inventory_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.request.PharmacistRequest;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.ApiResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.PharmacistResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.Role;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.User;
import pharmacy_inventory_management.pharmacy_inventory_management.enums.RoleType;
import pharmacy_inventory_management.pharmacy_inventory_management.repository.RoleRepository;
import pharmacy_inventory_management.pharmacy_inventory_management.repository.UserRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PharmacistServiceImpl implements PharmacistService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<PharmacistResponse> addPharmacist(PharmacistRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            return ApiResponse.<PharmacistResponse>builder()
                    .success(false)
                    .message("Email already exists")
                    .data(null)
                    .build();
        }

        Role pharmacistRole = roleRepository.findByRoleName(RoleType.PHARMACIST)
                .orElseThrow(() -> new RuntimeException("PHARMACIST role not found"));

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .roles(Set.of(pharmacistRole))
                .build();

        userRepository.save(user);

        return ApiResponse.<PharmacistResponse>builder()
                .success(true)
                .message("Pharmacist created successfully")
                .data(mapToResponse(user))
                .build();
    }

    @Override
    public ApiResponse<Page<PharmacistResponse>> getAllPharmacists(
            int page,
            int size,
            String sortBy,
            String direction) {

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<PharmacistResponse> pharmacists =
                userRepository.findAllPharmacists(pageable)
                        .map(this::mapToResponse);

        return ApiResponse.<Page<PharmacistResponse>>builder()
                .success(true)
                .message("Pharmacists fetched successfully")
                .data(pharmacists)
                .build();
    }

    @Override
    public ApiResponse<PharmacistResponse> getPharmacist(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pharmacist not found"));

        return ApiResponse.<PharmacistResponse>builder()
                .success(true)
                .message("Pharmacist fetched successfully")
                .data(mapToResponse(user))
                .build();
    }

    @Override
    public ApiResponse<PharmacistResponse> updatePharmacist(
            Long id,
            PharmacistRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pharmacist not found"));

        if (!user.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            return ApiResponse.<PharmacistResponse>builder()
                    .success(false)
                    .message("Email already exists")
                    .data(null)
                    .build();
        }

        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());

        if (request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            user.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        userRepository.save(user);

        return ApiResponse.<PharmacistResponse>builder()
                .success(true)
                .message("Pharmacist updated successfully")
                .data(mapToResponse(user))
                .build();
    }

    @Override
    public ApiResponse<String> deletePharmacist(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pharmacist not found"));

        userRepository.delete(user);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Pharmacist deleted successfully")
                .data("Deleted")
                .build();
    }

    @Override
    public ApiResponse<Page<PharmacistResponse>> searchPharmacists(
            String keyword,
            int page,
            int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<PharmacistResponse> pharmacists =
                userRepository.searchPharmacists(keyword, pageable)
                        .map(this::mapToResponse);

        return ApiResponse.<Page<PharmacistResponse>>builder()
                .success(true)
                .message("Search completed")
                .data(pharmacists)
                .build();
    }

    private PharmacistResponse mapToResponse(User user) {

        return PharmacistResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .enabled(user.getEnabled())
                .build();
    }
}