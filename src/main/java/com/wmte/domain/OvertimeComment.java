package com.wmte.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A OvertimeComment.
 */
@Entity
@Table(name = "overtime_comment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OvertimeComment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "employee_name")
    private String employeeName;

    @Column(name = "employee_email")
    private String employeeEmail;

    @ManyToOne
    @JsonIgnoreProperties("overtimeComments")
    private Overtime overtime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public OvertimeComment comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public OvertimeComment createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public OvertimeComment employeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public OvertimeComment employeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
        return this;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public Overtime getOvertime() {
        return overtime;
    }

    public OvertimeComment overtime(Overtime overtime) {
        this.overtime = overtime;
        return this;
    }

    public void setOvertime(Overtime overtime) {
        this.overtime = overtime;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OvertimeComment)) {
            return false;
        }
        return id != null && id.equals(((OvertimeComment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OvertimeComment{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", employeeName='" + getEmployeeName() + "'" +
            ", employeeEmail='" + getEmployeeEmail() + "'" +
            "}";
    }
}
