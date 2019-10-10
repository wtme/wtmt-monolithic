package com.wmte.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmte.domain.EmployeeDepartment} entity.
 */
public class EmployeeDepartmentDTO implements Serializable {

    private Long id;


    private Long employeeId;

    private String employeeFullname;

    private Long departmentId;

    private String departmentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFullname() {
        return employeeFullname;
    }

    public void setEmployeeFullname(String employeeFullname) {
        this.employeeFullname = employeeFullname;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDepartmentDTO employeeDepartmentDTO = (EmployeeDepartmentDTO) o;
        if (employeeDepartmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeDepartmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeDepartmentDTO{" +
            "id=" + getId() +
            ", employee=" + getEmployeeId() +
            ", employee='" + getEmployeeFullname() + "'" +
            ", department=" + getDepartmentId() +
            ", department='" + getDepartmentName() + "'" +
            "}";
    }
}
