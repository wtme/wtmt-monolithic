package com.wmte.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.wmte.domain.enumeration.OvertimeStatus;

/**
 * A Overtime.
 */
@Entity
@Table(name = "overtime")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Overtime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OvertimeStatus status;

    @Column(name = "details")
    private String details;

    @Column(name = "from_date")
    private Instant fromDate;

    @Column(name = "to_date")
    private Instant toDate;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "overtime_in_hours", precision = 21, scale = 2)
    private BigDecimal overtimeInHours;

    @Column(name = "total_billable_hours", precision = 21, scale = 2)
    private BigDecimal totalBillableHours;

    @Column(name = "total_costing_amount", precision = 21, scale = 2)
    private BigDecimal totalCostingAmount;

    @Column(name = "note")
    private String note;

    @Column(name = "location")
    private String location;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_email")
    private String employeeEmail;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "department_code")
    private String departmentCode;

    @OneToMany(mappedBy = "overtime")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OvertimeApproval> overtimeApprovals = new HashSet<>();

    @OneToMany(mappedBy = "overtime")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OvertimeHistory> overtimeHistories = new HashSet<>();

    @OneToMany(mappedBy = "overtime")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OvertimeComment> overtimeComments = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public Overtime code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public Overtime name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OvertimeStatus getStatus() {
        return status;
    }

    public Overtime status(OvertimeStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OvertimeStatus status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public Overtime details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public Overtime fromDate(Instant fromDate) {
        this.fromDate = fromDate;
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public Overtime toDate(Instant toDate) {
        this.toDate = toDate;
        return this;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Overtime createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public BigDecimal getOvertimeInHours() {
        return overtimeInHours;
    }

    public Overtime overtimeInHours(BigDecimal overtimeInHours) {
        this.overtimeInHours = overtimeInHours;
        return this;
    }

    public void setOvertimeInHours(BigDecimal overtimeInHours) {
        this.overtimeInHours = overtimeInHours;
    }

    public BigDecimal getTotalBillableHours() {
        return totalBillableHours;
    }

    public Overtime totalBillableHours(BigDecimal totalBillableHours) {
        this.totalBillableHours = totalBillableHours;
        return this;
    }

    public void setTotalBillableHours(BigDecimal totalBillableHours) {
        this.totalBillableHours = totalBillableHours;
    }

    public BigDecimal getTotalCostingAmount() {
        return totalCostingAmount;
    }

    public Overtime totalCostingAmount(BigDecimal totalCostingAmount) {
        this.totalCostingAmount = totalCostingAmount;
        return this;
    }

    public void setTotalCostingAmount(BigDecimal totalCostingAmount) {
        this.totalCostingAmount = totalCostingAmount;
    }

    public String getNote() {
        return note;
    }

    public Overtime note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLocation() {
        return location;
    }

    public Overtime location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Overtime employeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public Overtime employeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
        return this;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public Overtime departmentName(String departmentName) {
        this.departmentName = departmentName;
        return this;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public Overtime departmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
        return this;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public Set<OvertimeApproval> getOvertimeApprovals() {
        return overtimeApprovals;
    }

    public Overtime overtimeApprovals(Set<OvertimeApproval> overtimeApprovals) {
        this.overtimeApprovals = overtimeApprovals;
        return this;
    }

    public Overtime addOvertimeApproval(OvertimeApproval overtimeApproval) {
        this.overtimeApprovals.add(overtimeApproval);
        overtimeApproval.setOvertime(this);
        return this;
    }

    public Overtime removeOvertimeApproval(OvertimeApproval overtimeApproval) {
        this.overtimeApprovals.remove(overtimeApproval);
        overtimeApproval.setOvertime(null);
        return this;
    }

    public void setOvertimeApprovals(Set<OvertimeApproval> overtimeApprovals) {
        this.overtimeApprovals = overtimeApprovals;
    }

    public Set<OvertimeHistory> getOvertimeHistories() {
        return overtimeHistories;
    }

    public Overtime overtimeHistories(Set<OvertimeHistory> overtimeHistories) {
        this.overtimeHistories = overtimeHistories;
        return this;
    }

    public Overtime addOvertimeHistory(OvertimeHistory overtimeHistory) {
        this.overtimeHistories.add(overtimeHistory);
        overtimeHistory.setOvertime(this);
        return this;
    }

    public Overtime removeOvertimeHistory(OvertimeHistory overtimeHistory) {
        this.overtimeHistories.remove(overtimeHistory);
        overtimeHistory.setOvertime(null);
        return this;
    }

    public void setOvertimeHistories(Set<OvertimeHistory> overtimeHistories) {
        this.overtimeHistories = overtimeHistories;
    }

    public Set<OvertimeComment> getOvertimeComments() {
        return overtimeComments;
    }

    public Overtime overtimeComments(Set<OvertimeComment> overtimeComments) {
        this.overtimeComments = overtimeComments;
        return this;
    }

    public Overtime addOvertimeComment(OvertimeComment overtimeComment) {
        this.overtimeComments.add(overtimeComment);
        overtimeComment.setOvertime(this);
        return this;
    }

    public Overtime removeOvertimeComment(OvertimeComment overtimeComment) {
        this.overtimeComments.remove(overtimeComment);
        overtimeComment.setOvertime(null);
        return this;
    }

    public void setOvertimeComments(Set<OvertimeComment> overtimeComments) {
        this.overtimeComments = overtimeComments;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Overtime)) {
            return false;
        }
        return id != null && id.equals(((Overtime) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Overtime{" +
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
