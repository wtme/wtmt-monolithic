package com.wmte.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.wmte.domain.enumeration.Gender;

import com.wmte.domain.enumeration.EmployeeStatus;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    @NotNull
    @Size(max = 50)
    @Column(name = "fullname", length = 50, nullable = false)
    private String fullname;

    @NotNull
    @Size(max = 50)
    @Column(name = "login", length = 50, nullable = false)
    private String login;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "email", nullable = false)
    private String email;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "date_of_joining")
    private Instant dateOfJoining;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EmployeeStatus status;

    @Column(name = "employee_number")
    private String employeeNumber;

    @Column(name = "date_of_birth")
    private Instant dateOfBirth;

    @Column(name = "note")
    private String note;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JsonIgnoreProperties("employees")
    private Employee manager;

    @ManyToOne
    @JsonIgnoreProperties("employees")
    private Department departmen;

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<EmployeeHasRole> employeeHasRoles = new HashSet<>();

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

    public Employee code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFullname() {
        return fullname;
    }

    public Employee fullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLogin() {
        return login;
    }

    public Employee login(String login) {
        this.login = login;
        return this;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public Employee email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public Employee personalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
        return this;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Employee phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public Employee mobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Instant getDateOfJoining() {
        return dateOfJoining;
    }

    public Employee dateOfJoining(Instant dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
        return this;
    }

    public void setDateOfJoining(Instant dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Gender getGender() {
        return gender;
    }

    public Employee gender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public Employee status(EmployeeStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public Employee employeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
        return this;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public Employee dateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNote() {
        return note;
    }

    public Employee note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserId() {
        return userId;
    }

    public Employee userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Employee getManager() {
        return manager;
    }

    public Employee manager(Employee employee) {
        this.manager = employee;
        return this;
    }

    public void setManager(Employee employee) {
        this.manager = employee;
    }

    public Department getDepartmen() {
        return departmen;
    }

    public Employee departmen(Department department) {
        this.departmen = department;
        return this;
    }

    public void setDepartmen(Department department) {
        this.departmen = department;
    }

    public Set<EmployeeHasRole> getEmployeeHasRoles() {
        return employeeHasRoles;
    }

    public Employee employeeHasRoles(Set<EmployeeHasRole> employeeHasRoles) {
        this.employeeHasRoles = employeeHasRoles;
        return this;
    }

    public Employee addEmployeeHasRole(EmployeeHasRole employeeHasRole) {
        this.employeeHasRoles.add(employeeHasRole);
        employeeHasRole.setEmployee(this);
        return this;
    }

    public Employee removeEmployeeHasRole(EmployeeHasRole employeeHasRole) {
        this.employeeHasRoles.remove(employeeHasRole);
        employeeHasRole.setEmployee(null);
        return this;
    }

    public void setEmployeeHasRoles(Set<EmployeeHasRole> employeeHasRoles) {
        this.employeeHasRoles = employeeHasRoles;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", fullname='" + getFullname() + "'" +
            ", login='" + getLogin() + "'" +
            ", email='" + getEmail() + "'" +
            ", personalEmail='" + getPersonalEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", mobile='" + getMobile() + "'" +
            ", dateOfJoining='" + getDateOfJoining() + "'" +
            ", gender='" + getGender() + "'" +
            ", status='" + getStatus() + "'" +
            ", employeeNumber='" + getEmployeeNumber() + "'" +
            ", dateOfBirth='" + getDateOfBirth() + "'" +
            ", note='" + getNote() + "'" +
            ", userId='" + getUserId() + "'" +
            "}";
    }
}
