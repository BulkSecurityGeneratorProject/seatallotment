package com.mycompany.myapp.service.dto;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.Applicationlog} entity.
 */
public class ApplicationlogDTO implements Serializable {

    private UUID id;

    private String application_no;

    private String action;

    private String action_by;

    private ZonedDateTime action_date;


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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAction_by() {
        return action_by;
    }

    public void setAction_by(String action_by) {
        this.action_by = action_by;
    }

    public ZonedDateTime getAction_date() {
        return action_date;
    }

    public void setAction_date(ZonedDateTime action_date) {
        this.action_date = action_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApplicationlogDTO applicationlogDTO = (ApplicationlogDTO) o;
        if (applicationlogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), applicationlogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ApplicationlogDTO{" +
            "id=" + getId() +
            ", application_no='" + getApplication_no() + "'" +
            ", action='" + getAction() + "'" +
            ", action_by='" + getAction_by() + "'" +
            ", action_date='" + getAction_date() + "'" +
            "}";
    }
}
