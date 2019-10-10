package com.wmte.service.mapper;

import com.wmte.domain.*;
import com.wmte.service.dto.OvertimeApprovalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OvertimeApproval} and its DTO {@link OvertimeApprovalDTO}.
 */
@Mapper(componentModel = "spring", uses = {OvertimeMapper.class})
public interface OvertimeApprovalMapper extends EntityMapper<OvertimeApprovalDTO, OvertimeApproval> {

    @Mapping(source = "overtime.id", target = "overtimeId")
    OvertimeApprovalDTO toDto(OvertimeApproval overtimeApproval);

    @Mapping(source = "overtimeId", target = "overtime")
    OvertimeApproval toEntity(OvertimeApprovalDTO overtimeApprovalDTO);

    default OvertimeApproval fromId(Long id) {
        if (id == null) {
            return null;
        }
        OvertimeApproval overtimeApproval = new OvertimeApproval();
        overtimeApproval.setId(id);
        return overtimeApproval;
    }
}
