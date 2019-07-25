package com.mycompany.myapp.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Application} entity.
 */
public class ApplicationDTO implements Serializable {

    private UUID id;

    private String application_no;

    private String applicant_name;

    private String applicant_email;

    private String applicant_mobile;

    private ZonedDateTime applicant_dob;

    private String applicant_address;

    private String course;

    private String institute;

    private String speciality;

    private String course_doc;

    private String first_preference;

    private String second_preference;

    private String third_preference;

    private String entryby;

    private ZonedDateTime entry_date;

    private String status;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getApplication_no() {
        return application_no;
    }

    public void setApplication_no(String application_no) {
        this.application_no = application_no;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public String getApplicant_email() {
        return applicant_email;
    }

    public void setApplicant_email(String applicant_email) {
        this.applicant_email = applicant_email;
    }

    public String getApplicant_mobile() {
        return applicant_mobile;
    }

    public void setApplicant_mobile(String applicant_mobile) {
        this.applicant_mobile = applicant_mobile;
    }

    public ZonedDateTime getApplicant_dob() {
        return applicant_dob;
    }

    public void setApplicant_dob(ZonedDateTime applicant_dob) {
        this.applicant_dob = applicant_dob;
    }

    public String getApplicant_address() {
        return applicant_address;
    }

    public void setApplicant_address(String applicant_address) {
        this.applicant_address = applicant_address;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCourse_doc() {
        return course_doc;
    }

    public void setCourse_doc(String course_doc) {
        this.course_doc = course_doc;
    }

    public String getFirst_preference() {
        return first_preference;
    }

    public void setFirst_preference(String first_preference) {
        this.first_preference = first_preference;
    }

    public String getSecond_preference() {
        return second_preference;
    }

    public void setSecond_preference(String second_preference) {
        this.second_preference = second_preference;
    }

    public String getThird_preference() {
        return third_preference;
    }

    public void setThird_preference(String third_preference) {
        this.third_preference = third_preference;
    }

    public String getEntryby() {
        return entryby;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    public ZonedDateTime getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(ZonedDateTime entry_date) {
        this.entry_date = entry_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationDTO applicationDTO = (ApplicationDTO) o;
        if (applicationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationDTO{" +
            "id=" + getId() +
            ", application_no='" + getApplication_no() + "'" +
            ", applicant_name='" + getApplicant_name() + "'" +
            ", applicant_email='" + getApplicant_email() + "'" +
            ", applicant_mobile='" + getApplicant_mobile() + "'" +
            ", applicant_dob='" + getApplicant_dob() + "'" +
            ", applicant_address='" + getApplicant_address() + "'" +
            ", course='" + getCourse() + "'" +
            ", institute='" + getInstitute() + "'" +
            ", speciality='" + getSpeciality() + "'" +
            ", course_doc='" + getCourse_doc() + "'" +
            ", first_preference='" + getFirst_preference() + "'" +
            ", second_preference='" + getSecond_preference() + "'" +
            ", third_preference='" + getThird_preference() + "'" +
            ", entryby='" + getEntryby() + "'" +
            ", entry_date='" + getEntry_date() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
