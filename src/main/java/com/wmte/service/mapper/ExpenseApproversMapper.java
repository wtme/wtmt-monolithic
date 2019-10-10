package com.wmte.service.mapper;

import com.wmte.domain.*;
import com.wmte.service.dto.ExpenseApproversDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ExpenseApprovers} and its DTO {@link ExpenseApproversDTO}.
 */
@Mapper(componentModel = "spring", uses = {EmployeeMapper.class, DepartmentMapper.class, DepartmentRoleMapper.class})
public interface ExpenseApproversMapper extends EntityMapper<ExpenseApproversDTO, ExpenseApprovers> {

    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(source = "employee.fullname", target = "employeeFullname")
    @Mapping(source = "department.id", target = "departmentId")
    @Mapping(source = "department.name", target = "departmentName")
    @Mapping(source = "departmentRole.id", target = "departmentRoleId")
    @Mapping(source = "departmentRole.name", target = "departmentRoleName")
    ExpenseApproversDTO toDto(ExpenseApprovers expenseApprovers);

    @Mapping(source = "employeeId", target = "employee")
    @Mapping(source = "departmentId", target = "department")
    @Mapping(source = "departmentRoleId", target = "departmentRole")
    ExpenseApprovers toEntity(ExpenseApproversDTO expenseApproversDTO);

    default ExpenseApprovers fromId(Long id) {
        if (id == null) {
            return null;
        }
        ExpenseApprovers expenseApprovers = new ExpenseApprovers();
        expenseApprovers.setId(id);
        return expenseApprovers;
    }
}
