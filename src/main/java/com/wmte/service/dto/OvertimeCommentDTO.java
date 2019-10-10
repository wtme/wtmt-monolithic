package com.wmte.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmte.domain.OvertimeComment} entity.
 */
public class OvertimeCommentDTO implements Serializable {

    private Long id;

    private String comment;

    private Instant createdDate;

    private String employeeName;

    private String employeeEmail;


    private Long overtimeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
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

    public Long getOvertimeId() {
        return overtimeId;
    }

    public void setOvertimeId(Long overtimeId) {
        this.overtimeId = overtimeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OvertimeCommentDTO overtimeCommentDTO = (OvertimeCommentDTO) o;
        if (overtimeCommentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), overtimeCommentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OvertimeCommentDTO{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", employeeName='" + getEmployeeName() + "'" +
            ", employeeEmail='" + getEmployeeEmail() + "'" +
            ", overtime=" + getOvertimeId() +
            "}";
    }
}
