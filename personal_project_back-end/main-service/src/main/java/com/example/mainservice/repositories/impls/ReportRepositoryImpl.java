package com.example.mainservice.repositories.impls;

import com.example.common.service.components.BaseNativeQuery;
import com.example.common.service.models.reports.MaintenanceStatusReportModel;
import com.example.common.service.models.reports.RoomUtilityUsageReportModel;
import com.example.mainservice.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ReportRepositoryImpl implements ReportRepository {
    @Autowired
    private BaseNativeQuery nativeQuery;

    @Override
    public List<RoomUtilityUsageReportModel> getRoomUtilityUsageReport(String startDate,
                                                                       String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        String sql = """
                SELECT
                    r.ID AS roomId,
                    r.NUMBER AS roomName,
                    SUM(ui.WATER_USAGE) AS totalWaterUsage,
                    SUM(ui.ELECTRIC_USAGE) AS totalElectricUsage,
                    ui.MONTH_MEASURE as monthInventory,
                    ui.YEAR_MEASURE as yearInventory
                  FROM
                    utility_index ui
                    JOIN room r ON ui.ROOM_ID = r.ID
                  WHERE ui.CREATED_AT BETWEEN :startDate AND :endDate
                  GROUP BY r.ID, r.NUMBER, ui.MONTH_MEASURE, ui.YEAR_MEASURE
                  ORDER BY
                    r.ID""";
        List<RoomUtilityUsageReportModel> result = nativeQuery.findList(sql, RoomUtilityUsageReportModel.class, params);
        return result;
    }

    @Override
    public List<MaintenanceStatusReportModel> findMaintenanceStatusReport(String startDate, String endDate) {
        Map<String, Object> params = new HashMap<>();
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        String sql = """
                SELECT
                  r.ID AS roomId,
                  r.NAME AS roomName,
                  mr.ID AS maintenanceId,
                  mr.STATUS AS maintenanceStatus,
                  mr.REQUEST_DATE requestDate,
                  mr.REQUEST_DONE_DATE requestDoneDate,
                  mr.TOTAL_FEE totalFee
                FROM
                  maintenance_request mr
                  JOIN contract c ON mr.CONTRACT_ID = c.ID
                  JOIN room r ON c.ROOM_ID = r.ID
                WHERE
                  mr.CREATED_AT BETWEEN :startDate AND :endDate
                ORDER BY
                  r.ID""";
        List<MaintenanceStatusReportModel> result = nativeQuery.findList(sql, MaintenanceStatusReportModel.class, params);
        return result;
    }

}
