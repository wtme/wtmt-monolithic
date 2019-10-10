package com.wmte.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.wmte.domain.OvertimeApproval} entity.
 */
public class OvertimeApprovalDTO implements Serializable {

    private Long id;

    private String approverName;

    private String approverEmail;


    private Long overtimeId;

    private String overtimeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getApproverEmail() {
        return approverEmail;
    }

    public void setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
    }

    public Long getOvertimeId() {
        return overtimeId;
    }

    public void setOvertimeId(Long overtimeId) {
        this.overtimeId = overtimeId;
    }

    public String getOvertimeName() {
        return overtimeName;
    }

    public void setOvertimeName(String overtimeName) {
        this.overtimeName = overtimeName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OvertimeApprovalDTO overtimeApprovalDTO = (OvertimeApprovalDTO) o;
        if (overtimeApprovalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), overtimeApprovalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OvertimeApprovalDTO{" +
            "id=" + getId() +
            ", approverName='" + getApproverName() + "'" +
            ", approverEmail='" + getApproverEmail() + "'" +
            ", overtime=" + getOvertimeId() +
            ", overtime='" + getOvertimeName() + "'" +
            "}";
    }
}
