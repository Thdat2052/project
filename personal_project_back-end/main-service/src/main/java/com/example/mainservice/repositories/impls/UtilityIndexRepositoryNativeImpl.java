package com.example.mainservice.repositories.impls;

import com.example.common.service.components.BaseNativeQuery;
import com.example.common.service.models.UtilityIndexModel;
import com.example.common.service.models.requests.PagingQueryConditionRequest;
import com.example.mainservice.repositories.UtilityIndexRepositoryNative;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.ObjectUtils;
@Repository
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UtilityIndexRepositoryNativeImpl implements UtilityIndexRepositoryNative {
    BaseNativeQuery nativeQuery;

    @Override
    public List<UtilityIndexModel> findAllWithRoomIsRented() {
        String sql = """
                SELECT
                utility_index.id, 
                 room.ID AS roomId,
                 utility_index.ELECTRIC_OLD_INDEX AS electricityOldIndex,
                 utility_index.ELECTRIC_NEW_INDEX AS electricityNewIndex,
                 utility_index.WATER_OLD_INDEX AS waterOldIndex,
                 utility_index.WATER_NEW_INDEX AS waterNewIndex,
                 utility_index.WATER_USAGE AS waterUsage,
                 utility_index.ELECTRIC_USAGE AS electricUsage,
                 utility_index.MONTH_MEASURE AS monthMeasure,
                 utility_index.YEAR_MEASURE AS yearMeasure
               FROM room
               LEFT JOIN utility_index ON utility_index.ROOM_ID = room.id WHERE room.STATUS = 'RENTED'
                """;
        List<UtilityIndexModel> result = nativeQuery.findList(sql, UtilityIndexModel.class);
        return result;
    }

    public Page<UtilityIndexModel> findAllWithPaging(PagingQueryConditionRequest pageRequest) {
        String baseQuery = " FROM UTILITY_INDEX ui JOIN ROOM r ON ui.ROOM_ID = r.ID";
        String whereClause = "";
        Map<String, Object> params= new HashMap<>();

        if (!ObjectUtils.isEmpty(pageRequest.getKeyword())) {
            String keyword = "%" + pageRequest.getKeyword().trim() + "%";
            whereClause = " WHERE CAST(ui.MONTH_MEASURE AS CHAR) LIKE :keyword OR CAST(ui.YEAR_MEASURE AS CHAR) LIKE :keyword ";
            params.put("keyword", keyword);
        }

        String countSql = "SELECT COUNT(*) " + baseQuery + whereClause;

        PageRequest pageable = PageRequest.of(pageRequest.getPageCurrent(), pageRequest.getPageSize());
        String querySql = """
                            SELECT ui.ID, ui.ELECTRIC_OLD_INDEX as electricityOldIndex,
                             ui.ELECTRIC_NEW_INDEX AS electricityNewIndex, ui.WATER_OLD_INDEX, 
                            ui.WATER_NEW_INDEX, ui.WATER_USAGE, ui.ELECTRIC_USAGE, ui.MONTH_MEASURE, 
                            ui.YEAR_MEASURE, r.NUMBER AS roomNumber  """ +
                baseQuery + whereClause;

        return nativeQuery.findPage(querySql, countSql, pageable, UtilityIndexModel.class, params);
    }
}
