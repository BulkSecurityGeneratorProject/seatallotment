package com.mycompany.myapp.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A ApplicationLog.
 */
@Table("applicationLog")
public class ApplicationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    private String application_no;

    private String action;

    private String action_by;

    private ZonedDateTime action_date;

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

    public ApplicationLog application_no(String application_no) {
        this.application_no = application_no;
        return this;
    }

    public void setApplication_no(String application_no) {
        this.application_no = application_no;
    }

    public String getAction() {
        return action;
    }

    public ApplicationLog action(String action) {
        this.action = action;
        return this;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction_by() {
        return action_by;
    }

    public ApplicationLog action_by(String action_by) {
        this.action_by = action_by;
        return this;
    }

    public void setAction_by(String action_by) {
        this.action_by = action_by;
    }

    public ZonedDateTime getAction_date() {
        return action_date;
    }

    public ApplicationLog action_date(ZonedDateTime action_date) {
        this.action_date = action_date;
        return this;
    }

    public void setAction_date(ZonedDateTime action_date) {
        this.action_date = action_date;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationLog)) {
            return false;
        }
        return id != null && id.equals(((ApplicationLog) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ApplicationLog{" +
            "id=" + getId() +
            ", application_no='" + getApplication_no() + "'" +
            ", action='" + getAction() + "'" +
            ", action_by='" + getAction_by() + "'" +
            ", action_date='" + getAction_date() + "'" +
            "}";
    }
}
