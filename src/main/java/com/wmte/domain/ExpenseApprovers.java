package com.wmte.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A ExpenseApprovers.
 */
@Entity
@Table(name = "expense_approvers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ExpenseApprovers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties("expenseApprovers")
    private Employee employee;

    @ManyToOne
    @JsonIgnoreProperties("expenseApprovers")
    private Department department;

    @ManyToOne
    @JsonIgnoreProperties("expenseApprovers")
    private DepartmentRole departmentRole;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public ExpenseApprovers employee(Employee employee) {
        this.employee = employee;
        return this;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Department getDepartment() {
        return department;
    }

    public ExpenseApprovers department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public DepartmentRole getDepartmentRole() {
        return departmentRole;
    }

    public ExpenseApprovers departmentRole(DepartmentRole departmentRole) {
        this.departmentRole = departmentRole;
        return this;
    }

    public void setDepartmentRole(DepartmentRole departmentRole) {
        this.departmentRole = departmentRole;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExpenseApprovers)) {
            return false;
        }
        return id != null && id.equals(((ExpenseApprovers) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ExpenseApprovers{" +
            "id=" + getId() +
            "}";
    }
}
