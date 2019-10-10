package com.wmte.service.mapper;

import com.wmte.domain.*;
import com.wmte.service.dto.OvertimeHistoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OvertimeHistory} and its DTO {@link OvertimeHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {OvertimeMapper.class})
public interface OvertimeHistoryMapper extends EntityMapper<OvertimeHistoryDTO, OvertimeHistory> {

    @Mapping(source = "overtime.id", target = "overtimeId")
    OvertimeHistoryDTO toDto(OvertimeHistory overtimeHistory);

    @Mapping(source = "overtimeId", target = "overtime")
    OvertimeHistory toEntity(OvertimeHistoryDTO overtimeHistoryDTO);

    default OvertimeHistory fromId(Long id) {
        if (id == null) {
            return null;
        }
        OvertimeHistory overtimeHistory = new OvertimeHistory();
        overtimeHistory.setId(id);
        return overtimeHistory;
    }
}
