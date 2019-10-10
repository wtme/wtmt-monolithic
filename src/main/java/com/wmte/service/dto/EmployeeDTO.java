package com.wmte.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.wmte.domain.enumeration.Gender;
import com.wmte.domain.enumeration.EmployeeStatus;

/**
 * A DTO for the {@link com.wmte.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 50)
    private String code;

    @NotNull
    @Size(max = 50)
    private String fullname;

    @NotNull
    @Size(max = 50)
    private String login;

    @NotNull
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String email;

    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")
    private String personalEmail;

    private String phoneNumber;

    private String mobile;

    private Instant dateOfJoining;

    private Gender gender;

    private EmployeeStatus status;

    private String employeeNumber;

    private Instant dateOfBirth;

    private String note;

    private String userId;


    private Long managerId;

    private Long departmenId;

    private String departmenName;

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

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Instant getDateOfJoining() {
        return dateOfJoining;
    }

    public void setDateOfJoining(Instant dateOfJoining) {
        this.dateOfJoining = dateOfJoining;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Instant getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long employeeId) {
        this.managerId = employeeId;
    }

    public Long getDepartmenId() {
        return departmenId;
    }

    public void setDepartmenId(Long departmentId) {
        this.departmenId = departmentId;
    }

    public String getDepartmenName() {
        return departmenName;
    }

    public void setDepartmenName(String departmentName) {
        this.departmenName = departmentName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (employeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
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
            ", manager=" + getManagerId() +
            ", departmen=" + getDepartmenId() +
            ", departmen='" + getDepartmenName() + "'" +
            "}";
    }
}
