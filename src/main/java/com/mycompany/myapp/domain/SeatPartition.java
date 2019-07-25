package com.mycompany.myapp.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;
import java.util.UUID;

/**
 * A SeatPartition.
 */
@Table("seatPartition")
public class SeatPartition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    private String district;

    private Integer total;

    private Integer vacant;

    private Integer occupied;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDistrict() {
        return district;
    }

    public SeatPartition district(String district) {
        this.district = district;
        return this;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public Integer getTotal() {
        return total;
    }

    public SeatPartition total(Integer total) {
        this.total = total;
        return this;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getVacant() {
        return vacant;
    }

    public SeatPartition vacant(Integer vacant) {
        this.vacant = vacant;
        return this;
    }

    public void setVacant(Integer vacant) {
        this.vacant = vacant;
    }

    public Integer getOccupied() {
        return occupied;
    }

    public SeatPartition occupied(Integer occupied) {
        this.occupied = occupied;
        return this;
    }

    public void setOccupied(Integer occupied) {
        this.occupied = occupied;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SeatPartition)) {
            return false;
        }
        return id != null && id.equals(((SeatPartition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SeatPartition{" +
            "id=" + getId() +
            ", district='" + getDistrict() + "'" +
            ", total=" + getTotal() +
            ", vacant=" + getVacant() +
            ", occupied=" + getOccupied() +
            "}";
    }
}
