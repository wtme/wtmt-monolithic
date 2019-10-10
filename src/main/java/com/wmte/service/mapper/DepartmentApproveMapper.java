package com.wmte.service.mapper;

import com.wmte.domain.*;
import com.wmte.service.dto.DepartmentApproveDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DepartmentApprove} and its DTO {@link DepartmentApproveDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, DepartmentMapper.class, DepartmentRoleMapper.class})
public interface DepartmentApproveMapper extends EntityMapper<DepartmentApproveDTO, DepartmentApprove> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullname", target = "employeeFullname")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "departmentRole.id", target = "departmentRoleId")
    @Mapping(source = "departmentRole.name", target = "departmentRoleName")
    DepartmentApproveDTO toDto(DepartmentApprove departmentApprove);

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "departmentRoleId", target = "departmentRole")
    DepartmentApprove toEntity(DepartmentApproveDTO departmentApproveDTO);

    default DepartmentApprove fromId(Long id) {
        if (id == null) {
            return null;
        }
        DepartmentApprove departmentApprove = new DepartmentApprove();
        departmentApprove.setId(id);
        return departmentApprove;
    }
}
