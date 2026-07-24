package pharmacy_inventory_management.pharmacy_inventory_management.service;


import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.ApiResponse;
import pharmacy_inventory_management.pharmacy_inventory_management.dto.response.ReportResponse;

public interface ReportService {

    ApiResponse<ReportResponse> getReports();

}
