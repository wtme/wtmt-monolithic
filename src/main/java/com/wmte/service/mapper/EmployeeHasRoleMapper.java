package com.wmte.service.mapper;

import com.wmte.domain.*;
import com.wmte.service.dto.EmployeeHasRoleDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeHasRole} and its DTO {@link EmployeeHasRoleDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class})
public interface EmployeeHasRoleMapper extends EntityMapper<EmployeeHasRoleDTO, EmployeeHasRole> {

    @Mapping(source = "employee.id", target = "employeeId")
    EmployeeHasRoleDTO toDto(EmployeeHasRole employeeHasRole);

    @Mapping(source = "employeeId", target = "employee")
    EmployeeHasRole toEntity(EmployeeHasRoleDTO employeeHasRoleDTO);

    default EmployeeHasRole fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeHasRole employeeHasRole = new EmployeeHasRole();
        employeeHasRole.setId(id);
        return employeeHasRole;
    }
}
