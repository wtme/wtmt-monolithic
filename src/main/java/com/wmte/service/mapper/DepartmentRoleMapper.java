package com.wmte.service.mapper;

import com.wmte.domain.*;
import com.wmte.service.dto.DepartmentRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DepartmentRole} and its DTO {@link DepartmentRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepartmentRoleMapper extends EntityMapper<DepartmentRoleDTO, DepartmentRole> {



    default DepartmentRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        DepartmentRole departmentRole = new DepartmentRole();
        departmentRole.setId(id);
        return departmentRole;
    }
}
