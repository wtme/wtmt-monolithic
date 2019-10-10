package com.wmte.service.mapper;

import com.wmte.domain.*;
import com.wmte.service.dto.EmployeeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {DepartmentMapper.class})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {

    @Mapping(source = "manager.id", target = "managerId")
    @Mapping(source = "departmen.id", target = "departmenId")
    @Mapping(source = "departmen.name", target = "departmenName")
    EmployeeDTO toDto(Employee employee);

    @Mapping(source = "managerId", target = "manager")
    @Mapping(source = "departmenId", target = "departmen")
    @Mapping(target = "employeeHasRoles", ignore = true)
    @Mapping(target = "removeEmployeeHasRole", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);

    default Employee fromId(Long id) {
        if (id == null) {
            return null;
        }
        Employee employee = new Employee();
        employee.setId(id);
        return employee;
    }
}
