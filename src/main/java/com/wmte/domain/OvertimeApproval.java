package com.wmte.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A OvertimeApproval.
 */
@Entity
@Table(name = "overtime_approval")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OvertimeApproval implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "approver_name")
    private String approverName;

    @Column(name = "approver_email")
    private String approverEmail;

    @ManyToOne
    @JsonIgnoreProperties("overtimeApprovals")
    private Overtime overtime;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApproverName() {
        return approverName;
    }

    public OvertimeApproval approverName(String approverName) {
        this.approverName = approverName;
        return this;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getApproverEmail() {
        return approverEmail;
    }

    public OvertimeApproval approverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
        return this;
    }

    public void setApproverEmail(String approverEmail) {
        this.approverEmail = approverEmail;
    }

    public Overtime getOvertime() {
        return overtime;
    }

    public OvertimeApproval overtime(Overtime overtime) {
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
        if (!(o instanceof OvertimeApproval)) {
            return false;
        }
        return id != null && id.equals(((OvertimeApproval) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OvertimeApproval{" +
            "id=" + getId() +
            ", approverName='" + getApproverName() + "'" +
            ", approverEmail='" + getApproverEmail() + "'" +
            "}";
    }
}
