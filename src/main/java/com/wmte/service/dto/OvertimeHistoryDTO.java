package com.wmte.service.dto;
import java.time.Instant;
import java.io.Serializable;
import java.util.Objects;
import com.wmte.domain.enumeration.OvertimeStatus;

/**
 * A DTO for the {@link com.wmte.domain.OvertimeHistory} entity.
 */
public class OvertimeHistoryDTO implements Serializable {

    private Long id;

    private Instant date;

    private OvertimeStatus status;

    private String changeType;

    private String who;


    private Long overtimeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public OvertimeStatus getStatus() {
        return status;
    }

    public void setStatus(OvertimeStatus status) {
        this.status = status;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
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

        OvertimeHistoryDTO overtimeHistoryDTO = (OvertimeHistoryDTO) o;
        if (overtimeHistoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), overtimeHistoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OvertimeHistoryDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", changeType='" + getChangeType() + "'" +
            ", who='" + getWho() + "'" +
            ", overtime=" + getOvertimeId() +
            "}";
    }
}
