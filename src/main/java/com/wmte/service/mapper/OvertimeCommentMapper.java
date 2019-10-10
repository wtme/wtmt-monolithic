package com.wmte.service.mapper;

import com.wmte.domain.*;
import com.wmte.service.dto.OvertimeCommentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link OvertimeComment} and its DTO {@link OvertimeCommentDTO}.
 */
@Mapper(componentModel = "spring", uses = {OvertimeMapper.class})
public interface OvertimeCommentMapper extends EntityMapper<OvertimeCommentDTO, OvertimeComment> {

    @Mapping(source = "overtime.id", target = "overtimeId")
    OvertimeCommentDTO toDto(OvertimeComment overtimeComment);

    @Mapping(source = "overtimeId", target = "overtime")
    OvertimeComment toEntity(OvertimeCommentDTO overtimeCommentDTO);

    default OvertimeComment fromId(Long id) {
        if (id == null) {
            return null;
        }
        OvertimeComment overtimeComment = new OvertimeComment();
        overtimeComment.setId(id);
        return overtimeComment;
    }
}
