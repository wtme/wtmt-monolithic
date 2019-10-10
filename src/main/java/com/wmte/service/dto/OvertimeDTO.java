package com.wmte.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import com.wmte.domain.enumeration.OvertimeStatus;

/**
 * A DTO for the {@link com.wmte.domain.Overtime} entity.
 */
public class OvertimeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String code;

    private String name;

    @NotNull
    private OvertimeStatus status;

    private String details;

    private Instant fromDate;

    private Instant toDate;

    private Instant createdDate;

    private BigDecimal overtimeInHours;

    private BigDecimal totalBillableHours;

    private BigDecimal totalCostingAmount;

    private String note;

    private String location;

    private String employeeName;

    private String employeeEmail;

    private String departmentName;

    private String departmentCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OvertimeStatus getStatus() {
        return status;
    }

    public void setStatus(OvertimeStatus status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getOvertimeInHours() {
        return overtimeInHours;
    }

    public void setOvertimeInHours(BigDecimal overtimeInHours) {
        this.overtimeInHours = overtimeInHours;
    }

    public BigDecimal getTotalBillableHours() {
        return totalBillableHours;
    }

    public void setTotalBillableHours(BigDecimal totalBillableHours) {
        this.totalBillableHours = totalBillableHours;
    }

    public BigDecimal getTotalCostingAmount() {
        return totalCostingAmount;
    }

    public void setTotalCostingAmount(BigDecimal totalCostingAmount) {
        this.totalCostingAmount = totalCostingAmount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OvertimeDTO overtimeDTO = (OvertimeDTO) o;
        if (overtimeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), overtimeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OvertimeDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", status='" + getStatus() + "'" +
            ", details='" + getDetails() + "'" +
            ", fromDate='" + getFromDate() + "'" +
            ", toDate='" + getToDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", overtimeInHours=" + getOvertimeInHours() +
            ", totalBillableHours=" + getTotalBillableHours() +
            ", totalCostingAmount=" + getTotalCostingAmount() +
            ", note='" + getNote() + "'" +
            ", location='" + getLocation() + "'" +
            ", employeeName='" + getEmployeeName() + "'" +
            ", employeeEmail='" + getEmployeeEmail() + "'" +
            ", departmentName='" + getDepartmentName() + "'" +
            ", departmentCode='" + getDepartmentCode() + "'" +
            "}";
    }
}
