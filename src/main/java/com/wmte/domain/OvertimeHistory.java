package com.wmte.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.wmte.domain.enumeration.OvertimeStatus;

/**
 * A OvertimeHistory.
 */
@Entity
@Table(name = "overtime_history")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OvertimeHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "date")
    private Instant date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OvertimeStatus status;

    @Column(name = "change_type")
    private String changeType;

    @Column(name = "who")
    private String who;

    @ManyToOne
    @JsonIgnoreProperties("overtimeHistories")
    private Overtime overtime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public OvertimeHistory date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public OvertimeStatus getStatus() {
        return status;
    }

    public OvertimeHistory status(OvertimeStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(OvertimeStatus status) {
        this.status = status;
    }

    public String getChangeType() {
        return changeType;
    }

    public OvertimeHistory changeType(String changeType) {
        this.changeType = changeType;
        return this;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getWho() {
        return who;
    }

    public OvertimeHistory who(String who) {
        this.who = who;
        return this;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public Overtime getOvertime() {
        return overtime;
    }

    public OvertimeHistory overtime(Overtime overtime) {
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
        if (!(o instanceof OvertimeHistory)) {
            return false;
        }
        return id != null && id.equals(((OvertimeHistory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OvertimeHistory{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", status='" + getStatus() + "'" +
            ", changeType='" + getChangeType() + "'" +
            ", who='" + getWho() + "'" +
            "}";
    }
}
