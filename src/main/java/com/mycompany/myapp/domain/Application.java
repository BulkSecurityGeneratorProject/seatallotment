package com.mycompany.myapp.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A Application.
 */
@Table("application")
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
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

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getApplication_no() {
        return application_no;
    }

    public Application application_no(String application_no) {
        this.application_no = application_no;
        return this;
    }

    public void setApplication_no(String application_no) {
        this.application_no = application_no;
    }

    public String getApplicant_name() {
        return applicant_name;
    }

    public Application applicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
        return this;
    }

    public void setApplicant_name(String applicant_name) {
        this.applicant_name = applicant_name;
    }

    public String getApplicant_email() {
        return applicant_email;
    }

    public Application applicant_email(String applicant_email) {
        this.applicant_email = applicant_email;
        return this;
    }

    public void setApplicant_email(String applicant_email) {
        this.applicant_email = applicant_email;
    }

    public String getApplicant_mobile() {
        return applicant_mobile;
    }

    public Application applicant_mobile(String applicant_mobile) {
        this.applicant_mobile = applicant_mobile;
        return this;
    }

    public void setApplicant_mobile(String applicant_mobile) {
        this.applicant_mobile = applicant_mobile;
    }

    public ZonedDateTime getApplicant_dob() {
        return applicant_dob;
    }

    public Application applicant_dob(ZonedDateTime applicant_dob) {
        this.applicant_dob = applicant_dob;
        return this;
    }

    public void setApplicant_dob(ZonedDateTime applicant_dob) {
        this.applicant_dob = applicant_dob;
    }

    public String getApplicant_address() {
        return applicant_address;
    }

    public Application applicant_address(String applicant_address) {
        this.applicant_address = applicant_address;
        return this;
    }

    public void setApplicant_address(String applicant_address) {
        this.applicant_address = applicant_address;
    }

    public String getCourse() {
        return course;
    }

    public Application course(String course) {
        this.course = course;
        return this;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getInstitute() {
        return institute;
    }

    public Application institute(String institute) {
        this.institute = institute;
        return this;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getSpeciality() {
        return speciality;
    }

    public Application speciality(String speciality) {
        this.speciality = speciality;
        return this;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCourse_doc() {
        return course_doc;
    }

    public Application course_doc(String course_doc) {
        this.course_doc = course_doc;
        return this;
    }

    public void setCourse_doc(String course_doc) {
        this.course_doc = course_doc;
    }

    public String getFirst_preference() {
        return first_preference;
    }

    public Application first_preference(String first_preference) {
        this.first_preference = first_preference;
        return this;
    }

    public void setFirst_preference(String first_preference) {
        this.first_preference = first_preference;
    }

    public String getSecond_preference() {
        return second_preference;
    }

    public Application second_preference(String second_preference) {
        this.second_preference = second_preference;
        return this;
    }

    public void setSecond_preference(String second_preference) {
        this.second_preference = second_preference;
    }

    public String getThird_preference() {
        return third_preference;
    }

    public Application third_preference(String third_preference) {
        this.third_preference = third_preference;
        return this;
    }

    public void setThird_preference(String third_preference) {
        this.third_preference = third_preference;
    }

    public String getEntryby() {
        return entryby;
    }

    public Application entryby(String entryby) {
        this.entryby = entryby;
        return this;
    }

    public void setEntryby(String entryby) {
        this.entryby = entryby;
    }

    public ZonedDateTime getEntry_date() {
        return entry_date;
    }

    public Application entry_date(ZonedDateTime entry_date) {
        this.entry_date = entry_date;
        return this;
    }

    public void setEntry_date(ZonedDateTime entry_date) {
        this.entry_date = entry_date;
    }

    public String getStatus() {
        return status;
    }

    public Application status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Application)) {
            return false;
        }
        return id != null && id.equals(((Application) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Application{" +
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
