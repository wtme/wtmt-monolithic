package com.wmte.service.mapper;

import com.wmte.domain.*;
import com.wmte.service.dto.EmployeeDepartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeDepartment} and its DTO {@link EmployeeDepartmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, DepartmentMapper.class})
public interface EmployeeDepartmentMapper extends EntityMapper<EmployeeDepartmentDTO, EmployeeDepartment> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullname", target = "employeeFullname")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    EmployeeDepartmentDTO toDto(EmployeeDepartment employeeDepartment);

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "departmentId", target = "department")
    EmployeeDepartment toEntity(EmployeeDepartmentDTO employeeDepartmentDTO);

    default EmployeeDepartment fromId(Long id) {
        if (id == null) {
            return null;
        }
        EmployeeDepartment employeeDepartment = new EmployeeDepartment();
        employeeDepartment.setId(id);
        return employeeDepartment;
    }
}
