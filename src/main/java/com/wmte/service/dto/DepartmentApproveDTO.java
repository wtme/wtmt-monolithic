package com.wmte.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmte.domain.DepartmentApprove} entity.
 */
public class DepartmentApproveDTO implements Serializable {

    private Long id;


    private Long employeeId;

    private String employeeFullname;

    private Long departmentId;

    private String departmentName;

    private Long departmentRoleId;

    private String departmentRoleName;

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

    public Long getDepartmentRoleId() {
        return departmentRoleId;
    }

    public void setDepartmentRoleId(Long departmentRoleId) {
        this.departmentRoleId = departmentRoleId;
    }

    public String getDepartmentRoleName() {
        return departmentRoleName;
    }

    public void setDepartmentRoleName(String departmentRoleName) {
        this.departmentRoleName = departmentRoleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DepartmentApproveDTO departmentApproveDTO = (DepartmentApproveDTO) o;
        if (departmentApproveDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), departmentApproveDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepartmentApproveDTO{" +
            "id=" + getId() +
            ", employee=" + getEmployeeId() +
            ", employee='" + getEmployeeFullname() + "'" +
            ", department=" + getDepartmentId() +
            ", department='" + getDepartmentName() + "'" +
            ", departmentRole=" + getDepartmentRoleId() +
            ", departmentRole='" + getDepartmentRoleName() + "'" +
            "}";
    }
}
