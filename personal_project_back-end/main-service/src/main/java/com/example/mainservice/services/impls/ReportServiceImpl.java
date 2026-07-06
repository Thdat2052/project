package com.example.mainservice.services.impls;

import com.example.common.service.models.reports.MaintenanceStatusReportModel;
import com.example.common.service.models.reports.RoomUtilityUsageReportModel;
import com.example.mainservice.repositories.ReportRepository;
import com.example.mainservice.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository repository;

    @Override
    public List<RoomUtilityUsageReportModel> getRoomUtilityUsageReport(String startDate, String endDate) {
        return repository.getRoomUtilityUsageReport(startDate , endDate);
    }

    @Override
    public List<MaintenanceStatusReportModel> findMaintenanceStatusReport(String startDate, String endDate) {
        return repository.findMaintenanceStatusReport(startDate , endDate);
    }
}
