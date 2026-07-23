package pharmacy_inventory_management.pharmacy_inventory_management.config;


import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pharmacy_inventory_management.pharmacy_inventory_management.entity.Role;
import pharmacy_inventory_management.pharmacy_inventory_management.enums.RoleType;
import pharmacy_inventory_management.pharmacy_inventory_management.repository.RoleRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {

        if (roleRepository.findByRoleName(RoleType.ADMIN).isEmpty()) {
            roleRepository.save(
                    Role.builder()
                            .roleName(RoleType.ADMIN)
                            .build()
            );
        }

        if (roleRepository.findByRoleName(RoleType.PHARMACIST).isEmpty()) {
            roleRepository.save(
                    Role.builder()
                            .roleName(RoleType.PHARMACIST)
                            .build()
            );
        }
    }
}