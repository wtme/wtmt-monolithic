package com.wmte.service.mapper;

import com.wmte.domain.*;
import com.wmte.service.dto.OvertimeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Overtime} and its DTO {@link OvertimeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OvertimeMapper extends EntityMapper<OvertimeDTO, Overtime> {


    @Mapping(target = "overtimeApprovals", ignore = true)
    @Mapping(target = "removeOvertimeApproval", ignore = true)
    @Mapping(target = "overtimeHistories", ignore = true)
    @Mapping(target = "removeOvertimeHistory", ignore = true)
    @Mapping(target = "overtimeComments", ignore = true)
    @Mapping(target = "removeOvertimeComment", ignore = true)
    Overtime toEntity(OvertimeDTO overtimeDTO);

    default Overtime fromId(Long id) {
        if (id == null) {
            return null;
        }
        Overtime overtime = new Overtime();
        overtime.setId(id);
        return overtime;
    }
}
