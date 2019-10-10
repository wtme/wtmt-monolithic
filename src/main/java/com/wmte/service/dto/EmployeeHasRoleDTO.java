package com.wmte.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmte.domain.EmployeeHasRole} entity.
 */
public class EmployeeHasRoleDTO implements Serializable {

    private Long id;

    private String name;


    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeHasRoleDTO employeeHasRoleDTO = (EmployeeHasRoleDTO) o;
        if (employeeHasRoleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeHasRoleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeHasRoleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", employee=" + getEmployeeId() +
            "}";
    }
}
