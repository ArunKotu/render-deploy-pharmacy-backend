package pharmacy_inventory_management.pharmacy_inventory_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.DashboardResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public  DashboardResponse getDashboard() {

        return dashboardService.getDashboard();

    }

}